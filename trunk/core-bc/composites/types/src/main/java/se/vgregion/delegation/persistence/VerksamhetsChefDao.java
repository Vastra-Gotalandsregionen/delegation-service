package se.vgregion.delegation.persistence;

import se.vgregion.delegation.domain.VerksamhetsChefInfo;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-10-19 11:24
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public interface VerksamhetsChefDao {
    boolean isVerksamhetsChef(String vgrId);
    List<VerksamhetsChefInfo> find(String vgrId);
}
