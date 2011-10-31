package se.vgregion.routes;

import org.apache.camel.spring.SpringRouteBuilder;
import se.vgregion.handlers.SigningHandler;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-10-28 17:39
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class DelegationSigningResultRouteBuilder extends SpringRouteBuilder {
    private final String uri;
    private final SigningHandler signingHandler;

    public DelegationSigningResultRouteBuilder(String uri, SigningHandler signingHandler) {
        this.uri = uri;
        this.signingHandler = signingHandler;
    }

    @Override
    public void configure() throws Exception {
        from("jetty:" + uri)
                .errorHandler(deadLetterChannel("direct:error_" + uri))
                .bean(signingHandler, "signApproval");
    }
}
