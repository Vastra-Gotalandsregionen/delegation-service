package se.vgregion.routes;

import org.apache.camel.spring.SpringRouteBuilder;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-10-28 17:39
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class DelegationSigningResultRouteBuilder extends SpringRouteBuilder {
    private final String uri;

    public DelegationSigningResultRouteBuilder(String uri) {
        this.uri = uri;
    }

    @Override
    public void configure() throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
