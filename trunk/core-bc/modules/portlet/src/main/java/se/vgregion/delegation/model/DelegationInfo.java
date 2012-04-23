package se.vgregion.delegation.model;

import se.vgregion.delegation.domain.DelegationBlock;
import se.vgregion.delegation.domain.HealthCareUnit;
import se.vgregion.delegation.domain.VardEnhetInfo;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-11-07 15:47
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class DelegationInfo {

    private HealthCareUnit vardEnhet;
    private VardEnhetInfo vardEnhetInfo;
    private DelegationBlock delegation;
    private DelegationBlock activeDelegation;
    private List<DelegationBlock> delegationHistory;

    public DelegationBlock getActiveDelegation() {
        return activeDelegation;
    }

    public void setActiveDelegation(DelegationBlock activeDelegation) {
        this.activeDelegation = activeDelegation;
    }

    public DelegationBlock getDelegation() {
        return delegation;
    }

    public void setDelegation(DelegationBlock delegation) {
        this.delegation = delegation;
    }

    public List<DelegationBlock> getDelegationHistory() {
        return delegationHistory;
    }

    public void setDelegationHistory(List<DelegationBlock> delegationHistory) {
        this.delegationHistory = delegationHistory;
    }

    public HealthCareUnit getVardEnhet() {
        return vardEnhet;
    }

    public void setVardEnhet(HealthCareUnit vardEnhet) {
        this.vardEnhet = vardEnhet;
    }

    public VardEnhetInfo getVardEnhetInfo() {
        return vardEnhetInfo;
    }

    public void setVardEnhetInfo(VardEnhetInfo vardEnhetInfo) {
        this.vardEnhetInfo = vardEnhetInfo;
    }
}
