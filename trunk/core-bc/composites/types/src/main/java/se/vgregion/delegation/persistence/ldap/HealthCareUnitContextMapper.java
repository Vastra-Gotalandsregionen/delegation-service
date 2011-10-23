package se.vgregion.delegation.persistence.ldap;

import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import se.vgregion.delegation.domain.HealthCareUnit;

public class HealthCareUnitContextMapper implements ContextMapper {
        @Override
        public Object mapFromContext(Object context) {
            DirContextAdapter ctx = (DirContextAdapter) context;

            HealthCareUnit unit = new HealthCareUnit();
            unit.setDn(ctx.getDn().toString());
            unit.setOu(ctx.getStringAttribute("ou"));
            unit.setHsaIdentity(ctx.getStringAttribute("hsaIdentity"));
            unit.setHsaHealthCareUnitManager(ctx.getStringAttribute("hsaHealthCareUnitManager"));
            unit.setHsaHealthCareUnitMembers(ctx.getStringAttributes("hsaHealthCareUnitMember"));
            unit.setHsaResponsibleHealthCareProvider(ctx.getStringAttribute("hsaResponsibleHealthCareProvider"));
            unit.setHsaInternalAddress(ctx.getStringAttribute("hsaInternalAddress"));
            unit.setLabeledUri(ctx.getStringAttribute("labeledUri"));
            unit.setVgrOrganizationalUnitNameShort(ctx.getStringAttribute("vgrOrganizationalUnitNameShort"));

            return unit;
        }
}