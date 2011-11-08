package se.vgregion.delegation.persistence.ldap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import se.vgregion.delegation.domain.HealthCareUnit;
import se.vgregion.delegation.persistence.HealthCareUnitDao;

import java.util.*;

import static java.lang.String.format;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-11-07 17:15
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class HealthCareUnitDaoImpl implements HealthCareUnitDao {
    private final Logger LOGGER = LoggerFactory.getLogger(HealthCareUnitDaoImpl.class);

    @Autowired
    private LdapTemplate ldapTemplate;

    @Override
    public Set<HealthCareUnit> findAll() {
        Set<HealthCareUnit> allHealthCareUnits = new TreeSet<HealthCareUnit>(new Comparator<HealthCareUnit>() {
            @Override
            public int compare(HealthCareUnit unitLeft, HealthCareUnit unitRight) {
                return unitLeft.getOu().compareTo(unitRight.getOu());
            }
        });

        for (String q : Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
                "s", "t", "u", "v", "w", "x", "y", "z", "å", "ä", "ö")) {
            String filter = String.format("(&(objectClass=hsaHealthCareUnit)(ou=%s*))", q);
            allHealthCareUnits.addAll(resolveUnit(filter));
            LOGGER.debug("{}: {}", q, allHealthCareUnits.size());
        }
        return allHealthCareUnits;
    }

    public HealthCareUnit lookupUnit(String orgDN) {
        try {
            return (HealthCareUnit) ldapTemplate.lookup(orgDN, new HealthCareUnitContextMapper());
        } catch (Exception ex) {
            String msg = format("Failed to resolve Unit information for DN [%s]", orgDN);
            throw new RuntimeException(msg, ex);
        }
    }

    public List<HealthCareUnit> resolveUnit(String filter) {
        try {
            String base = "ou=Org";
            return (List<HealthCareUnit>) ldapTemplate.search(base, filter, new HealthCareUnitContextMapper());
        } catch (Exception ex) {
            String msg = format("Failed to resolve Unit information for [%s]", filter);
            throw new RuntimeException(msg, ex);
        }
    }

}
