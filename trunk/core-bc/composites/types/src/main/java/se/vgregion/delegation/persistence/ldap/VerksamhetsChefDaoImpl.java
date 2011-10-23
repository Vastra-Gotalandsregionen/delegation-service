package se.vgregion.delegation.persistence.ldap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import se.vgregion.delegation.domain.HealthCareUnit;
import se.vgregion.delegation.domain.PersonalInfo;
import se.vgregion.delegation.domain.VerksamhetsChefInfo;
import se.vgregion.delegation.persistence.VerksamhetsChefDao;

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
            PersonalInfo vcInfo = lookup(vgrId);
            if (StringUtils.isBlank(vcInfo.getHsaIdentity())) {
                return false;
            }
            List<HealthCareUnit> vardEnheter = lookupVerksamhetsChefHealthCareUnitDNs(vcInfo.getHsaIdentity());
            return vardEnheter.size() > 0;
        } catch (Exception ex) {
            LOGGER.warn("Failed to query catalog for VC status", ex);
            return false;
        }
    }

    @Override
    public List<VerksamhetsChefInfo> find(String vcVgrId) {
        List<VerksamhetsChefInfo> verksamhetsChefList = new ArrayList<VerksamhetsChefInfo>();
        try {
            PersonalInfo vcInfo = lookup(vcVgrId);
            if (StringUtils.isBlank(vcInfo.getHsaIdentity())) {
                String msg = format("User [%s] is not VerksamhetsChef." +
                        "Catalog data-error - no hsaIdentity", vcVgrId);
                throw new RuntimeException(msg); // Empty List, no hsaIdentity found.
            }

            List<HealthCareUnit> vardEnhets = lookupVerksamhetsChefHealthCareUnitDNs(vcInfo.getHsaIdentity());
            for (HealthCareUnit vardEnhet : vardEnhets) {
                VerksamhetsChefInfo vc = new VerksamhetsChefInfo();

                // 1: resolve Personal information
                vc.setVerksamhetsChef(vcInfo);

                // 2: resolve VårdEnhet information
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
                        orClause.append("(hsaIdentity=").append(hsaIdentityUnit).append(")");
                    }
                    orClause.append(")");
                    String ingaendeEnhetFilter = format("(&(objectclass=organizationalUnit)%s)", orClause);
                    vc.setIngaendeEnheter(resolveUnit(ingaendeEnhetFilter));

                    // 4b: foreach Enhet add Personal
                    for (HealthCareUnit enhet : vc.getIngaendeEnheter()) {
                        String enhetPersonalFilter = format("(&(objectclass=person)(vgrStrukturPersonDN=%s))",
                                enhet.getDn());
                        enhet.setPersonal(resolvePersonal(enhetPersonalFilter));
                    }
                }

                // 5: OK, add to list
                verksamhetsChefList.add(vc);
            }
            return verksamhetsChefList;
        } catch (Exception ex) {
            LOGGER.error("Failed to extract VerksamhetsChef", ex);
            return new ArrayList<VerksamhetsChefInfo>(); // Empty List due to data-error.
        }
    }

    private PersonalInfo lookup(String vgrId) {
        String userFilter = format("(&(objectclass=person)(uid=%s))", vgrId);

        List<PersonalInfo> hsaIdentities = resolvePersonal(userFilter);

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

    private List<HealthCareUnit> lookupVerksamhetsChefHealthCareUnitDNs(String hsaIdentity) {
        String orgFilter = format("(&(objectclass=organizationalUnit)(hsaHealthCareUnitManager=%s))",
                hsaIdentity);

        return resolveUnit(orgFilter);
    }

    //    private HealthCareUnit lookupUnit(String orgDN) {
//        return (HealthCareUnit) ldapTemplate.lookup(orgDN, new UnitContextMapper());
//    }

    private List<HealthCareUnit> resolveUnit(String filter) {
        try {
            String base = "ou=Org,o=vgr";
            return (List<HealthCareUnit>) ldapTemplate.search(base, filter, new HealthCareUnitContextMapper());
        } catch (Exception ex) {
            String msg = format("Failed to resolve Unit information for [%s]", filter);
            throw new RuntimeException(msg, ex);
        }
    }

    private List<PersonalInfo> resolvePersonal(String filter) {
        try {
            String base = "ou=personal,ou=anv,o=vgr";
            return (List<PersonalInfo>) ldapTemplate.search(base, filter, new PersonalInfoContextMapper());
        } catch (Exception ex) {
            String msg = format("Failed to resolve Personal information for [%s]", filter);
            throw new RuntimeException(msg, ex);
        }
    }
}
