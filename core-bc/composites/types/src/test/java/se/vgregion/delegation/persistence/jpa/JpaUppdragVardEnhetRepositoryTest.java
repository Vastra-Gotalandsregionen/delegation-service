package se.vgregion.delegation.persistence.jpa;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.vgregion.delegation.domain.ChangeType;
import se.vgregion.delegation.domain.UppdragVardEnhet;
import se.vgregion.delegation.domain.UppdragVardEnhetAction;
import se.vgregion.delegation.persistence.DelegationToRepository;
import se.vgregion.delegation.persistence.UppdragVardEnhetRepository;
import se.vgregion.hamcrest.ComparableMatcher;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-11-09 13:18
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
@ContextConfiguration("classpath:JpaRepositoryTest-context.xml")
public class JpaUppdragVardEnhetRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private UppdragVardEnhetRepository uppdragVardEnhetRepository;

    @Before
    public void setUp() throws Exception {
        executeSqlScript("classpath:dbsetup/test-data-uve.sql", false);
    }

    @Test
    public void testFindAll() {
        Collection<UppdragVardEnhet> uppdragVardEnhets = uppdragVardEnhetRepository.findAll();
        assertEquals(4, uppdragVardEnhets.size());
    }

    @Test
    public void testFindById() {
        UppdragVardEnhet uppdragVardEnhet = uppdragVardEnhetRepository.find(-1L);
        assertEquals(2, uppdragVardEnhet.getUppdragVardEnhetActions().size());

        uppdragVardEnhet = uppdragVardEnhetRepository.find(-2L);
        assertEquals(1, uppdragVardEnhet.getUppdragVardEnhetActions().size());

        uppdragVardEnhet = uppdragVardEnhetRepository.find(-3L);
        assertEquals(1, uppdragVardEnhet.getUppdragVardEnhetActions().size());

        uppdragVardEnhet = uppdragVardEnhetRepository.find(-4L);
        assertEquals(1, uppdragVardEnhet.getUppdragVardEnhetActions().size());
    }

    @Test
    public void testNewUppdragVardEnhet() {
        UppdragVardEnhet uppdrag = new UppdragVardEnhet();
        uppdrag.setVardEnhet("newVE");

        uppdragVardEnhetRepository.store(uppdrag);

        // Validate
        Collection<UppdragVardEnhet> check = uppdragVardEnhetRepository.findAll();
        assertEquals(5, check.size());
        for (UppdragVardEnhet ve : check) {
            assertNotNull(ve.getId());
        }

        String result = simpleJdbcTemplate.queryForObject("select vardEnhet from vgr_uppdrag" +
                " where id = 1", String.class);

        assertEquals("newVE", result);
    }

    @Test(expected = javax.persistence.PersistenceException.class)
    public void testNullVardenhetUppdragVardEnhet() {
        UppdragVardEnhet uppdrag = new UppdragVardEnhet();

        uppdragVardEnhetRepository.store(uppdrag);
    }

    @Test
    public void testAddMemberAction() {
        UppdragVardEnhet uppdragVardEnhet = uppdragVardEnhetRepository.find(-4L);
        uppdragVardEnhet.setVardEnhet("new"+uppdragVardEnhet.getVardEnhet());

        UppdragVardEnhetAction action = new UppdragVardEnhetAction();
        action.setType(ChangeType.ADD_MEMBER);
        action.setHsaIdentity("hsaS2");
        action.setHsaCommissionMember("hsaP6;;");
        action.setCreatedBy("test");


        uppdragVardEnhet.addAction(action);
        uppdragVardEnhetRepository.store(uppdragVardEnhet);
        uppdragVardEnhetRepository.flush();

        // Validate
        UppdragVardEnhet check = uppdragVardEnhetRepository.find(-4L);
        assertEquals(2, check.getUppdragVardEnhetActions().size());
        for (UppdragVardEnhetAction actionCheck : check.getUppdragVardEnhetActions()) {
            assertNotNull("action ["+actionCheck.getHsaIdentity()+"]", actionCheck.getId());
            assertNotNull("action ["+actionCheck.getId()+"]", actionCheck.getCreated());
        }

        Map result = simpleJdbcTemplate.queryForMap("select created from vgr_uppdrag_action" +
                " where id = 1 and uppdragVardEnhet_id = -4");

        System.out.println(result);
    }

    @Test(expected = javax.persistence.PersistenceException.class)
    public void testNullHsaIdentityAction() {
        UppdragVardEnhet uppdragVardEnhet = uppdragVardEnhetRepository.find(-4L);
        uppdragVardEnhet.setVardEnhet("new"+uppdragVardEnhet.getVardEnhet());

        UppdragVardEnhetAction action = new UppdragVardEnhetAction();
        action.setType(ChangeType.ADD_MEMBER);
        action.setHsaCommissionMember("hsaP6;;");
        action.setCreatedBy("test");

        uppdragVardEnhet.addAction(action);
        uppdragVardEnhetRepository.store(uppdragVardEnhet);
    }

    @Test(expected = javax.persistence.PersistenceException.class)
    public void testNullCreatedByAction() {
        UppdragVardEnhet uppdragVardEnhet = uppdragVardEnhetRepository.find(-4L);
        uppdragVardEnhet.setVardEnhet("new"+uppdragVardEnhet.getVardEnhet());

        UppdragVardEnhetAction action = new UppdragVardEnhetAction();
        action.setType(ChangeType.ADD_MEMBER);
        action.setHsaIdentity("hsaS2");
        action.setHsaCommissionMember("hsaP6;;");
//        action.setCreatedBy("test");

        uppdragVardEnhet.addAction(action);
        uppdragVardEnhetRepository.store(uppdragVardEnhet);
    }

    @Test(expected = java.lang.IllegalAccessError.class)
    public void testChangeSignedUppdrag1() {
        UppdragVardEnhet uppdragVardEnhet = uppdragVardEnhetRepository.find(-1L);
        uppdragVardEnhet.setVardEnhet("apa");
    }

    @Test(expected = java.lang.IllegalAccessError.class)
    public void testChangeSignedUppdrag2() {
        UppdragVardEnhet uppdragVardEnhet = uppdragVardEnhetRepository.find(-1L);
        uppdragVardEnhet.setApprovedBy("apa");
    }

    @Test(expected = java.lang.IllegalAccessError.class)
    public void testChangeSignedUppdrag3() {
        UppdragVardEnhet uppdragVardEnhet = uppdragVardEnhetRepository.find(-1L);
        uppdragVardEnhet.setApprovedOn(new Date());
    }

    @Test(expected = java.lang.IllegalAccessError.class)
    public void testChangeSignedUppdrag4() {
        UppdragVardEnhet uppdragVardEnhet = uppdragVardEnhetRepository.find(-1L);
        uppdragVardEnhet.setSignToken("apa");
    }

    @Test(expected = java.lang.IllegalAccessError.class)
    public void testChangeSignedUppdrag5() {
        UppdragVardEnhet uppdragVardEnhet = uppdragVardEnhetRepository.find(-1L);
        uppdragVardEnhet.addAction(new UppdragVardEnhetAction());
    }

    @Test(expected = java.lang.IllegalAccessError.class)
    public void testChangeSignedUppdrag6() {
        UppdragVardEnhet uppdragVardEnhet = uppdragVardEnhetRepository.find(-1L);
        UppdragVardEnhetAction action = uppdragVardEnhet.getUppdragVardEnhetActions().iterator().next();
        uppdragVardEnhet.removeAction(action);
    }

    @Test
    public void testActionCreated_NotModified() {
        UppdragVardEnhet uppdragVardEnhet = uppdragVardEnhetRepository.find(-4L);
        UppdragVardEnhetAction action = uppdragVardEnhet.getUppdragVardEnhetActions().iterator().next();
        Date created = action.getCreated();

        action.setHsaIdentity("newHsaIdentity");

        uppdragVardEnhetRepository.store(uppdragVardEnhet);

        // Validate
        UppdragVardEnhet check = uppdragVardEnhetRepository.find(-4L);
        assertEquals(1, check.getUppdragVardEnhetActions().size());
        UppdragVardEnhetAction newAction = uppdragVardEnhet.getUppdragVardEnhetActions().iterator().next();

        assertEquals("newHsaIdentity", newAction.getHsaIdentity());
        assertEquals(created, newAction.getCreated());
    }

    @Test
    public void testActionCreated_Unmodified() {
        Date now = new Date();
        UppdragVardEnhet uppdragVardEnhet = uppdragVardEnhetRepository.find(-4L);
        UppdragVardEnhetAction action = uppdragVardEnhet.getUppdragVardEnhetActions().iterator().next();
        Date created = new Date(action.getCreated().getTime());
        Date again = action.getCreated();

        again.setTime(new Date().getTime());

        assertThat(created, ComparableMatcher.lessThan(again));
        assertEquals(created, action.getCreated());
    }

    @Test
    public void testUppdragApprovedOn_Unmodifiable() {
        UppdragVardEnhet uppdragVardEnhet = uppdragVardEnhetRepository.find(-1L);
        Date approvedOn = new Date(uppdragVardEnhet.getApprovedOn().getTime());
        Date again = uppdragVardEnhet.getApprovedOn();

        again.setTime(new Date().getTime());

        assertThat(approvedOn, ComparableMatcher.lessThan(again));
        assertEquals(approvedOn, uppdragVardEnhet.getApprovedOn());
    }
}
