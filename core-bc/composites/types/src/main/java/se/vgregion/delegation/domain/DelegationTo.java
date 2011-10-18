package se.vgregion.delegation.domain;

import se.vgregion.dao.domain.patterns.entity.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-10-18 11:29
 *
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
    private Delegation delegation;

    private String delegatedFor;
    private String delegateTo;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date validFrom;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date validTo;

    public void clearId() {
        this.id = null;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getDelegatedFor() {
        return delegatedFor;
    }

    public void setDelegatedFor(String delegatedFor) {
        this.delegatedFor = delegatedFor;
    }

    public String getDelegateTo() {
        return delegateTo;
    }

    public void setDelegateTo(String delegateTo) {
        this.delegateTo = delegateTo;
    }

    public Delegation getDelegation() {
        return delegation;
    }

    public void setDelegation(Delegation delegation) {
        this.delegation = delegation;
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
                ", delegationBy=" + delegation.getId() +
                ", delegatedFor='" + delegatedFor + '\'' +
                ", delegateTo='" + delegateTo + '\'' +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                '}';
    }
}
