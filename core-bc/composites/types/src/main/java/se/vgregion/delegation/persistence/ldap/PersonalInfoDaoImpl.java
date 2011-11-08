package se.vgregion.delegation.persistence.ldap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import se.vgregion.delegation.domain.PersonalInfo;
import se.vgregion.delegation.persistence.PersonalInfoDao;

import java.util.List;

import static java.lang.String.format;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-11-08 13:31
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class PersonalInfoDaoImpl implements PersonalInfoDao {

    @Autowired
    private LdapTemplate ldapTemplate;

    public PersonalInfo lookup(String vgrId) {
        String userFilter = format("(&(objectClass=person)(uid=%s))", vgrId);

        List<PersonalInfo> hsaIdentities = resolvePersonal(userFilter);

        switch (hsaIdentities.size()) {
            case 0:
                return null;
            case 1:
                return hsaIdentities.get(0);
            default:
                String msg = format("Failed to find PersonalInfo, vgrId [%s] is not distinct in the catalog"
                        , vgrId);
                throw new RuntimeException(msg);
        }
    }

    public String personalInUnitFilter(String unitDn) {
        String[] strukturGroup = unitDn.split(",");

        StringBuilder sb = new StringBuilder();
        sb.append("(ou=").append(strukturGroup[0].split("=")[1]).append(")");
        for (int i=1; i < strukturGroup.length; i++) {
            sb.append("(StrukturGrupp=").append(strukturGroup[i].split("=")[1]).append(")");
        }

        return sb.toString();
    }

    public List<PersonalInfo> resolvePersonal(String filter) {
        try {
            String base = "ou=personal,ou=anv";
            return (List<PersonalInfo>) ldapTemplate.search(base, filter, new PersonalInfoContextMapper());
        } catch (Exception ex) {
            String msg = format("Failed to resolve Personal information for [%s]", filter);
            throw new RuntimeException(msg, ex);
        }
    }
}
