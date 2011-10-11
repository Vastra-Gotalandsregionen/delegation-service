/**
 * Copyright 2010 Västra Götalandsregionen
 *
 *   This library is free software; you can redistribute it and/or modify
 *   it under the terms of version 2.1 of the GNU Lesser General Public
 *   License as published by the Free Software Foundation.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the
 *   Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 *   Boston, MA 02111-1307  USA
 *
 */

package se.vgregion.delegation.domain;

import org.hibernate.annotations.Type;
import se.vgregion.dao.domain.patterns.entity.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;
import java.util.List;


/**
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
@Entity
@Table(name = "vgr_delegation")
public class Delegation extends AbstractEntity<Long> implements se.vgregion.dao.domain.patterns.entity.Entity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String createdBy;
    private String signedBy;

    private String delegatedBy;
    private String delegatedFor;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "vgr_delegate_to", joinColumns = @JoinColumn(name = "delegation_id"))
    @Column(name = "delegation_delegateto")
    private List<String> delegateTo;

    @Column(unique = false, nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date approved;
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

    public Date getApproved() {
        return approved;
    }

    public void setApproved(Date approved) {
        this.approved = approved;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    public List<String> getDelegateTo() {
        return delegateTo;
    }

    public void setDelegateTo(List<String> delegateTo) {
        this.delegateTo = delegateTo;
    }

    public String getSignedBy() {
        return signedBy;
    }

    public void setSignedBy(String signedBy) {
        this.signedBy = signedBy;
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
        return "Delegation{" +
                "id=" + id +
                ", approved=" + approved +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                ", createdBy='" + createdBy + '\'' +
                ", signedBy='" + signedBy + '\'' +
                ", delegatedBy='" + delegatedBy + '\'' +
                ", delegatedFor='" + delegatedFor + '\'' +
                ", delegateTo=" + delegateTo +
                ", created=" + created +
                '}';
    }
}
