/**
 * Copyright 2010 Västra Götalandsregionen
 *
 *   This library is free software; you can redistribute it and/or modify
 *   it under the terms of version 2.1 of the GNU Lesser General Public
 *   License as published by the Free Software Foundation.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the
 *   Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 *   Boston, MA 02111-1307  USA
 *
 */

package se.vgregion.delegation.persistence.jpa;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import se.vgregion.dao.domain.patterns.repository.db.jpa.DefaultJpaRepository;
import se.vgregion.delegation.domain.Delegation;
import se.vgregion.delegation.domain.DelegationStatus;
import se.vgregion.delegation.domain.DelegationTo;
import se.vgregion.delegation.persistence.DelegationRepository;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-10-18 11:29
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
@Repository
public class JpaDelegationRepository extends DefaultJpaRepository<Delegation, Long>
        implements DelegationRepository {

    @Override
    public Delegation activeDelegation(String vcVgrId) {
        Date now = new Date();
        String query = "SELECT d FROM Delegation d " +
                "WHERE d.delegatedBy = :delegatedBy" +
                " AND d.approvedOn < :time" +
                " AND d.revokedOn IS NULL" +
                " AND d.status = :active" +
                "";
        Query q = entityManager.createQuery(query);

        return (Delegation) q.setParameter("delegatedBy", vcVgrId)
                .setParameter("time", now)
                .setParameter("active", DelegationStatus.ACTIVE)
                .getSingleResult();
    }

    @Override
    public List<Delegation> delegatedBy(String vcVgrId) {
        String query = "SELECT d FROM Delegation d " +
                "WHERE d.delegatedBy = :delegatedBy" +
                "";
        Query q = entityManager.createQuery(query);

        return q.setParameter("delegatedBy", vcVgrId).getResultList();
    }

    @Override
    public Delegation delegatedOn(String vcVgrId, Date time) {
        String query = "SELECT d FROM Delegation d " +
                "WHERE d.delegatedBy = :delegatedBy" +
                " AND (" +
                "  (d.approvedOn < :time AND d.revokedOn > :time AND d.status = :superseded)" +
                "  OR (d.approvedOn < :time AND d.revokedOn IS NULL AND d.status = :active)" +
                " )" +
                "";
        Query q = entityManager.createQuery(query);

        return (Delegation) q.setParameter("delegatedBy", vcVgrId)
                .setParameter("time", time)
                .setParameter("superseded", DelegationStatus.SUPERSEDED)
                .setParameter("active", DelegationStatus.ACTIVE)
                .getSingleResult();
    }

    @Override
    public Delegation pendingDelegation(String vcVgrId) {
        String query = "SELECT d FROM Delegation d " +
                "WHERE d.delegatedBy = :delegatedBy" +
                " AND d.status = :in_progress" +
                "";
        Query q = entityManager.createQuery(query);

        return (Delegation) q.setParameter("delegatedBy", vcVgrId)
                .setParameter("in_progress", DelegationStatus.PENDING)
                .getSingleResult();
    }

    @Override
    public Delegation clone(Delegation delegation) {
        Delegation pending = new Delegation();
        pending.setStatus(DelegationStatus.PENDING);

        pending.setDelegatedBy(delegation.getDelegatedBy());
        for (DelegationTo delegationTo: delegation.getDelegationsTo()) {
            delegationTo.clearId();
            pending.addDelegationTo(delegationTo);
        }
        return pending;
    }
}