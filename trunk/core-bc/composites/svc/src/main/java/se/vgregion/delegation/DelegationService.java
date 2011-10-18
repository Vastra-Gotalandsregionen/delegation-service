package se.vgregion.delegation;

import org.joda.time.DateTime;
import se.vgregion.delegation.domain.Delegation;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: 10/10-11
 * Time: 16:43
 */
public interface DelegationService {
    /**
     * Fetch currently active delegations made by Verksamhets-Chef.
     *
     * @param vcVgrId - vgrId for Verksamhets-Chef
     * @return - a list of active delegations
     */
    Delegation activeDelegations(String vcVgrId);

    /**
     * Fetch all delegations made by Verksamhets-Chef.
     *
     * @param vcVgrId - vgrId for Verksamhets-Chef
     * @return - a list of all delegations
     */
    List<Delegation> delegatedBy(String vcVgrId);

    /**
     * Fetch delegations that where active on a specific date made by Verksamhets-Chef.
     *
     * @param vcVgrId - vgrId for Verksamhets-Chef
     * @param on - the date
     * @return - a list of delegations that where active on a specific date
     */
    Delegation delegatedBy(String vcVgrId, DateTime on);

    List<Delegation> delegatedTo(String vgrId);

    List<Delegation> delegationsInProgress(String vcVgrId);

    boolean approve(Delegation delegation, String signToken);

    /**
     * Convenience method to extend an existing Delegation.
     * The new Delegation has to be signed to be valid.
     *
     * @param oldDelegationId
     * @param createdBy
     * @param newFrom
     * @param newTo
     * @return
     */
    Delegation extend(Long oldDelegationId, String createdBy, DateTime newFrom, DateTime newTo);
}
