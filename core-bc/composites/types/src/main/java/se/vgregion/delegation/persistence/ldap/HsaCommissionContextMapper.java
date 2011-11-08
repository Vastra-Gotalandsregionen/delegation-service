package se.vgregion.delegation.persistence.ldap;

import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import se.vgregion.delegation.domain.HealthCareUnit;
import se.vgregion.delegation.domain.HsaCommission;

public class HsaCommissionContextMapper implements ContextMapper {
        @Override
        public Object mapFromContext(Object context) {
            DirContextAdapter ctx = (DirContextAdapter) context;

            HsaCommission commission = new HsaCommission();
            commission.setDn(ctx.getDn().toString());
            commission.setCn(ctx.getStringAttribute("cn"));
            commission.setHsaIdentity(ctx.getStringAttribute("hsaIdentity"));
            commission.setHsaCommissionMember(ctx.getStringAttributes("hsaCommissionMember"));
            commission.setHsaCommissionPurpose(ctx.getStringAttribute("hsaCommissionPurpose"));
            commission.setHsaCommissionRight(ctx.getStringAttribute("hsaCommissionRight"));

            return commission;
        }
}