package se.vgregion.delegation.domain;

import se.vgregion.dao.domain.patterns.entity.AbstractEntity;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
@Entity
@Table(name = "vgr_delegation")
public class Delegation extends AbstractEntity<Long>
        implements se.vgregion.dao.domain.patterns.entity.Entity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DelegationStatus status;

    private String signToken;

    private String delegatedBy;

    @OneToMany(mappedBy = "delegation", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<DelegationTo> delegationsTo = new HashSet<DelegationTo>();

    @Column(nullable = true, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvedOn;

    @Column(nullable = true, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date revokedOn;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    /**
     * Adds an delegatonTo to this delegation.
     *
     * IMPORTANT: The call to changeAllowed are aligned so that all checks are made before any change.
     * @param delegationTo - the DelegationTo to be added.
     */
    public void addDelegationTo(DelegationTo delegationTo) {
        changeAllowed();
        Delegation oldDelegation = delegationTo.getDelegation();
        if (oldDelegation != null && oldDelegation.getDelegationsTo().contains(delegationTo)) {
            oldDelegation.removeDelegationTo(delegationTo);
        }
        delegationTo.setDelegation(this);
        delegationsTo.add(delegationTo);
    }

    /**
     * Remove an delegatonTo from this delegation.
     *
     * IMPORTANT: The call to changeAllowed are aligned so that all checks are made before any change.
     * @param delegationTo - the DelegationTo to be added.
     */
    public void removeDelegationTo(DelegationTo delegationTo) {
        changeAllowed();
        Delegation oldDelegation = delegationTo.getDelegation();
        if (oldDelegation != null
                && oldDelegation != this
                && oldDelegation.getDelegationsTo().contains(delegationTo)) {
            oldDelegation.removeDelegationTo(delegationTo);
        }
        delegationTo.setDelegation(null);
        delegationsTo.remove(delegationTo);
    }

    @PrePersist
    private void onCreate() {
        created = new Date();
    }

    @Override
    public Long getId() {
        return id;
    }

    public Date getApprovedOn() {
        return approvedOn;
    }

    public void setApprovedOn(Date approvedOn) {
        changeAllowed();
        this.approvedOn = approvedOn;
    }

    public Date getCreated() {
        return created;
    }

    public String getDelegatedBy() {
        return delegatedBy;
    }

    public void setDelegatedBy(String delegatedBy) {
        changeAllowed();
        this.delegatedBy = delegatedBy;
    }

    /**
     * Protected access to DelegationsTo handled.
     * Use addDelegationTo, removeDelegationTo to change delegationsTo.
     *
     * @return An unmodifiable representation of delegations.
     */
    public Set<DelegationTo> getDelegationsTo() {
//        return Collections.unmodifiableSet(delegationsTo);
        return delegationsTo;
    }

    public Date getRevokedOn() {
        return revokedOn;
    }

    public void setRevokedOn(Date revokedOn) {
        changeAllowed();
        this.revokedOn = revokedOn;
    }

    public String getSignToken() {
        return signToken;
    }

    public void setSignToken(String signToken) {
        changeAllowed();
        this.signToken = signToken;
    }

    public DelegationStatus getStatus() {
        return status;
    }

    public void setStatus(DelegationStatus status) {
        if (this.status != null) changeAllowed();
        this.status = status;
    }

    @Override
    public String toString() {
        return "Delegation{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", delegatedBy='" + delegatedBy + '\'' +
                ", approvedOn=" + approvedOn +
                ", signToken='" + signToken + '\'' +
                ", revokedOn=" + revokedOn +
                ", delegationsTo=" + delegationsTo +
                ", created=" + created +
                '}';
    }

    private void changeAllowed() {
        if (status != DelegationStatus.PENDING) {
            String msg = String.format("No change allowed, the delegation [%s - %s] is locked.",
                    id != null ? id : "", status.toString());
            throw new IllegalAccessError(msg);
        }
    }
}
