package se.vgregion.delegation.domain;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-10-19 09:43
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class VerksamhetsChefInfo {
    PersonalInfo verksamhetsChef;
    HealthCareUnit vardEnhet;
    Collection<HealthCareUnit> ingaendeEnheter;
    HealthCareUnit vardGivare;

    public PersonalInfo getVerksamhetsChef() {
        return verksamhetsChef;
    }

    public void setVerksamhetsChef(PersonalInfo verksamhetsChef) {
        this.verksamhetsChef = verksamhetsChef;
    }

    public HealthCareUnit getVardEnhet() {
        return vardEnhet;
    }

    public void setVardEnhet(HealthCareUnit vardEnhet) {
        this.vardEnhet = vardEnhet;
    }

    public Collection<HealthCareUnit> getIngaendeEnheter() {
        return ingaendeEnheter;
    }

    public void setIngaendeEnheter(Collection<HealthCareUnit> ingaendeEnheter) {
        this.ingaendeEnheter = ingaendeEnheter;
    }

    public HealthCareUnit getVardGivare() {
        return vardGivare;
    }

    public void setVardGivare(HealthCareUnit vardGivare) {
        this.vardGivare = vardGivare;
    }

    @Override
    public String toString() {
        StringBuilder memberUnitsBuffer = new StringBuilder("[");
        for (HealthCareUnit unit : ingaendeEnheter) {
            if (memberUnitsBuffer.length() > 1) memberUnitsBuffer.append(", ");
            memberUnitsBuffer.append(unit.getDn());
        }
        memberUnitsBuffer.append("]");

        String vardGivarStr = "";
        if (vardEnhet != null) {
            if (vardEnhet.dn != null) {
                vardGivarStr = vardEnhet.dn;
            } else {
                vardGivarStr = vardEnhet.hsaIdentity;
            }
        }

        String vardEnhetStr = "";
        if (vardEnhet != null) {
            vardEnhetStr = vardEnhet.dn;
        }

        String verksamhetsChefStr = "";
        if (verksamhetsChef != null) {
            verksamhetsChefStr = verksamhetsChef.toString();
        }

        return "VerksamhetsChefInfo-{" + verksamhetsChefStr +
                ", vardEnhet=" + vardEnhetStr +
                ", ingaendeEnheter=" + memberUnitsBuffer +
                ", vardGivare=" + vardGivarStr +
                '}';
    }
}
