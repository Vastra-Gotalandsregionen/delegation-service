package se.vgregion.delegation.persistence;

import se.vgregion.delegation.domain.HealthCareUnit;

import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-11-07 17:13
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public interface HealthCareUnitDao {

    Set<HealthCareUnit> findAll();

    HealthCareUnit lookupUnit(String orgDN);

    List<HealthCareUnit> resolveUnit(String filter);
}
