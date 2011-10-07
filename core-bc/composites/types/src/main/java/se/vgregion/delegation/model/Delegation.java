package se.vgregion.delegation.model;

import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import se.vgregion.dao.domain.patterns.entity.*;

import javax.persistence.*;
import javax.persistence.Entity;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: 7/10-11
 * Time: 14:48
 */
@Entity
@Table(name = "vgr_delegation")
public class Delegation extends AbstractEntity<Long> implements se.vgregion.dao.domain.patterns.entity.Entity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String createdBy;
    private String signedBy;

    private String delegateFrom;
    private String delegateFor;
    private String delegateTo;
    private String delegateToGroup;

    @Temporal(TemporalType.TIMESTAMP)
    private DateTime created;
    @Temporal(TemporalType.TIMESTAMP)
    private DateTime approved;
    @Temporal(TemporalType.TIMESTAMP)
    private DateTime validFrom;
    @Temporal(TemporalType.TIMESTAMP)
    private DateTime validTo;

    @Override
    public Long getId() {
        return id;
    }
}
