package se.vgregion.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spring.SpringRouteBuilder;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-10-28 09:59
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class JettyTestRouteBuilder extends SpringRouteBuilder {

    private final String url;

    public JettyTestRouteBuilder(String uri) {
        this.url = uri;
    }

    @Override
    public void configure() throws Exception {
        from("jetty:"+url)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        System.out.println("Hej Jetty");
                    }
                })
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String httpMethod = exchange.getIn().getHeader("CamelHttpMethod", String.class);
                        String inBody = exchange.getIn().getBody(String.class);
                        String bootSize = exchange.getIn().getHeader("bootSize", String.class);
                        String laceing = exchange.getIn().getHeader("laceing", String.class);
                        String msg = null;
                        if ("POST PUT".contains(httpMethod)) {
                            msg = String.format("%s does support body content [%s]" +
                                    "\nListening for bootSize [%s]" +
                                    "\nListening for laceing [%s]", httpMethod, inBody, bootSize, laceing);
                        } else {
                            msg = String.format("%s does not support body content [%s]" +
                                    "\nListening for bootSize [%s]" +
                                    "\nListening for laceing [%s]", httpMethod, inBody, bootSize, laceing);
                        }
                        exchange.getOut().setBody(msg);
                    }
                });
    }
}
