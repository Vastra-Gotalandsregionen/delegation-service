package se.vgregion.delegation.domain;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import se.vgregion.dao.domain.patterns.entity.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-11-09 12:30
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
@Entity
@Table(name = "vgr_uppdrag_action")
public class UppdragVardEnhetAction extends AbstractEntity<Long>
        implements se.vgregion.dao.domain.patterns.entity.Entity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChangeType changeType;

    /**
     * hsaIdentity of hsaCommission.
     */
    @Column(nullable = false)
    private String hsaIdentity;

    private String hsaCommissionPurpose;

    private String hsaCommissionRight;

    private String hsaCommissionMember;

    private String cn;

    private String description;

    @Column(nullable = false)
    private String createdBy;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    private UppdragVardEnhet uppdragVardEnhet;

    @PrePersist
    private void onCreate() {
        created = new Date();
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        changeAllowed();
        this.cn = cn;
    }

    public Date getCreated() {
        return (created != null) ? new DateTime(created).toDate() : null;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        changeAllowed();
        this.createdBy = createdBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        changeAllowed();
        this.description = description;
    }

    public String getHsaCommissionMember() {
        return hsaCommissionMember;
    }

    public void setHsaCommissionMember(String hsaCommissionMember) {
        changeAllowed();
        this.hsaCommissionMember = hsaCommissionMember;
    }

    public String getHsaCommissionPurpose() {
        return hsaCommissionPurpose;
    }

    public void setHsaCommissionPurpose(String hsaCommissionPurpose) {
        changeAllowed();
        this.hsaCommissionPurpose = hsaCommissionPurpose;
    }

    public String getHsaCommissionRight() {
        return hsaCommissionRight;
    }

    public void setHsaCommissionRight(String hsaCommissionRight) {
        changeAllowed();
        this.hsaCommissionRight = hsaCommissionRight;
    }

    public String getHsaIdentity() {
        return hsaIdentity;
    }

    public void setHsaIdentity(String hsaIdentity) {
        changeAllowed();
        this.hsaIdentity = hsaIdentity;
    }

    public ChangeType getType() {
        return changeType;
    }

    public void setType(ChangeType type) {
        changeAllowed();
        this.changeType = type;
    }

    public UppdragVardEnhet getUppdragVardEnhet() {
        return uppdragVardEnhet;
    }

    public void setUppdragVardEnhet(UppdragVardEnhet uppdragVardEnhet) {
        changeAllowed();
        this.uppdragVardEnhet = uppdragVardEnhet;
    }

    public void changeAllowed() {
        if (uppdragVardEnhet != null && StringUtils.isNotBlank(uppdragVardEnhet.getSignToken())) {
            String msg = String.format("The UVE change-order [%s] is signed and change is not allowed",
                    uppdragVardEnhet.getId());
            throw new IllegalAccessError(msg);
        }
    }
}
