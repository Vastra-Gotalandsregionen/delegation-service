package se.vgregion.delegation.persistence.ldap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import se.vgregion.delegation.domain.HealthCareUnit;
import se.vgregion.delegation.domain.Personal;
import se.vgregion.delegation.domain.VerksamhetsChef;
import se.vgregion.delegation.persistence.VerksamhetsChefDao;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-10-19 11:23
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class VerksamhetsChefDaoImpl implements VerksamhetsChefDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerksamhetsChefDaoImpl.class);

    @Autowired
    private LdapTemplate ldapTemplate;

    @Override
    public boolean isVerksamhetsChef(String vgrId) {
        try {
            String hsaIdentity = lookupHsaIdentity(vgrId);
            if (hsaIdentity == null) {
                return false;
            }
            List<String> orgDNs = lookupVerksamhetsChefHealthCareUnitDNs(hsaIdentity);
            return orgDNs.size() > 0;
        } catch (Exception ex) {
            LOGGER.warn("Failed to query catalog for VC status", ex);
            return false;
        }
    }

    @Override
    public List<VerksamhetsChef> find(String vcVgrId) {
        List<VerksamhetsChef> verksamhetsChefList = new ArrayList<VerksamhetsChef>();
        try {
            String hsaIdentityVc = lookupHsaIdentity(vcVgrId);
            if (StringUtils.isBlank(hsaIdentityVc)) {
                String msg = format("User [%s] is not VerksamhetsChef, due to no hsaIdentity", vcVgrId);
                LOGGER.warn(msg);
                return verksamhetsChefList; // Empty List, no hsaIdentity found.
            }

            List<String> orgDNs = lookupVerksamhetsChefHealthCareUnitDNs(hsaIdentityVc);
            for (String orgDN : orgDNs) {
                VerksamhetsChef vc = new VerksamhetsChef();

                // 1: resolve Personal information
                String userFilter = format("(&(objectclass=person)(uid=%s))", vcVgrId);
                List<Personal> personalList = resolvePersonal(userFilter);
                if (personalList.size() != 1) {
                    String msg = format("Personal information is ambiguous for [%s]", vcVgrId);
                    throw new RuntimeException(msg);
                }
                vc.setPersonal(personalList.get(0));

                // 2: resolve VårdEnhet information
                HealthCareUnit vardEnhet = lookupUnit(orgDN);
                vc.setVardEnhet(vardEnhet);

                // 2b: resolve Personal for VårdEnhet
                String vardEnhetPersonalFilter = format("(&(objectclass=person)(vgrStrukturPersonDN=%s))",
                        vardEnhet.getDn());
                vardEnhet.setPersonal(resolvePersonal(vardEnhetPersonalFilter));


                // 3: resolve VårdGivare
                String vardGivarFilter = format("(&(objectclass=organizationalUnit)(hsaIdentity=%s))",
                        vardEnhet.getHsaResponsibleHealthCareProvider());
                List<HealthCareUnit> vardGivarResult = resolveUnit(vardGivarFilter);
                switch (vardGivarResult.size()) {
                    case 0: {
                        String msg = format("Failed to resolve VårdGivare [%s] för VårdEnhet [%s]. " +
                                "Catalog data-error - VårdGivare cannot be found.",
                                vardEnhet.getHsaResponsibleHealthCareProvider(),
                                vardEnhet.getHsaIdentity());
                        throw new RuntimeException(msg);
                    }
                    case 1:
                        vc.setVardGivare(vardGivarResult.get(0));
                        break;
                    default: {
                        String msg = format("Failed to resolve VårdGivare [%s] för VårdEnhet [%s]. " +
                                "Catalog data-error - VårdGivare is ambiguous.",
                                vardEnhet.getHsaResponsibleHealthCareProvider(),
                                vardEnhet.getHsaIdentity());
                        throw new RuntimeException(msg);
                    }
                }

                // 4: resolve IngåendeEnheter
                if (vardEnhet.getHsaHealthCareUnitMembers().length > 0) {
                    StringBuilder orClause = new StringBuilder("(| ");
                    for (String hsaIdentityUnit : vardEnhet.getHsaHealthCareUnitMembers()) {
                        orClause.append("(").append(hsaIdentityUnit).append(")");
                    }
                    orClause.append(")");
                    String ingaendeEnhetFilter = format("(&(objectclass=organizationalUnit)%s)", orClause);
                    vc.setIngaendeEnheter(resolveUnit(vardGivarFilter));

                    // 4b: foreach Enhet add Personal
                    for (HealthCareUnit enhet: vc.getIngaendeEnheter()) {
                        String enhetPersonalFilter = format("(&(objectclass=person)(vgrStrukturPersonDN=%s))",
                                enhet.getDn());
                        enhet.setPersonal(resolvePersonal(enhetPersonalFilter));
                    }
                }
            }
            return verksamhetsChefList;
        } catch (Exception ex) {
            LOGGER.error("Failed to extract VerksamhetsChef", ex);
            return new ArrayList<VerksamhetsChef>(); // Empty List due to data-error.
        }
    }

    private HealthCareUnit lookupUnit(String orgDN) {
        return (HealthCareUnit) ldapTemplate.lookup(orgDN, new UnitContextMapper());
    }

    private List<HealthCareUnit> resolveUnit(String filter) {
        String base = "ou=Org,o=vgr";
        return (List<HealthCareUnit>) ldapTemplate.search(base, filter, new UnitContextMapper());
    }

    private List<Personal> resolvePersonal(String filter) {
        try {
            String base = "ou=personal,ou=anv,o=vgr";
            return (List<Personal>) ldapTemplate.search(base, filter, new PersonalContextMapper());
        } catch (Exception ex) {
            String msg = format("Failed to resolvePersonal Personal information for [%s]", filter);
            throw new RuntimeException(msg, ex);
        }
    }

    private String lookupHsaIdentity(String vgrId) {
        String userBase = "ou=personal,ou=anv,o=vgr";
        String userFilter = format("(&(objectclass=person)(uid=%s))", vgrId);

        List<String> hsaIdentities = ldapTemplate.search(userBase, userFilter, new AttributesMapper() {
            @Override
            public Object mapFromAttributes(Attributes attributes) throws NamingException {
                return attributes.get("hsaIdentity");
            }
        });

        switch (hsaIdentities.size()) {
            case 0:
                return null;
            case 1:
                return hsaIdentities.get(0);
            default:
                String msg = format("Failed to find hsaIdentity, vgrId [%s] is not distinct in the catalog"
                        , vgrId);
                throw new RuntimeException(msg);
        }
    }

    private List<String> lookupVerksamhetsChefHealthCareUnitDNs(String hsaIdentity) {
        String orgBase = "ou=org,o=vgr";
        String orgFilter = format("(hsaHealthCareUnitManager=%s)", hsaIdentity);

        return ldapTemplate.search(orgBase, orgFilter, new AttributesMapper() {
            @Override
            public Object mapFromAttributes(Attributes attributes) throws NamingException {
                return attributes.get("dn");
            }
        });
    }

    private class PersonalContextMapper implements ContextMapper {
        @Override
        public Object mapFromContext(Object context) {
            DirContextAdapter ctx = (DirContextAdapter) context;

            Personal personal = new Personal();
            personal.setDn(ctx.getDn().toString());
            personal.setDisplayName(ctx.getStringAttribute("displayName"));
            personal.setFullName(ctx.getStringAttribute("fullName"));
            personal.setGivenName(ctx.getStringAttribute("givenName"));
            personal.setHsaIdentity(ctx.getStringAttribute("hsaIdentity"));
            personal.setMail(ctx.getStringAttribute("mail"));
            personal.setSn(ctx.getStringAttribute("sn"));
            personal.setTitle(ctx.getStringAttribute("title"));
            personal.setUid(ctx.getStringAttribute("uid"));
            personal.setVgrStructurePersonDN(ctx.getStringAttributes("vgrStructurePersonDN"));

            return personal;
        }
    }

    private static class UnitContextMapper implements ContextMapper {
        @Override
        public Object mapFromContext(Object context) {
            DirContextAdapter ctx = (DirContextAdapter) context;

            HealthCareUnit unit = new HealthCareUnit();
            unit.setDn(ctx.getDn().toString());
            unit.setOu(ctx.getStringAttribute("ou"));
            unit.setHsaIdentity(ctx.getStringAttribute("hsaIdentity"));
            unit.setHsaHealthCareUnitManager(ctx.getStringAttribute("hsaHealthCareUnitManager"));
            unit.setHsaHealthCareUnitMembers(ctx.getStringAttributes("hsaHealthCareUnitMember"));
            unit.setHsaResponsibleHealthCareProvider(ctx.getStringAttribute("hsaResponsibleHealthCareProvider"));
            unit.setHsaInternalAddress(ctx.getStringAttribute("hsaInternalAddress"));
            unit.setLabeledUri(ctx.getStringAttribute("labeledUri"));
            unit.setVgrOrganizationalUnitNameShort(ctx.getStringAttribute("vgrOrganizationalUnitNameShort"));

            return unit;
        }
    }
}
