package se.vgregion.delegation.domain;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import se.vgregion.dao.domain.patterns.entity.AbstractEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-11-09 10:10
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
@Entity
@Table(name = "vgr_uppdrag")
public class UppdragVardEnhet extends AbstractEntity<Long>
        implements se.vgregion.dao.domain.patterns.entity.Entity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    /**
     * hsaIdentity for VårdEnhet.
     */
    @Column(nullable = false)
    private String vardEnhet;

    /**
     * Token returned from signing this batch.
     */
    private String signToken;

    /**
     * HsaIdentity of person signing the batch.
     */
    private String approvedBy;

    @Column(unique = false, nullable = true, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvedOn;

    /**
     * The batch of change actions.
     */
    @OneToMany(mappedBy = "uppdragVardEnhet", cascade = CascadeType.ALL)
    private Set<UppdragVardEnhetAction> uppdragVardEnhetActions;

    /**
     * Adds an action to this change-set.
     *
     * IMPORTANT: The call to changeAllowed are aligned so that a check are made before trying to make and change.
     * @param action - the action to be removed.
     */
    public void addAction(UppdragVardEnhetAction action) {
        changeAllowed();
        UppdragVardEnhet oldUppdrag = action.getUppdragVardEnhet();
        if (oldUppdrag != null && oldUppdrag.getUppdragVardEnhetActions().contains(action)) {
            oldUppdrag.removeAction(action);
        }
        action.setUppdragVardEnhet(this);
        uppdragVardEnhetActions.add(action);
    }

    /**
     * Removes an action to this change-set.
     *
     * IMPORTANT: The call to changeAllowed are aligned so that a check are made before trying to make and change.
     * @param action - the action to be removed.
     */
    public void removeAction(UppdragVardEnhetAction action) {
        changeAllowed();
        UppdragVardEnhet oldUppdrag = action.getUppdragVardEnhet();
        if (oldUppdrag != null && oldUppdrag.getUppdragVardEnhetActions().contains(action)) {
            oldUppdrag.removeAction(action);
        }
        action.setUppdragVardEnhet(null);
        uppdragVardEnhetActions.remove(action);
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        changeAllowed();
        this.approvedBy = approvedBy;
    }

    /**
     * Gard date from modification.
     *
     * @return when signed.
     */
    public Date getApprovedOn() {
        return (approvedOn != null) ? new DateTime(approvedOn).toDate() : null;
    }

    /**
     * When signed approvedOn should not be modifiable.
     *
     * @param approvedOn - timestamp when signed.
     */
    public void setApprovedOn(Date approvedOn) {
        changeAllowed();
        this.approvedOn = approvedOn;
    }

    public String getSignToken() {
        return signToken;
    }

    public void setSignToken(String signToken) {
        changeAllowed();
        this.signToken = signToken;
    }

    /**
     * Protected access to actions handled by this change-order.
     *
     * @return An unmodifiable representation of handled actions.
     */
    public Set<UppdragVardEnhetAction> getUppdragVardEnhetActions() {
        return Collections.unmodifiableSet(uppdragVardEnhetActions);
    }

    public String getVardEnhet() {
        return vardEnhet;
    }

    public void setVardEnhet(String vardEnhet) {
        changeAllowed();
        this.vardEnhet = vardEnhet;
    }

    public void changeAllowed() {
        if (StringUtils.isNotBlank(signToken)) {
            String msg = String.format("The UVE change-order [%s] is signed and change is not allowed", id);
            throw new IllegalAccessError(msg);
        }
    }
}
