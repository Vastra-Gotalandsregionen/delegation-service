package se.vgregion.delegation;

import com.sun.tools.internal.ws.wsdl.framework.NoSuchEntityException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import se.vgregion.delegation.domain.Delegation;
import se.vgregion.delegation.persistence.DelegationRepository;
import se.vgregion.delegation.persistence.DelegationToRepository;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class DelegationServiceImpl implements DelegationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DelegationServiceImpl.class);

    @Autowired
    private DelegationRepository delegationRepository;

    @Autowired
    private DelegationToRepository delegationToRepository;

    @Override
    public Delegation activeDelegations(String vcVgrId) {
        try {
            return delegationRepository.activeDelegation(vcVgrId);
        } catch (NoResultException ex) {
            return null;
        } catch (NonUniqueResultException ex) {
            LOGGER.error("Delegation databas is in an inconsistent state, " +
                    "multiple ACTIVE delegations for VerksamhetChef ["+vcVgrId+"]");
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Delegation> delegatedBy(String vcVgrId) {
        return delegationRepository.delegatedBy(vcVgrId);
    }

    @Override
    public Delegation delegatedBy(String vcVgrId, DateTime on) {
        try {
            return delegationRepository.delegatedOn(vcVgrId, new Date(on.getMillis()));
        } catch (NoResultException ex) {
            return null;
        } catch (NonUniqueResultException ex) {
            LOGGER.error("Delegation databas is in an inconsistent state, " +
                    "there where multiple ACTIVE delegations for VerksamhetChef ["+vcVgrId+"] on ["+on+"]");
            throw new RuntimeException(ex);
        }
    }

    /**
     * Fetch the pending delegation made by Verksamhets-Chef.
     * If no pending delegation found, copy active delegation and return that.
     *
     * @param vcVgrId - vgrId for Verksamhets-Chef
     * @return
     */
    @Override
    public Delegation pendingDelegation(String vcVgrId) {
        Delegation delegation = null;
        try {
            delegation = delegationRepository.pendingDelegation(vcVgrId);
        } catch (NoResultException ex) {
            Delegation activeDelegation = activeDelegations(vcVgrId);
            if (activeDelegation == null) {
                delegation = new Delegation();
            } else {
                delegation = delegationRepository.clone(activeDelegation);
            }
        } catch (NonUniqueResultException ex) {
            LOGGER.error("Delegation databas is in an inconsistent state, " +
                    "there where multiple PENDING delegations for VerksamhetChef ["+vcVgrId+"]");
            throw new RuntimeException(ex);
        }
        return delegation;
    }

    @Override
    public List<Delegation> delegatedTo(String vgrId) {
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

    @Override
    public Delegation find(Long delegationId) {
        return delegationRepository.find(delegationId);
    }

    /**
     * Validate that signToken is signed by signer.
     *
     * @param delegation
     * @return
     */
    private boolean validateSigning(Delegation delegation) {
        return false;
    }
}
