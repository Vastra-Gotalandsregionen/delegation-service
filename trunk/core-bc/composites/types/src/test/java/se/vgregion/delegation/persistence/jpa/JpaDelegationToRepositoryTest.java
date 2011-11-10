package se.vgregion.delegation.persistence.jpa;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import se.vgregion.delegation.domain.Delegation;
import se.vgregion.delegation.domain.DelegationTo;
import se.vgregion.delegation.persistence.DelegationToRepository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-10-18 19:59
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
@ContextConfiguration("classpath:JpaRepositoryTest-context.xml")
public class JpaDelegationToRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private DelegationToRepository delegationToRepository;

    @Before
    public void setUp() throws Exception {
        executeSqlScript("classpath:dbsetup/test-data-delegation.sql", false);
    }

    @Test
    public void testFindAll() {
        Collection<DelegationTo> delegationTos = delegationToRepository.findAll();
        assertEquals(15, delegationTos.size());

        for (DelegationTo delegationTo: delegationTos) {
            System.out.println(delegationTo);
        }
    }

    @Test
    public void testDelegatedTo() {
        List<DelegationTo> delegationTos = delegationToRepository.delegateTo("apa");
        assertEquals(2, delegationTos.size());
        assertEquals(new Long(-11), delegationTos.get(0).getId());
        assertEquals(new Long(-4), delegationTos.get(1).getId());
    }

    @Test
    public void testDelegatedOn() {
        DateTime dateTime = new DateTime(2011, 9, 2, 10, 0, 0, 0);
        Date on = new Date(dateTime.getMillis());
        List<DelegationTo> delegationTos = delegationToRepository.delegatedOn("apa", on);
        assertEquals(1, delegationTos.size());
        assertEquals(new Long(-1), delegationTos.get(0).getId());
    }

    @Test
    public void testDelegatedOn2() {
        DateTime dateTime = new DateTime(2011, 9, 10, 10, 0, 0, 0);
        Date on = new Date(dateTime.getMillis());
        List<DelegationTo> delegationTos = delegationToRepository.delegatedOn("apa", on);

        assertEquals(1, delegationTos.size());
        assertEquals(new Long(-1), delegationTos.get(0).getId());
    }

    @Test
    public void testDelegatedOn3() {
        DateTime dateTime = new DateTime(2011, 9, 11, 10, 0, 0, 0);
        Date on = new Date(dateTime.getMillis());
        List<DelegationTo> delegationTos = delegationToRepository.delegatedOn("apa", on);

        assertEquals(2, delegationTos.size());
        assertEquals(new Long(-11), delegationTos.get(0).getId());
        assertEquals(new Long(-1), delegationTos.get(1).getId());
    }

    @Test
    public void testDelegatedOn4() {
        DateTime dateTime = new DateTime(2011, 9, 11, 10, 0, 0, 0);
        Date on = new Date(dateTime.getMillis());
        List<DelegationTo> delegationTos = delegationToRepository.delegatedOn("bepa", on);

        assertEquals(1, delegationTos.size());
        assertEquals(new Long(-2), delegationTos.get(0).getId());
    }

    @Test
    public void testDelegatedOn5() {
        DateTime dateTime = new DateTime(2011, 9, 16, 10, 0, 0, 0);
        Date on = new Date(dateTime.getMillis());
        List<DelegationTo> delegationTos = delegationToRepository.delegatedOn("bepa", on);

        assertEquals(0, delegationTos.size());
    }
}
