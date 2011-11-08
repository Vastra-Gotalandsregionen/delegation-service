package se.vgregion.delegation.domain;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-11-08 17:09
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class HsaCommission {
    private String dn;
    private String cn;
    private String[] hsaCommissionMember;
    private String hsaCommissionPurpose;
    private String hsaCommissionRight;
    private String hsaIdentity;

    private Collection<PersonalInfo> members;

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public String[] getHsaCommissionMember() {
        return hsaCommissionMember;
    }

    public void setHsaCommissionMember(String[] hsaCommissionMember) {
        this.hsaCommissionMember = hsaCommissionMember;
    }

    public String getHsaCommissionPurpose() {
        return hsaCommissionPurpose;
    }

    public void setHsaCommissionPurpose(String hsaCommissionPurpose) {
        this.hsaCommissionPurpose = hsaCommissionPurpose;
    }

    public String getHsaCommissionRight() {
        return hsaCommissionRight;
    }

    public void setHsaCommissionRight(String hsaCommissionRight) {
        this.hsaCommissionRight = hsaCommissionRight;
    }

    public String getHsaIdentity() {
        return hsaIdentity;
    }

    public void setHsaIdentity(String hsaIdentity) {
        this.hsaIdentity = hsaIdentity;
    }

    public Collection<PersonalInfo> getMembers() {
        return members;
    }

    public void setMembers(Collection<PersonalInfo> members) {
        this.members = members;
    }
}
