package se.vgregion.delegation.domain;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-10-19 09:43
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class VardEnhetInfo {
    PersonalInfo verksamhetsChef;
    HealthCareUnit vardEnhet;
    Collection<HealthCareUnit> ingaendeEnheter;
    HealthCareUnit vardGivare;
    Collection<HsaCommission> uppdrag;

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

    public Collection<HsaCommission> getUppdrag() {
        return uppdrag;
    }

    public void setUppdrag(Collection<HsaCommission> uppdrag) {
        this.uppdrag = uppdrag;
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

        StringBuilder uppdragBuffer = new StringBuilder("[");
        for (HsaCommission commission : uppdrag) {
            if (uppdragBuffer.length() > 1) uppdragBuffer.append(", ");
            uppdragBuffer.append(commission.getCn());
        }
        uppdragBuffer.append("]");

        return "VardEnhet-{" + vardEnhetStr +
                ", verksamhetsChef=" + verksamhetsChefStr +
                ", ingaendeEnheter=" + memberUnitsBuffer +
                ", vardGivare=" + vardGivarStr +
                ", uppdrag=" + uppdragBuffer +
                '}';
    }
}
