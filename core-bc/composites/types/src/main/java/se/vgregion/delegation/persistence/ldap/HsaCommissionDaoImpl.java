package se.vgregion.delegation.persistence.ldap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import se.vgregion.delegation.domain.HealthCareUnit;
import se.vgregion.delegation.domain.HsaCommission;
import se.vgregion.delegation.persistence.HsaCommissionDao;

import javax.naming.directory.SearchControls;
import java.util.List;

import static java.lang.String.format;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-11-08 18:12
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class HsaCommissionDaoImpl implements HsaCommissionDao {

    @Autowired
    private LdapTemplate ldapTemplate;

    public List<HsaCommission> findAll(String memberHsaIdentity) {
        String base = "ou=Org";
        String filter = String.format("(& (objectClass=hsaCommission)(hsaCommissionMember=%s))",
                memberHsaIdentity);
        try {
            return (List<HsaCommission>) ldapTemplate.search(base, filter, new HsaCommissionContextMapper());
        } catch (Exception ex) {
            String msg = format("Failed to find HsaCommission information for [%s]", memberHsaIdentity);
            throw new RuntimeException(msg, ex);
        }
    }

    @Override
    public List<HsaCommission> resolve(HealthCareUnit vardEnhet) {
        String base = vardEnhet.getDn();
        try {
            return (List<HsaCommission>) ldapTemplate.search(base, "(objectClass=hsaCommission)",
                    SearchControls.ONELEVEL_SCOPE, new HsaCommissionContextMapper());
        } catch (Exception ex) {
            String msg = format("Failed to resolve HsaCommission information for [%s]", base);
            throw new RuntimeException(msg, ex);
        }
    }
}
