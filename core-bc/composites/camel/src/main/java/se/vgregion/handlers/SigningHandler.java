package se.vgregion.handlers;

import org.apache.camel.CamelContext;
import se.vgregion.proxy.signera.signature.SignatureEnvelope;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-10-31 09:53
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public interface SigningHandler {

    /**
     * Handles the response from signing service.
     *
     * @param signMessage - the signMessage returned from signing service after it is done signing.
     * @param camelContext - handle to the camelContext.
     * @return result of local signing approval handling that can be processed further.
     */
    Object signApproval(SignatureEnvelope signMessage, CamelContext camelContext);
}
