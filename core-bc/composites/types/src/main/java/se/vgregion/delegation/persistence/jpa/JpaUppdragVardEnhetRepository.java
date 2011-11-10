package se.vgregion.delegation.persistence.jpa;

import se.vgregion.dao.domain.patterns.repository.db.jpa.DefaultJpaRepository;
import se.vgregion.delegation.domain.UppdragVardEnhet;
import se.vgregion.delegation.persistence.UppdragVardEnhetRepository;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-11-09 13:08
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class JpaUppdragVardEnhetRepository extends DefaultJpaRepository<UppdragVardEnhet, Long>
        implements UppdragVardEnhetRepository {
}
