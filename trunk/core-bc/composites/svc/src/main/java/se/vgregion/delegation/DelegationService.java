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
    List<Delegation> delegatedBy(String vcVgrId);

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
