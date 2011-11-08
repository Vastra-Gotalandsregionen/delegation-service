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
import se.vgregion.delegation.domain.HealthCareUnit;
import se.vgregion.delegation.persistence.HealthCareUnitDao;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-11-08 15:39
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class HealthCareUnitDaoImplTest {
    @Mock
    private LdapTemplate ldapTemplate;

    @InjectMocks
    private HealthCareUnitDao healthCareUnitDao = new HealthCareUnitDaoImpl();

    @Test
    public void testFindAll() throws Exception {
        HealthCareUnit ve = Mockito.mock(HealthCareUnit.class);
        when(ve.getOu()).thenReturn("ou");
        List<HealthCareUnit> veList = Arrays.asList(ve);
        when(ldapTemplate.search(eq("ou=Org"), anyString(),
                Matchers.<ContextMapper>anyObject())).thenReturn(veList);

        Collection<HealthCareUnit> result = healthCareUnitDao.findAll();

        assertEquals(veList.size(), result.size());

        verify(ldapTemplate, times(29)).search(anyString(), anyString(), Matchers.<ContextMapper>anyObject());
    }

    @Test
    public void testLookupUnit() throws Exception {
        HealthCareUnit ve = Mockito.mock(HealthCareUnit.class);
        when(ldapTemplate.lookup(eq("veDN"), Matchers.<ContextMapper>anyObject())).thenReturn(ve);

        HealthCareUnit result = healthCareUnitDao.lookupUnit("veDN");

        assertSame(ve, result);
    }

    @Test(expected = RuntimeException.class)
    public void testLookupUnitFail() throws Exception {
        when(ldapTemplate.lookup("veDN", Matchers.<ContextMapper>anyObject()))
                .thenThrow(new Exception("error"));

        HealthCareUnit result = healthCareUnitDao.lookupUnit("veDN");
    }

    @Test
    public void testResolveUnit() throws Exception {
        HealthCareUnit ve = Mockito.mock(HealthCareUnit.class);
        List<HealthCareUnit> veList = Arrays.asList(ve);
        when(ldapTemplate.search(eq("ou=Org"), eq("(filter=apa)"),
                Matchers.<ContextMapper>anyObject())).thenReturn(veList);

        List<HealthCareUnit> result = healthCareUnitDao.resolveUnit("(filter=apa)");

        assertSame(veList, result);
    }

    @Test(expected = RuntimeException.class)
    public void testResolveUnitFail() throws Exception {
        when(ldapTemplate.search(eq("ou=Org"), eq("(filter=apa)"),
                Matchers.<ContextMapper>anyObject())).thenThrow(new Exception("error"));

        List<HealthCareUnit> result = healthCareUnitDao.resolveUnit("(filter=apa)");
    }
}
