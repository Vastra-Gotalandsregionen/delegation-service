package se.vgregion.delegation.domain;

import se.vgregion.dao.domain.patterns.entity.AbstractEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

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

    @Enumerated(EnumType.STRING)
    private DelegationStatus status;

    private String signToken;

    private String delegatedBy;
    private String delegatedFor;

    @OneToMany(mappedBy = "delegationBy")
    private Collection<DelegationTo> delegationsTo;

    @Column(unique = false, nullable = true, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvedOn;

    @Column(unique = false, nullable = true, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date revokedOn;

    @Column(unique = false, nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public void addDelegationEntry(DelegationTo delegationTo) {
        if (!getDelegationsTo().contains(delegationTo)) {
            getDelegationsTo().add(delegationTo);
            delegationTo.setDelegationBy(this);
        }
    }

    @Override
    public Long getId() {
        return id;
    }

    public Date getApprovedOn() {
        return approvedOn;
    }

    public void setApprovedOn(Date approvedOn) {
        this.approvedOn = approvedOn;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getDelegatedBy() {
        return delegatedBy;
    }

    public void setDelegatedBy(String delegatedBy) {
        this.delegatedBy = delegatedBy;
    }

    public String getDelegatedFor() {
        return delegatedFor;
    }

    public void setDelegatedFor(String delegatedFor) {
        this.delegatedFor = delegatedFor;
    }

    public Collection<DelegationTo> getDelegationsTo() {
        return delegationsTo;
    }

    public Date getRevokedOn() {
        return revokedOn;
    }

    public void setRevokedOn(Date revokedOn) {
        this.revokedOn = revokedOn;
    }

    public String getSignToken() {
        return signToken;
    }

    public void setSignToken(String signToken) {
        this.signToken = signToken;
    }

    public DelegationStatus getStatus() {
        return status;
    }

    public void setStatus(DelegationStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Delegation{" +
                "id=" + id +
                ", delegatedBy='" + delegatedBy + '\'' +
                ", delegatedFor='" + delegatedFor + '\'' +
                ", status=" + status +
                ", approvedOn=" + approvedOn +
                ", signToken='" + signToken + '\'' +
                ", revokedOn=" + revokedOn +
                ", delegationsTo=" + delegationsTo +
                ", created=" + created +
                '}';
    }
}
