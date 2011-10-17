package se.vgregion.delegation.domain;

import se.vgregion.dao.domain.patterns.entity.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
@Entity
@Table(name = "vgr_delegation_to")
public class DelegationTo extends AbstractEntity<Long>
        implements se.vgregion.dao.domain.patterns.entity.Entity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Delegation delegationBy;

    private String delegateTo;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date validFrom;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date validTo;

    @Override
    public Long getId() {
        return id;
    }

    public String getDelegateTo() {
        return delegateTo;
    }

    public void setDelegateTo(String delegateTo) {
        this.delegateTo = delegateTo;
    }

    public Delegation getDelegationBy() {
        return delegationBy;
    }

    public void setDelegationBy(Delegation delegationBy) {
        this.delegationBy = delegationBy;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    @Override
    public String toString() {
        return "DelegationTo{" +
                "id=" + id +
                ", delegationBy=" + delegationBy.getId() +
                ", delegateTo='" + delegateTo + '\'' +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                '}';
    }
}
