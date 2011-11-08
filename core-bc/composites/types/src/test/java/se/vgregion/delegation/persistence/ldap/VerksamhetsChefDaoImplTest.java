package se.vgregion.delegation.persistence.ldap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import se.vgregion.delegation.domain.HealthCareUnit;
import se.vgregion.delegation.domain.PersonalInfo;
import se.vgregion.delegation.domain.VardEnhetInfo;
import se.vgregion.delegation.persistence.HealthCareUnitDao;
import se.vgregion.delegation.persistence.PersonalInfoDao;
import se.vgregion.delegation.persistence.VardEnhetDao;

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
    private PersonalInfoDao personalInfoDao;

    @Mock
    private HealthCareUnitDao healthCareUnitDao;

    @InjectMocks
    private VardEnhetDao dao = new VardEnhetDaoImpl();

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testIsVerksamhetsChef() throws Exception {
        PersonalInfo apa = Mockito.mock(PersonalInfo.class);
        when(personalInfoDao.lookup(eq("apa"))).thenReturn(apa);
        when(apa.getHsaIdentity()).thenReturn("hsaApa");

        HealthCareUnit vardEnhet = Mockito.mock(HealthCareUnit.class);
        when(healthCareUnitDao.resolveUnit(eq("(&(objectClass=organizationalUnit)" +
                "(hsaHealthCareUnitManager=hsaApa))"))).thenReturn(Arrays.asList(vardEnhet));

        assertTrue(dao.isVerksamhetsChef("apa"));
    }

    @Test
    public void testIsVerksamhetsChefNoHsaIdentity() throws Exception {

        PersonalInfo apa = Mockito.mock(PersonalInfo.class);
        when(personalInfoDao.lookup(eq("apa"))).thenReturn(apa);
        when(apa.getHsaIdentity()).thenReturn("");

        assertFalse(dao.isVerksamhetsChef("apa"));
    }

    @Test
    public void testIsVerksamhetsChefNotFound() throws Exception {
        when(personalInfoDao.lookup(eq("apa"))).thenReturn(null);

        assertFalse(dao.isVerksamhetsChef("apa"));
    }

    @Test
    public void testIsVerksamhetsChefToManyFound() throws Exception {
        when(personalInfoDao.lookup(eq("apa"))).thenThrow(new RuntimeException("felmeddelenda"));

        assertFalse(dao.isVerksamhetsChef("apa"));
    }

    @Test
    public void testIsVerksamhetsChefNoVardEnhet() throws Exception {
        PersonalInfo apa = Mockito.mock(PersonalInfo.class);
        when(personalInfoDao.lookup(eq("apa"))).thenReturn(apa);
        when(apa.getHsaIdentity()).thenReturn("hsaApa");

        when(healthCareUnitDao.resolveUnit(eq("(&(objectClass=organizationalUnit)" +
                "(hsaHealthCareUnitManager=hsaApa))"))).thenReturn(Arrays.<HealthCareUnit>asList());

        assertFalse(dao.isVerksamhetsChef("apa"));
    }

    @Test
    public void testFind() throws Exception {
        PersonalInfo apa = Mockito.mock(PersonalInfo.class);
        when(personalInfoDao.lookup(eq("apa"))).thenReturn(apa);
        when(apa.getHsaIdentity()).thenReturn("hsaApa");

        HealthCareUnit vardEnhet = Mockito.mock(HealthCareUnit.class);
        when(healthCareUnitDao.resolveUnit(eq("(&(objectClass=organizationalUnit)" +
                "(hsaHealthCareUnitManager=hsaApa))"))).thenReturn(Arrays.asList(vardEnhet));

        when(vardEnhet.getDn()).thenReturn("ou=veDN,o=VGR");
        PersonalInfo bepa = Mockito.mock(PersonalInfo.class);
        List vePersonal = Arrays.asList(apa, bepa);
        when(personalInfoDao.personalInUnitFilter(eq("ou=veDN,o=VGR")))
                .thenReturn("(ou=veDN)(StrukturGrupp=VGR)");
        when(personalInfoDao.resolvePersonal(eq("(&(objectClass=person)(ou=veDN)(StrukturGrupp=VGR))")))
                .thenReturn(vePersonal);

        when(vardEnhet.getHsaResponsibleHealthCareProvider()).thenReturn("hsaVardGivare");
        HealthCareUnit vardGivare = Mockito.mock(HealthCareUnit.class);
        when(healthCareUnitDao.resolveUnit(eq("(&(objectClass=organizationalUnit)(hsaIdentity=hsaVardGivare))")))
                .thenReturn(Arrays.asList(vardGivare));

        when(vardEnhet.getHsaHealthCareUnitMembers()).thenReturn(new String[]{});

        List<VardEnhetInfo> result = dao.find("apa");
        assertEquals(1, result.size());

        VardEnhetInfo vc = result.get(0);
        assertSame(apa, vc.getVerksamhetsChef());
        assertSame(vardEnhet, vc.getVardEnhet());
        verify(vardEnhet).setPersonal(vePersonal);
        assertSame(vardGivare, vc.getVardGivare());
    }

    @Test
    public void testFindMoreResolve() throws Exception {
        // Setup VerksamhetsChef
        PersonalInfo apa = Mockito.mock(PersonalInfo.class);
        when(personalInfoDao.lookup(eq("apa"))).thenReturn(apa);
        when(apa.getHsaIdentity()).thenReturn("hsaApa");

        // Setup VårdEnhet
        HealthCareUnit vardEnhet = Mockito.mock(HealthCareUnit.class);
        when(healthCareUnitDao.resolveUnit(eq("(&(objectClass=organizationalUnit)" +
                "(hsaHealthCareUnitManager=hsaApa))"))).thenReturn(Arrays.asList(vardEnhet));

        when(vardEnhet.getDn()).thenReturn("ou=veDN,o=VGR");
        PersonalInfo bepa = Mockito.mock(PersonalInfo.class);
        List vePersonal = Arrays.asList(apa, bepa);
        when(personalInfoDao.personalInUnitFilter(eq("ou=veDN,o=VGR")))
                .thenReturn("(ou=veDN)(StrukturGrupp=VGR)");
        when(personalInfoDao.resolvePersonal(eq("(&(objectClass=person)(ou=veDN)(StrukturGrupp=VGR))")))
                .thenReturn(vePersonal);

        // Setup VårdGivare
        when(vardEnhet.getHsaResponsibleHealthCareProvider()).thenReturn("hsaVardGivare");
        HealthCareUnit vardGivare = Mockito.mock(HealthCareUnit.class);
        when(healthCareUnitDao.resolveUnit(eq("(&(objectClass=organizationalUnit)(hsaIdentity=hsaVardGivare))")))
                .thenReturn(Arrays.asList(vardGivare));

        // Setup IngåendeEnheter
        HealthCareUnit in1 = Mockito.mock(HealthCareUnit.class);
        HealthCareUnit in2 = Mockito.mock(HealthCareUnit.class);
        List ingaende = Arrays.asList(in1, in2);
        when(vardEnhet.getHsaHealthCareUnitMembers()).thenReturn(new String[]{"hsaIn1", "hsaIn2"});
        when(healthCareUnitDao.resolveUnit(eq("(&(objectClass=organizationalUnit)" +
                "(| (hsaIdentity=hsaIn1)(hsaIdentity=hsaIn2)))"))).thenReturn(ingaende);

        when(in1.getDn()).thenReturn("ou=in1,ou=veDN,o=VGR");
        PersonalInfo cepa = Mockito.mock(PersonalInfo.class);
        List in1Personal = Arrays.asList(cepa);
        when(personalInfoDao.personalInUnitFilter(eq("ou=in1,ou=veDN,o=VGR")))
                .thenReturn("(ou=in1)(StrukturGrupp=veDN)(StrukturGrupp=VGR)");
        when(personalInfoDao.resolvePersonal(
                eq("(&(objectClass=person)(ou=in1)(StrukturGrupp=veDN)(StrukturGrupp=VGR))"))
        ).thenReturn(in1Personal);

        when(in2.getDn()).thenReturn("ou=in2,ou=veDN,o=VGR");
        PersonalInfo depa = Mockito.mock(PersonalInfo.class);
        List in2Personal = Arrays.asList(depa);
        when(personalInfoDao.personalInUnitFilter(eq("ou=in2,ou=veDN,o=VGR")))
                .thenReturn("(ou=in2)(StrukturGrupp=veDN)(StrukturGrupp=VGR)");
        when(personalInfoDao.resolvePersonal(
                eq("(&(objectClass=person)(ou=in2)(StrukturGrupp=veDN)(StrukturGrupp=VGR))"))
        ).thenReturn(in2Personal);

        List<VardEnhetInfo> result = dao.find("apa");
        assertEquals(1, result.size());

        VardEnhetInfo vc = result.get(0);
        assertSame(apa, vc.getVerksamhetsChef());
        assertSame(vardEnhet, vc.getVardEnhet());
        verify(vardEnhet).setPersonal(vePersonal);
        assertSame(vardGivare, vc.getVardGivare());
        assertSame(ingaende, vc.getIngaendeEnheter());
        verify(in1).setPersonal(in1Personal);
        verify(in2).setPersonal(in2Personal);
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
