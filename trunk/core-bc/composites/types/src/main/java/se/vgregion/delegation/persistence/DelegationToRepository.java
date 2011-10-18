package se.vgregion.delegation.persistence;

import se.vgregion.dao.domain.patterns.repository.Repository;
import se.vgregion.delegation.domain.DelegationTo;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-10-18 19:36
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public interface DelegationToRepository extends Repository<DelegationTo, Long> {
    List<DelegationTo> delegateTo(String vgrId);
    List<DelegationTo> delegatedOn(String vgrId, Date on);
}
