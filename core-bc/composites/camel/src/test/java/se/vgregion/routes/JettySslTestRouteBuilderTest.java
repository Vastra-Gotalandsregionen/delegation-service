package se.vgregion.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-10-28 16:03
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
@ContextConfiguration(locations = {"/META-INF/JettySslTestRouteBuilder-configuration.xml"})
public class JettySslTestRouteBuilderTest extends AbstractJUnit4SpringContextTests {

    @Value("${server.protocol}://${server.name}:${server.port}/${server.path}?httpClientConfigurer=httpsConfigurer")
    private String serverEndPoint;

    @Produce(uri = "direct:start")
    protected ProducerTemplate template;

    @Test
    @DirtiesContext
    public void testJettyHttpGet1() {
        String response = call(serverEndPoint, null, null);

        Assert.assertEquals("GET does not support body content [null]\n" +
                "Listening for bootSize [null]\n" +
                "Listening for laceing [null]", response);
    }

    @Test
    @DirtiesContext
    public void testJettyHttpGet2() {
        String response = call(serverEndPoint + "&bootSize=small&laceing=tight", null, null);

        Assert.assertEquals("GET does not support body content [null]\n" +
                "Listening for bootSize [small]\n" +
                "Listening for laceing [tight]", response);
    }

    @Test
    @DirtiesContext
    public void testJettyHttpGet3() {
        String response = call(serverEndPoint + "&bootSize=small&laceing=tight", null, "Apa");

        Assert.assertEquals("GET does not support body content [null]\n" +
                "Listening for bootSize [small]\n" +
                "Listening for laceing [tight]", response);
    }

    @Test
    @DirtiesContext
    public void testJettyHttpPost() {
        String response = call(serverEndPoint + "&bootSize=small&laceing=tight", "POST", "Apa");

        Assert.assertEquals("POST does support body content [Apa]\n" +
                "Listening for bootSize [small]\n" +
                "Listening for laceing [tight]", response);
    }

    @Test
    @DirtiesContext
    public void testJettyHttpPut() {
        String response = call(serverEndPoint + "&bootSize=small&laceing=tight", "PUT", "Apa");

        Assert.assertEquals("PUT does support body content [Apa]\n" +
                "Listening for bootSize [small]\n" +
                "Listening for laceing [tight]", response);
    }

    @Test
    @DirtiesContext
    public void testJettyHttpDelete() {
        String response = call(serverEndPoint + "&bootSize=small&laceing=tight", "DELETE", "Apa");

        Assert.assertEquals("DELETE does not support body content []\n" +
                "Listening for bootSize [small]\n" +
                "Listening for laceing [tight]", response);
    }

    private String call(String url, final String httpMethod, final String body) {
        Exchange result = template.send(url, new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                exchange.getIn().setBody(body);
                if (httpMethod != null) {
                    exchange.getIn().setHeader("CamelHttpMethod", httpMethod);
                }
                System.out.println("Test start");

            }
        });
        return result.getOut().getBody(String.class);
    }
}
