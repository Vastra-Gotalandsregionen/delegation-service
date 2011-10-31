package se.vgregion.ssl;

import org.eclipse.jetty.http.ssl.SslContextFactory;
import org.eclipse.jetty.util.resource.Resource;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-10-29 17:29
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class SpringSslContextFactory extends SslContextFactory {
    public void setTrustStoreResource(Resource resource) throws Exception {
        setTrustStore(resource);
    }
}
