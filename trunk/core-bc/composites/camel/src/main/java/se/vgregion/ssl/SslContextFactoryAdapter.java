package se.vgregion.ssl;

import org.eclipse.jetty.http.ssl.SslContextFactory;
import org.eclipse.jetty.util.resource.Resource;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-10-29 17:29
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class SslContextFactoryAdapter {

    /**
     * This class is not insatiable.
     */
    private SslContextFactoryAdapter() {
    }

    /**
     * Adapter factory method to allow spring instantiation of SslContextFactory.
     *
     * @param keyStore - keyStore Resource.
     * @param trustStore - trustStore Resource.
     * @return SslContextFactory
     */
    public static SslContextFactory createSslContextFactory(Resource keyStore, Resource trustStore) {
        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStoreResource(keyStore);
        sslContextFactory.setTrustStore(trustStore);
        return sslContextFactory;
    }
}
