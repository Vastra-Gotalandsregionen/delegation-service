package se.vgregion.delegation.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.collections.BeanMap;

import se.vgregion.dao.domain.patterns.entity.AbstractEntity;

/**
 * 
 * @author Simon Göransson
 * @author Claes Lundahl
 */
@Entity
@Table(name = "vgr_delegation")
public class Delegation extends AbstractEntity<Long> implements
        se.vgregion.dao.domain.patterns.entity.Entity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long delegationKey;

    @ManyToOne
    private DelegationBlock delegationBlock;

    @Column(nullable = false)
    private String delegatedFor;
    @Column(nullable = false)
    private String delegateTo;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date validFrom;
    @Column(name = "validTo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validTo;

    private String role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DelegationStatus status;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getDelegationKey() {
        return delegationKey;
    }

    public void setDelegationKey(Long delegationKey) {
        this.delegationKey = delegationKey;
    }

    public static void main(String[] args) {
        Delegation d = new Delegation();
        d.setDelegatedFor("df");
        d.setDelegateTo("dt");
        d.setValidTo(new Date());
        d.setId(-1l);
        d.setValidFrom(new Date());
        d.setRole("role");
        d.setStatus(DelegationStatus.ACTIVE);
        d.setDelegationKey(-1L);

        BeanMap bm = new BeanMap(d);
        Set keys = new HashSet();
        keys.addAll(bm.keySet());
        keys.remove("class");
        StringBuilder def = new StringBuilder("insert into vgr_" + Delegation.class.getSimpleName() + " ( ");
        StringBuilder val = new StringBuilder(" values (");
        for (Object o : keys) {
            Object v = bm.get(o);
            if (v != null) {
                def.append(o + ", ");
                val.append("'" + format(bm.get(o)) + "', ");
            }
        }
        def.delete(def.length() - 2, def.length());
        val.delete(val.length() - 2, val.length());

        System.out.println(def + ") " + val + ");");
    }

    private static String format(Object o) {
        if (o instanceof Date) {
            Date d = (Date) o;
            // 2011-09-01 10:00:00.0
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0");
            return sdf.format(d);
        }
        return o + "";
    }

    /**
     * @param delegationBlock
     *            the delegationBlock to set
     */
    public void setDelegationBlock(DelegationBlock delegationBlock) {
        this.delegationBlock = delegationBlock;
    }

    /**
     * @return the delegationBlock
     */
    public DelegationBlock getDelegationBlock() {
        return delegationBlock;
    }

    public DelegationStatus getStatus() {
        return status;
    }

    public void setStatus(DelegationStatus status) {
        this.status = status;
    }

}
