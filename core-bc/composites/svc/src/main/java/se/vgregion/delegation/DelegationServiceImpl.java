package se.vgregion.delegation;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import se.vgregion.delegation.domain.Delegation;
import se.vgregion.delegation.persistence.DelegationRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: 10/10-11
 * Time: 16:20
 */
public class DelegationServiceImpl implements DelegationService {
    @Autowired
    private DelegationRepository delegationRepository;

    @Override
    public List<Delegation> delegatedBy(String vcVgrId) {
        return delegationRepository.delegatedBy(vcVgrId);
    }

    @Override
    public List<Delegation> delegatedTo(String vgrId) {
        return new ArrayList<Delegation>();
    }

    @Override
    public List<Delegation> delegationsInProgress(String vcVgrId) {
        return new ArrayList<Delegation>();
    }

    @Override
    public boolean approve(Delegation delegation, String signToken) {
        return false;
    }

    @Override
    public Delegation extend(Long oldDelegationId, String createdBy, DateTime newFrom, DateTime newTo) {
        Delegation newDelegation = new Delegation();
        return newDelegation;
    }

    /**
     * Validate that signToken is signed by signer.
     *
     * @param delegation
     * @param signToken
     * @return
     */
    private boolean validateSignToken(Delegation delegation, String signToken) {
        return false;
    }
}
