package se.vgregion.handlers;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.springframework.transaction.annotation.Transactional;
import se.vgregion.delegation.DelegationService;
import se.vgregion.delegation.domain.Delegation;
import se.vgregion.signera.signature._1.SignatureEnvelope;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-10-31 12:55
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class DelegationSignatureHandler implements SignatureHandler {

    @Resource
    private DelegationService delegationService;

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public SignatureEnvelope storeSignature(SignatureEnvelope signatureEnvelope, Exchange exchange) {
        // 0: check for errors
        int errorCode = signatureEnvelope.getErrorCode();

        if (errorCode == 0) {
            // 1: resolve delegationId
            String correlationId = exchange.getIn().getHeader("correlationId", String.class);
            Long delegationId = exchange.getIn().getHeader("delegationId", Long.class);
            Delegation delegation = delegationService.find(delegationId);

            // 2: validate signature and delegation

            // 3: store signToken on delegation and commit
            delegationService.approve(delegation, signatureEnvelope.getSignature());
        }

        return signatureEnvelope;
    }
}
