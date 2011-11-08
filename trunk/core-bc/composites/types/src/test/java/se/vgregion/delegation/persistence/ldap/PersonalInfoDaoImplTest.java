package se.vgregion.delegation.persistence.ldap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.LdapTemplate;
import se.vgregion.delegation.domain.PersonalInfo;
import se.vgregion.delegation.persistence.PersonalInfoDao;

import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-11-08 15:13
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class PersonalInfoDaoImplTest {

    @Mock
    private LdapTemplate ldapTemplate;

    @InjectMocks
    private PersonalInfoDao personalInfoDao = new PersonalInfoDaoImpl();

    @Test
    public void testLookup() throws Exception {
        PersonalInfo apa = Mockito.mock(PersonalInfo.class);
        when(ldapTemplate.search(eq("ou=personal,ou=anv"), eq("(&(objectClass=person)(uid=apa))"),
                Matchers.<ContextMapper>anyObject())).thenReturn(Arrays.asList(apa));

        PersonalInfo result = personalInfoDao.lookup("apa");

        assertSame(apa, result);
    }

    @Test
    public void testLookupNotFound() throws Exception {
        when(ldapTemplate.search(eq("ou=personal,ou=anv"), eq("(&(objectClass=person)(uid=apa))"),
                Matchers.<ContextMapper>anyObject())).thenReturn(Arrays.asList());


        PersonalInfo result = personalInfoDao.lookup("apa");

        assertNull(result);
    }

    @Test(expected = RuntimeException.class)
    public void testLookupToManyFound() throws Exception {
        PersonalInfo apa = Mockito.mock(PersonalInfo.class);
        PersonalInfo bepa = Mockito.mock(PersonalInfo.class);
        when(ldapTemplate.search(eq("ou=personal,ou=anv"), eq("(&(objectClass=person)(uid=apa))"),
                Matchers.<ContextMapper>anyObject())).thenReturn(Arrays.asList(apa,bepa));


        PersonalInfo result = personalInfoDao.lookup("apa");
    }

    @Test
    public void testPersonalInUnitFilter1() throws Exception {
        String result = personalInfoDao.personalInUnitFilter("ou=e11,ou=e1,o=VGR");
        assertEquals("(ou=e11)(StrukturGrupp=e1)(StrukturGrupp=VGR)", result);
    }

    @Test
    public void testPersonalInUnitFilter2() throws Exception {
        String result = personalInfoDao.personalInUnitFilter("o=VGR");
        assertEquals("(ou=VGR)", result);
    }

    @Test
    public void testResolvePersonal() throws Exception {
        PersonalInfo apa = Mockito.mock(PersonalInfo.class);
        List<PersonalInfo> piList = Arrays.asList(apa);
        when(ldapTemplate.search(eq("ou=personal,ou=anv"), eq("(&(objectClass=person)(uid=apa))"),
                Matchers.<ContextMapper>anyObject())).thenReturn(piList);

        List<PersonalInfo> result = personalInfoDao.resolvePersonal("(&(objectClass=person)(uid=apa))");

        assertSame(piList, result);
    }

    @Test(expected = RuntimeException.class)
    public void testResolvePersonalFail() throws Exception {
        when(ldapTemplate.search(eq("ou=personal,ou=anv"), eq("(&(objectClass=person)(uid=apa))"),
                Matchers.<ContextMapper>anyObject())).thenThrow(new Exception("Error"));

        List<PersonalInfo> result = personalInfoDao.resolvePersonal("(&(objectClass=person)(uid=apa))");
    }
}
