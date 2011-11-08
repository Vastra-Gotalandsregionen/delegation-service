package se.vgregion.delegation.model;

import se.vgregion.delegation.domain.Delegation;
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
    private Delegation delegation;
    private Delegation activeDelegation;
    private List<Delegation> delegationHistory;

    public Delegation getActiveDelegation() {
        return activeDelegation;
    }

    public void setActiveDelegation(Delegation activeDelegation) {
        this.activeDelegation = activeDelegation;
    }

    public Delegation getDelegation() {
        return delegation;
    }

    public void setDelegation(Delegation delegation) {
        this.delegation = delegation;
    }

    public List<Delegation> getDelegationHistory() {
        return delegationHistory;
    }

    public void setDelegationHistory(List<Delegation> delegationHistory) {
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
