package se.vgregion.delegation.domain;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-10-19 11:20
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class PersonalInfo {
    String dn;
    String uid;
    String givenName;
    String sn;
    String fullName;
    String displayName;
    String title;
    String mail;
    String hsaIdentity;
    String[] vgrStrukturPersonDN;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getHsaIdentity() {
        return hsaIdentity;
    }

    public void setHsaIdentity(String hsaIdentity) {
        this.hsaIdentity = hsaIdentity;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String[] getVgrStrukturPersonDN() {
        return vgrStrukturPersonDN;
    }

    public void setVgrStrukturPersonDN(String[] vgrStrukturPersonDN) {
        this.vgrStrukturPersonDN = vgrStrukturPersonDN;
    }

    @Override
    public String toString() {
        return "PersonalInfo{" +
                "dn='" + dn + '\'' +
                ", uid='" + uid + '\'' +
                ", displayName='" + displayName + '\'' +
                ", givenName='" + givenName + '\'' +
                ", sn='" + sn + '\'' +
                ", fullName='" + fullName + '\'' +
                ", title='" + title + '\'' +
                ", mail='" + mail + '\'' +
                ", hsaIdentity='" + hsaIdentity + '\'' +
                ", vgrStrukturPersonDN='" + vgrStrukturPersonDN + '\'' +
                '}';
    }
}
