package se.vgregion.delegation.domain;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-10-19 10:57
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class HealthCareUnit {
    String dn;
    String ou;
    String hsaIdentity;
    String hsaInternalAddress;
    String vgrOrganizationalUnitNameShort;
    String labeledUri;

    String hsaHealthCareUnitManager;
    String[] hsaHealthCareUnitMembers;
    String hsaResponsibleHealthCareProvider;

    Collection<PersonalInfo> personal;

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public String getOu() {
        return ou;
    }

    public void setOu(String ou) {
        this.ou = ou;
    }

    public String getHsaIdentity() {
        return hsaIdentity;
    }

    public void setHsaIdentity(String hsaIdentity) {
        this.hsaIdentity = hsaIdentity;
    }

    public String getHsaInternalAddress() {
        return hsaInternalAddress;
    }

    public void setHsaInternalAddress(String hsaInternalAddress) {
        this.hsaInternalAddress = hsaInternalAddress;
    }

    public String getLabeledUri() {
        return labeledUri;
    }

    public void setLabeledUri(String labeledUri) {
        this.labeledUri = labeledUri;
    }

    public Collection<PersonalInfo> getPersonal() {
        return personal;
    }

    public void setPersonal(Collection<PersonalInfo> personal) {
        this.personal = personal;
    }

    public String getVgrOrganizationalUnitNameShort() {
        return vgrOrganizationalUnitNameShort;
    }

    public void setVgrOrganizationalUnitNameShort(String vgrOrganizationalUnitNameShort) {
        this.vgrOrganizationalUnitNameShort = vgrOrganizationalUnitNameShort;
    }

    public String getHsaHealthCareUnitManager() {
        return hsaHealthCareUnitManager;
    }

    public void setHsaHealthCareUnitManager(String hsaHealthCareUnitManager) {
        this.hsaHealthCareUnitManager = hsaHealthCareUnitManager;
    }

    public String[] getHsaHealthCareUnitMembers() {
        return hsaHealthCareUnitMembers;
    }

    public void setHsaHealthCareUnitMembers(String[] hsaHealthCareUnitMembers) {
        this.hsaHealthCareUnitMembers = hsaHealthCareUnitMembers;
    }

    public String getHsaResponsibleHealthCareProvider() {
        return hsaResponsibleHealthCareProvider;
    }

    public void setHsaResponsibleHealthCareProvider(String hsaResponsibleHealthCareProvider) {
        this.hsaResponsibleHealthCareProvider = hsaResponsibleHealthCareProvider;
    }

    @Override
    public String toString() {
        return "HealthCareUnit{" +
                "dn='" + dn + '\'' +
                ", hsaIdentity='" + hsaIdentity + '\'' +
                ", hsaInternalAddress='" + hsaInternalAddress + '\'' +
                ", vgrOrganizationalUnitNameShort='" + vgrOrganizationalUnitNameShort + '\'' +
                ", labeledUri='" + labeledUri + '\'' +
                ", hsaHealthCareUnitManager='" + hsaHealthCareUnitManager + '\'' +
                ", hsaHealthCareUnitMembers=" + hsaHealthCareUnitMembers +
                ", hsaResponsibleHealthCareProvider='" + hsaResponsibleHealthCareProvider + '\'' +
                ", personal=" + personal +
                '}';
    }
}
