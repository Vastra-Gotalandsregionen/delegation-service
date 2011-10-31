package se.vgregion.handlers;

import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import se.vgregion.delegation.DelegationService;
import se.vgregion.proxy.signera.signature.SignatureEnvelope;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-10-31 12:55
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class DelegationSigningHandler implements SigningHandler {

    @Autowired
    private DelegationService delegationService;

    /**
     * {@inheritDoc}
     */
    @Transactional
    public Object signApproval(SignatureEnvelope signatureEnvelope, CamelContext camelContext) {
        System.out.println(signatureEnvelope);

        // 1: Make object of reply

        // 2: store signToken on delegation and commit
//        delegationService.approve(null, signToken);

        int errorCode = signatureEnvelope.getErrorCode();


        return signatureEnvelope;
    }
}
