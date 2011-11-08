package se.vgregion.delegation.persistence;

import se.vgregion.delegation.domain.HealthCareUnit;
import se.vgregion.delegation.domain.HsaCommission;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-11-08 18:09
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public interface HsaCommissionDao {
    List<HsaCommission> findAll(String memberHsaIdentity);
    List<HsaCommission> resolve(HealthCareUnit vardEnhet);
}
