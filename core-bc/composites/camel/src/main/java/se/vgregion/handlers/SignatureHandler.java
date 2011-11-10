package se.vgregion.handlers;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import se.vgregion.signera.signature._1.SignatureEnvelope;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-10-31 09:53
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public interface SignatureHandler {

    /**
     * Handles the response from signing service.
     *
     * @param signMessage - the signMessage returned from signing service after it is done signing.
     * @return result of local signing approval handling that can be processed further.
     */
    SignatureEnvelope storeSignature(SignatureEnvelope signMessage, Exchange exchange);
}
