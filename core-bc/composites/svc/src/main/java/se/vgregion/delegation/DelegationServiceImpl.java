package se.vgregion.delegation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import se.vgregion.delegation.domain.Delegation;
import se.vgregion.delegation.domain.DelegationBlock;
import se.vgregion.delegation.persistence.DelegationBlockRepository;
import se.vgregion.delegation.persistence.DelegationRepository;

/**
 * @author Simon Göransson
 * @author Claes Lundahl
 * 
 */
public class DelegationServiceImpl implements DelegationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DelegationServiceImpl.class);

    @Autowired
    private DelegationBlockRepository delegationBlockRepository;

    @Autowired(required = false)
    private DelegationRepository delegationRepository;

    @Override
    public List<Delegation> getActiveDelegations(String delegatedFor) {
        return delegationRepository.getActiveDelegations(delegatedFor);

    }

    @Override
    public List<Delegation> getInActiveDelegations(String delegatedFor) {
        return delegationRepository.getInActiveDelegations(delegatedFor);
    }

    @Override
    public List<Delegation> getDelegations(String delegatedFor) {
        return delegationRepository.getDelegations(delegatedFor);
    }

    @Override
    public List<Delegation> getDelegationsByRole(String delegatedTo, String role) {
        return delegationRepository.getDelegationsByRole(delegatedTo, role);
    }

    @Override
    public Delegation getDelegation(Long delegationId) {
        return delegationRepository.getDelegation(delegationId);
    }

    @Override
    @Transactional
    public boolean save(DelegationBlock delegationBlock) {

        if (validateSigning(delegationBlock)) {
            delegationBlockRepository.store(delegationBlock);
            return true;
        }
        return false;
    }

    @Override
    public boolean hasDelegations(String delegatedFor, String delegatedTo, String role) {
        return delegationRepository.hasDelegations(delegatedFor, delegatedTo, role);
    }

    /**
     * Validate that signToken is signed by signer.
     * 
     * @param delegation
     * @return
     */
    private boolean validateSigning(DelegationBlock delegation) {
        // TODO check if the signtoken is valid using the signservice.
        if (delegation.getSignToken() != null) {
            return true;
        }
        return false;
    }

}
