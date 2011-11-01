package se.vgregion.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.vgregion.delegation.DelegationService;
import se.vgregion.delegation.domain.Delegation;
import se.vgregion.proxy.signera.signature.SignatureEnvelope;

import javax.annotation.Resource;

import java.security.SignatureException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-11-01 13:30
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/SignatureResponseRouteBuilder-configuration.xml"})
public class SignatureResponseRouteBuilderTest {
    @Value("${server.endpoint}")
    private String serverEndPoint;

    @Produce(uri = "direct:start")
    protected ProducerTemplate template;

    @Resource
    private DelegationService mockDelegationService;

    @Test
    @DirtiesContext
    public void testIncoming() {
        final String body = "<?xml version=\"1.0\"?>" +
                "<signatureEnvelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://signera.proxy.vgregion.se/signature\" >" +
                "<errorCode>0</errorCode>" +
                "<signatureName>123</signatureName>" +
                "<signature>Apa</signature>" +
                "</signatureEnvelope>";

        Delegation delegation = mock(Delegation.class);

        when(mockDelegationService.find(eq(123L))).thenReturn(delegation);

        Exchange result = template.send(serverEndPoint, new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                exchange.getIn().setBody(body);
                exchange.getIn().setHeader("CamelHttpMethod", "POST");
                System.out.println("Test start");

            }
        });
        Object resultBody = result.getOut().getBody(SignatureEnvelope.class);

        assertTrue(resultBody instanceof SignatureEnvelope);
        assertEquals("Apa", ((SignatureEnvelope)resultBody).getSignature());

        verify(mockDelegationService).approve(delegation, "Apa");
    }
}
