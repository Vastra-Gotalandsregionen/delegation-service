package se.vgregion.delegation.model;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-11-23 11:20
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class SearchPerson implements Serializable {
    private String firstName;
    private String lastName;
    private String vgrId;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getVgrId() {
        return vgrId;
    }

    public void setVgrId(String vgrId) {
        this.vgrId = vgrId;
    }
}
