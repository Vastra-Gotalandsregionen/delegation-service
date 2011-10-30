package se.vgregion.delegation.persistence.ldap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import se.vgregion.delegation.domain.HealthCareUnit;
import se.vgregion.delegation.domain.PersonalInfo;
import se.vgregion.delegation.domain.VerksamhetsChefInfo;
import se.vgregion.delegation.persistence.VerksamhetsChefDao;

import javax.naming.Name;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-10-22 19:43
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class VerksamhetsChefDaoImplTest extends AbstractJUnit4SpringContextTests {

    @Mock
    private LdapTemplate ldapTemplate;

    @InjectMocks
    private VerksamhetsChefDao dao = new VerksamhetsChefDaoImpl();

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testIsVerksamhetsChef() throws Exception {
        PersonalInfo apa = Mockito.mock(PersonalInfo.class);
        when(ldapTemplate.search(eq("ou=personal,ou=anv,o=vgr"), eq("(&(objectclass=person)(uid=apa))"),
                Matchers.<ContextMapper>anyObject())).thenReturn(Arrays.asList(apa));
        when(apa.getHsaIdentity()).thenReturn("hsaApa");

        HealthCareUnit vardEnhet = Mockito.mock(HealthCareUnit.class);
        when(ldapTemplate.search(eq("ou=Org,o=vgr"), eq("(&(objectclass=organizationalUnit)(hsaHealthCareUnitManager=hsaApa))"),
                Matchers.<ContextMapper>anyObject())).thenReturn(Arrays.asList(vardEnhet));

        assertTrue(dao.isVerksamhetsChef("apa"));
    }

    @Test
    public void testIsVerksamhetsChef2() throws Exception {

        PersonalInfo apa = Mockito.mock(PersonalInfo.class);
        when(ldapTemplate.search(eq("ou=personal,ou=anv,o=vgr"), eq("(&(objectclass=person)(uid=apa))"),
                Matchers.<ContextMapper>anyObject())).thenReturn(Arrays.asList(apa));
        when(apa.getHsaIdentity()).thenReturn("");

        assertFalse(dao.isVerksamhetsChef("apa"));
    }

    @Test
    public void testIsVerksamhetsChef3() throws Exception {

        PersonalInfo apa = Mockito.mock(PersonalInfo.class);
        PersonalInfo bepa = Mockito.mock(PersonalInfo.class);
        when(ldapTemplate.search(eq("ou=personal,ou=anv,o=vgr"), eq("(&(objectclass=person)(uid=apa))"),
                Matchers.<ContextMapper>anyObject())).thenReturn(Arrays.asList(apa, bepa));

        assertFalse(dao.isVerksamhetsChef("apa"));
    }

    @Test
    public void testIsVerksamhetsChef4() throws Exception {

        when(ldapTemplate.search(eq("ou=personal,ou=anv,o=vgr"), eq("(&(objectclass=person)(uid=apa))"),
                Matchers.<ContextMapper>anyObject())).thenReturn(Arrays.asList());

        assertFalse(dao.isVerksamhetsChef("apa"));
    }

    @Test
    public void testIsVerksamhetsChef5() throws Exception {

        PersonalInfo apa = Mockito.mock(PersonalInfo.class);
        when(ldapTemplate.search(eq("ou=personal,ou=anv,o=vgr"), eq("(&(objectclass=person)(uid=apa))"),
                Matchers.<ContextMapper>anyObject())).thenReturn(Arrays.asList(apa));
        when(apa.getHsaIdentity()).thenReturn("hsaApa");

        when(ldapTemplate.search(eq("ou=Org,o=vgr"), eq("(&(objectclass=organizationalUnit)(hsaHealthCareUnitManager=hsaApa))"),
                Matchers.<ContextMapper>anyObject())).thenReturn(Arrays.asList());

        assertFalse(dao.isVerksamhetsChef("apa"));
    }

    @Test
    public void testFind() throws Exception {
        PersonalInfo apa = Mockito.mock(PersonalInfo.class);
        when(ldapTemplate.search(eq("ou=personal,ou=anv,o=vgr"), eq("(&(objectclass=person)(uid=apa))"),
                Matchers.<ContextMapper>anyObject())).thenReturn(Arrays.asList(apa));
        when(apa.getHsaIdentity()).thenReturn("hsaApa");

        HealthCareUnit vardEnhet = Mockito.mock(HealthCareUnit.class);
        when(ldapTemplate.search(eq("ou=Org,o=vgr"), eq("(&(objectclass=organizationalUnit)(hsaHealthCareUnitManager=hsaApa))"),
                Matchers.<ContextMapper>anyObject())).thenReturn(Arrays.asList(vardEnhet));

        when(vardEnhet.getDn()).thenReturn("veDN");
        PersonalInfo bepa = Mockito.mock(PersonalInfo.class);
        List vePersonal = Arrays.asList(apa,bepa);
        when(ldapTemplate.search(eq("ou=personal,ou=anv,o=vgr"), eq("(&(objectclass=person)" +
                "(vgrStrukturPersonDN=veDN))"),
                Matchers.<ContextMapper>anyObject())).thenReturn(vePersonal);

        when(vardEnhet.getHsaResponsibleHealthCareProvider()).thenReturn("hsaVardGivare");
        HealthCareUnit vardGivare = Mockito.mock(HealthCareUnit.class);
        when(ldapTemplate.search(eq("ou=Org,o=vgr"), eq("(&(objectclass=organizationalUnit)(hsaIdentity=hsaVardGivare))"),
                Matchers.<ContextMapper>anyObject())).thenReturn(Arrays.asList(vardGivare));

        when(vardEnhet.getHsaHealthCareUnitMembers()).thenReturn(new String[] {});

        List<VerksamhetsChefInfo> result = dao.find("apa");
        assertEquals(1, result.size());

        VerksamhetsChefInfo vc = result.get(0);
        assertSame(apa, vc.getVerksamhetsChef());
        assertSame(vardEnhet, vc.getVardEnhet());
        verify(vardEnhet).setPersonal(vePersonal);
        assertSame(vardGivare, vc.getVardGivare());
    }

    @Test
    public void testFind2() throws Exception {
        PersonalInfo apa = Mockito.mock(PersonalInfo.class);
        when(ldapTemplate.search(eq("ou=personal,ou=anv,o=vgr"), eq("(&(objectclass=person)(uid=apa))"),
                Matchers.<ContextMapper>anyObject())).thenReturn(Arrays.asList(apa));
        when(apa.getHsaIdentity()).thenReturn("hsaApa");

        HealthCareUnit vardEnhet = Mockito.mock(HealthCareUnit.class);
        when(ldapTemplate.search(eq("ou=Org,o=vgr"), eq("(&(objectclass=organizationalUnit)(hsaHealthCareUnitManager=hsaApa))"),
                Matchers.<ContextMapper>anyObject())).thenReturn(Arrays.asList(vardEnhet));

        when(vardEnhet.getDn()).thenReturn("veDN");
        PersonalInfo bepa = Mockito.mock(PersonalInfo.class);
        List vePersonal = Arrays.asList(apa, bepa);
        when(ldapTemplate.search(eq("ou=personal,ou=anv,o=vgr"), eq("(&(objectclass=person)" +
                "(vgrStrukturPersonDN=veDN))"),
                Matchers.<ContextMapper>anyObject())).thenReturn(vePersonal);

        when(vardEnhet.getHsaResponsibleHealthCareProvider()).thenReturn("hsaVardGivare");
        HealthCareUnit vardGivare = Mockito.mock(HealthCareUnit.class);
        when(ldapTemplate.search(eq("ou=Org,o=vgr"), eq("(&(objectclass=organizationalUnit)(hsaIdentity=hsaVardGivare))"),
                Matchers.<ContextMapper>anyObject())).thenReturn(Arrays.asList(vardGivare));

        HealthCareUnit in1 = Mockito.mock(HealthCareUnit.class);
        HealthCareUnit in2 = Mockito.mock(HealthCareUnit.class);
        List ingaende = Arrays.asList(in1, in2);
        when(vardEnhet.getHsaHealthCareUnitMembers()).thenReturn(new String[] {"hsaIn1", "hsaIn2"});
        when(ldapTemplate.search(eq("ou=Org,o=vgr"), eq("(&(objectclass=organizationalUnit)" +
                "(| (hsaIdentity=hsaIn1)(hsaIdentity=hsaIn2)))"),
                Matchers.<ContextMapper>anyObject())).thenReturn(ingaende);

        List<VerksamhetsChefInfo> result = dao.find("apa");
        assertEquals(1, result.size());

        VerksamhetsChefInfo vc = result.get(0);
        assertSame(apa, vc.getVerksamhetsChef());
        assertSame(vardEnhet, vc.getVardEnhet());
        verify(vardEnhet).setPersonal(vePersonal);
        assertSame(vardGivare, vc.getVardGivare());
        assertSame(ingaende, vc.getIngaendeEnheter());
    }

    @Test
    public void testPersonalInfoContextMapper() {
        PersonalInfoContextMapper piMapper = new PersonalInfoContextMapper();

        DirContextAdapter ctx = Mockito.mock(DirContextAdapter.class);
        Name DN = Mockito.mock(Name.class);
        when(ctx.getDn()).thenReturn(DN);

        assertNotNull(piMapper.mapFromContext(ctx));
    }

    @Test
    public void testHealthCareUnitContextMapper() {
        HealthCareUnitContextMapper hcuMapper = new HealthCareUnitContextMapper();

        DirContextAdapter ctx = Mockito.mock(DirContextAdapter.class);
        Name DN = Mockito.mock(Name.class);
        when(ctx.getDn()).thenReturn(DN);

        assertNotNull(hcuMapper.mapFromContext(ctx));
    }
}
