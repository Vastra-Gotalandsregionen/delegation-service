package se.vgregion.delegation;

import org.joda.time.DateTime;
import se.vgregion.delegation.domain.Delegation;
import se.vgregion.delegation.domain.HealthCareUnit;
import se.vgregion.delegation.domain.VardEnhetInfo;

import java.util.List;
import java.util.Set;

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
     * @return - the active delegation
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
     * Fetch the delegation made by Verksamhets-Chef, that where active on a specific date.
     *
     * @param vcVgrId - vgrId for Verksamhets-Chef
     * @param on - the date
     * @return - the delegations that where active on a specific date
     */
    Delegation delegatedBy(String vcVgrId, DateTime on);

    Delegation pendingDelegation(String vcVgrId);

    List<Delegation> delegatedTo(String vgrId);

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

    Delegation find(Long delegationId);

    List<VardEnhetInfo> lookupVerksamhetsChefInfo(String vcVgrId);

    Set<HealthCareUnit> findAllVardEnhet();
}
