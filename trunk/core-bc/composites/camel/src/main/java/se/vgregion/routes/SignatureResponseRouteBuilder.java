package se.vgregion.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.http.HttpProducer;
import org.apache.camel.model.language.ConstantExpression;
import org.apache.camel.model.language.SimpleExpression;
import org.apache.camel.spring.SpringRouteBuilder;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import se.vgregion.handlers.SignatureHandler;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-10-28 17:39
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class SignatureResponseRouteBuilder extends SpringRouteBuilder {
    private String uri;
    private final SignatureHandler signingHandler;
    private String redirectOkUri;
    private String redirectFailUri;

    @Value("${sign.response.protocol}")
    private String signResponseProtocol;
    @Value("${sign.response.bind.ip}")
    private String signResponseBindIp;
    @Value("${sign.response.port}")
    private String signResponsePort;
    @Value("${sign.response.path}")
    private String signResponsePath;

    @Value("${sign.redirect.protocol}")
    private String signRedirectProtocol;
    @Value("${sign.redirect.name}")
    private String signRedirectName;
    @Value("${sign.redirect.port}")
    private String signRedirectPort;
    @Value("${sign.redirect.path.ok}")
    private String signRedirectPathOk;
    @Value("${sign.redirect.path.fail}")
    private String signRedirectPathFail;

    public SignatureResponseRouteBuilder(SignatureHandler signingHandler) {
        this.signingHandler = signingHandler;
    }

    public void init() throws Exception {
        this.uri = String.format("%s://%s%s%s",
                signResponseProtocol,
                signResponseBindIp,
                (StringUtils.isBlank(signResponsePort) || signResponsePort.equals("80")) ?
                        "" : ":"+signResponsePort,
                signResponsePath.startsWith("/") ? signResponsePath : "/" + signResponsePath
                );

        this.redirectOkUri = String.format("%s://%s%s%s",
                signRedirectProtocol,
                (StringUtils.isBlank(signRedirectName) || signRedirectName.equals("localhost")) ?
                    InetAddress.getLocalHost().getHostName() : signRedirectName,
                (StringUtils.isBlank(signRedirectPort) || signRedirectPort.equals("80")) ?
                        "" : ":"+signRedirectPort,
                signRedirectPathOk.startsWith("/") ? signRedirectPathOk : "/" + signRedirectPathOk
                );

        this.redirectFailUri = String.format("%s://%s%s%s",
                signRedirectProtocol,
                (StringUtils.isBlank(signRedirectName) || signRedirectName.equals("localhost")) ?
                    InetAddress.getLocalHost().getHostName() : signRedirectName,
                (StringUtils.isBlank(signRedirectPort) || signRedirectPort.equals("80")) ?
                        "" : ":"+signRedirectPort,
                signRedirectPathFail.startsWith("/") ? signRedirectPathFail : "/" + signRedirectPathFail
                );
    }

    @Override
    public void configure() throws Exception {
        from("jetty:" + uri)
                .errorHandler(deadLetterChannel("direct:error_" + uri))
                .bean(signingHandler, "storeSignature")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 302);
                        exchange.getOut().setHeader("Location", redirectOkUri);
                    }
                })
        ;

        from("direct:error_" + uri)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 302);
                        exchange.getOut().setHeader("Location", redirectFailUri);
                    }
                })
        ;
    }
}
