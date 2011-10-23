package se.vgregion.delegation.persistence.ldap;

import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import se.vgregion.delegation.domain.PersonalInfo;

public class PersonalInfoContextMapper implements ContextMapper {
        @Override
        public Object mapFromContext(Object context) {
            DirContextAdapter ctx = (DirContextAdapter) context;

            PersonalInfo personal = new PersonalInfo();
            personal.setDn(ctx.getDn().toString());
            personal.setDisplayName(ctx.getStringAttribute("displayName"));
            personal.setFullName(ctx.getStringAttribute("fullName"));
            personal.setGivenName(ctx.getStringAttribute("givenName"));
            personal.setHsaIdentity(ctx.getStringAttribute("hsaIdentity"));
            personal.setMail(ctx.getStringAttribute("mail"));
            personal.setSn(ctx.getStringAttribute("sn"));
            personal.setTitle(ctx.getStringAttribute("title"));
            personal.setUid(ctx.getStringAttribute("uid"));
            personal.setVgrStructurePersonDN(ctx.getStringAttributes("vgrStructurePersonDN"));

            return personal;
        }
}