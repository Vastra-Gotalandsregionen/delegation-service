package se.vgregion.delegation.persistence.ldap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import se.vgregion.delegation.domain.HealthCareUnit;
import se.vgregion.delegation.domain.PersonalInfo;
import se.vgregion.delegation.domain.VardEnhetInfo;
import se.vgregion.delegation.persistence.HealthCareUnitDao;
import se.vgregion.delegation.persistence.PersonalInfoDao;
import se.vgregion.delegation.persistence.VardEnhetDao;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-10-19 11:23
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class VardEnhetDaoImpl implements VardEnhetDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(VardEnhetDaoImpl.class);

    @Autowired
    private PersonalInfoDao personalInfoDao;

    @Autowired
    private HealthCareUnitDao healthCareUnitDao;

    @Override
    public boolean isVerksamhetsChef(String vgrId) {
        try {
            PersonalInfo vcInfo = personalInfoDao.lookup(vgrId);
            if (StringUtils.isBlank(vcInfo.getHsaIdentity())) {
                return false;
            }
            List<HealthCareUnit> vardEnheter = findVerksamhetsChefHealthCareUnit(vcInfo.getHsaIdentity());
            return vardEnheter.size() > 0;
        } catch (Exception ex) {
            LOGGER.warn("Failed to query catalog for VC status", ex);
            return false;
        }
    }

    @Override
    public List<VardEnhetInfo> find(String vcVgrId) {
        List<VardEnhetInfo> vardEnhetList = new ArrayList<VardEnhetInfo>();
        try {
            PersonalInfo vcInfo = personalInfoDao.lookup(vcVgrId);
            if (StringUtils.isBlank(vcInfo.getHsaIdentity())) {
                String msg = format("User [%s] is not VerksamhetsChef." +
                        "Catalog data-error - no hsaIdentity", vcVgrId);
                throw new RuntimeException(msg); // Empty List, no hsaIdentity found.
            }

            List<HealthCareUnit> vardEnhets = findVerksamhetsChefHealthCareUnit(vcInfo.getHsaIdentity());
            for (HealthCareUnit vardEnhet : vardEnhets) {
                VardEnhetInfo vardEnhetInfo = new VardEnhetInfo();

                // 1: resolve Personal information
                vardEnhetInfo.setVerksamhetsChef(vcInfo);

                // 2: resolve VårdEnhet information
                vardEnhetInfo.setVardEnhet(vardEnhet);

                // 2b: resolve Personal for VårdEnhet
                String vardEnhetPersonalFilter = format("(&(objectClass=person)%s)",
                        personalInfoDao.personalInUnitFilter(vardEnhet.getDn()));
                vardEnhet.setPersonal(personalInfoDao.resolvePersonal(vardEnhetPersonalFilter));


                // 3: resolve VårdGivare
                if ("SE2321000131-E000000000001".equals(vardEnhet.getHsaResponsibleHealthCareProvider())) {
                    HealthCareUnit vardGivare = new HealthCareUnit();
                    vardGivare.setHsaIdentity(vardEnhet.getHsaResponsibleHealthCareProvider());
                    vardEnhetInfo.setVardGivare(vardGivare);
                } else {
                    String vardGivarFilter = format("(&(objectClass=organizationalUnit)(hsaIdentity=%s))",
                            vardEnhet.getHsaResponsibleHealthCareProvider());
                    List<HealthCareUnit> vardGivarResult = healthCareUnitDao.resolveUnit(vardGivarFilter);
                    switch (vardGivarResult.size()) {
                        case 0: {
                            String msg = format("Failed to resolve VårdGivare [%s] för VårdEnhet [%s]. " +
                                    "Catalog data-error - VårdGivare cannot be found.",
                                    vardEnhet.getHsaResponsibleHealthCareProvider(),
                                    vardEnhet.getHsaIdentity());
                            throw new RuntimeException(msg);
                        }
                        case 1:
                            vardEnhetInfo.setVardGivare(vardGivarResult.get(0));
                            break;
                        default: {
                            String msg = format("Failed to resolve VårdGivare [%s] för VårdEnhet [%s]. " +
                                    "Catalog data-error - VårdGivare is ambiguous.",
                                    vardEnhet.getHsaResponsibleHealthCareProvider(),
                                    vardEnhet.getHsaIdentity());
                            throw new RuntimeException(msg);
                        }
                    }
                }

                // 4: resolve IngåendeEnheter
                if (vardEnhet.getHsaHealthCareUnitMembers().length > 0) {
                    String ingaendeEnhetFilter = ingaendeEnheterFilter(vardEnhet);
                    vardEnhetInfo.setIngaendeEnheter(healthCareUnitDao.resolveUnit(ingaendeEnhetFilter));

                    // 4b: foreach Enhet add Personal
                    for (HealthCareUnit enhet : vardEnhetInfo.getIngaendeEnheter()) {
                        String enhetPersonalFilter = format("(&(objectClass=person)%s)",
                                personalInfoDao.personalInUnitFilter(enhet.getDn()));

                        enhet.setPersonal(personalInfoDao.resolvePersonal(enhetPersonalFilter));
                    }
                }

                // 5: Uppdrag
                // TODO: Resolve HsaCommission

                // 6: OK, add to list
                vardEnhetList.add(vardEnhetInfo);
            }
            return vardEnhetList;
        } catch (Exception ex) {
            LOGGER.error("Failed to extract VerksamhetsChef", ex);
            return new ArrayList<VardEnhetInfo>(); // Empty List due to data-error.
        }
    }

    private String ingaendeEnheterFilter(HealthCareUnit vardEnhet) {
        StringBuilder orClause = new StringBuilder("(| ");
        for (String hsaIdentityUnit : vardEnhet.getHsaHealthCareUnitMembers()) {
            orClause.append("(hsaIdentity=").append(hsaIdentityUnit).append(")");
        }
        orClause.append(")");
        return format("(&(objectClass=organizationalUnit)%s)", orClause);
    }

    private List<HealthCareUnit> findVerksamhetsChefHealthCareUnit(String vcHsaIdentity) {
        String orgFilter = format("(&(objectClass=organizationalUnit)(hsaHealthCareUnitManager=%s))",
                vcHsaIdentity);

        return healthCareUnitDao.resolveUnit(orgFilter);
    }
}
