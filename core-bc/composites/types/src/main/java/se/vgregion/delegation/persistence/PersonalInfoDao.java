package se.vgregion.delegation.persistence;

import se.vgregion.delegation.domain.PersonalInfo;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-11-08 13:30
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public interface PersonalInfoDao {

    PersonalInfo lookup(String vgrId);

    String personalInUnitFilter(String unitDn);

    List<PersonalInfo> resolvePersonal(String filter);
}
