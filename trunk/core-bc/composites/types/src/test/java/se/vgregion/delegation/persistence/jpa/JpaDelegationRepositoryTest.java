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

package se.vgregion.delegation.persistence.jpa;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import se.vgregion.delegation.domain.Delegation;
import se.vgregion.delegation.persistence.DelegationRepository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static se.vgregion.hamcrest.ComparableMatcher.greaterThan;
import static se.vgregion.hamcrest.ComparableMatcher.lessThan;

/**
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
@ContextConfiguration("classpath:JpaRepositoryTest-context.xml")
public class JpaDelegationRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private DelegationRepository jpaDelegationRepository;

    @Before
    public void setUp() throws Exception {
        executeSqlScript("classpath:dbsetup/test-data.sql", false);
    }

    @Test
    public void testFindAll() {
        Collection<Delegation> delegations = jpaDelegationRepository.findAll();
        assertEquals(8, delegations.size());

        for (Delegation delegation: delegations) {
            System.out.println(delegation);
        }
    }


    @Test
    public void testFindDelegatedBy() {
        DateTime dateTime = new DateTime(2011, 10, 2, 10, 0, 0, 0);
        Date on = new Date(dateTime.getMillis());
        System.out.println(on);
        List<Delegation> delegations = jpaDelegationRepository.delegatedBy("delegatedBy", on);

        assertEquals(1, delegations.size());
    }
}
