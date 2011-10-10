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
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import se.vgregion.dao.domain.patterns.entity.*;

import javax.persistence.*;
import javax.persistence.Entity;
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

    private String delegateFrom;
    private String delegateFor;
    @ElementCollection
    private List<String> delegateTo;

    @Column(unique = false, nullable = false, updatable = false)
    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    private DateTime created;
    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    private DateTime approved;
    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    private DateTime validFrom;
    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    private DateTime validTo;

    @Override
    public Long getId() {
        return id;
    }


}
