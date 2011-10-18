package se.vgregion.delegation.persistence.jpa;

import se.vgregion.dao.domain.patterns.repository.db.jpa.DefaultJpaRepository;
import se.vgregion.delegation.domain.Delegation;
import se.vgregion.delegation.domain.DelegationStatus;
import se.vgregion.delegation.domain.DelegationTo;
import se.vgregion.delegation.persistence.DelegationToRepository;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-10-18 19:41
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class JpaDelegationToRepository extends DefaultJpaRepository<DelegationTo, Long>
        implements DelegationToRepository {

    @Override
    public List<DelegationTo> delegateTo(String vgrId) {
        Date now = new Date();
        String query = "SELECT t FROM DelegationTo t JOIN t.delegation d " +
                "WHERE t.delegateTo = :delegateTo" +
                " AND t.validFrom < :time " +
                " AND (t.validTo > :time OR t.validTo IS NULL) " +
                " AND d.approvedOn < :time" +
                " AND d.revokedOn IS NULL" +
                " AND d.status = :active" +
                "";
        Query q = entityManager.createQuery(query);

        return q.setParameter("delegateTo", vgrId)
                .setParameter("time", now)
                .setParameter("active", DelegationStatus.ACTIVE)
                .getResultList();
    }

    @Override
    public List<DelegationTo> delegatedOn(String vgrId, Date time) {
        String query = "SELECT t FROM DelegationTo t JOIN t.delegation d " +
                "WHERE t.delegateTo = :delegateTo" +
                " AND t.validFrom < :time " +
                " AND (t.validTo > :time OR t.validTo IS NULL) " +
                " AND (" +
                "  (d.approvedOn < :time AND d.revokedOn > :time AND d.status = :superseded)" +
                "  OR (d.approvedOn < :time AND d.revokedOn IS NULL AND d.status = :active)" +
                " )" +
                "";
        Query q = entityManager.createQuery(query);

        return q.setParameter("delegateTo", vgrId)
                .setParameter("time", time)
                .setParameter("superseded", DelegationStatus.SUPERSEDED)
                .setParameter("active", DelegationStatus.ACTIVE)
                .getResultList();
    }
}
