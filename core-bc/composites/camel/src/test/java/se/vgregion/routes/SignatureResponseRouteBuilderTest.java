package se.vgregion.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.http.HttpOperationFailedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.vgregion.delegation.DelegationService;
import se.vgregion.delegation.domain.DelegationBlock;

import javax.annotation.Resource;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

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
                "<signatureEnvelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://signera.vgregion.se/signature/1/\" >" +
                "<errorCode>0</errorCode>" +
                "<signatureName>A-123</signatureName>" +
                "<signature>Apa</signature>" +
                "</signatureEnvelope>";

        DelegationBlock delegation = mock(DelegationBlock.class);

        when(mockDelegationService.find(eq(456L))).thenReturn(delegation);

        Exchange result = template.send(serverEndPoint+"?correlationId=123&delegationId=456", new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                exchange.getIn().setBody(body);
                exchange.getIn().setHeader("CamelHttpMethod", "POST");
                System.out.println("Test start");

            }
        });

        HttpOperationFailedException resultException = result.getException(HttpOperationFailedException.class);

        assertEquals(302, resultException.getStatusCode());

        assertTrue(resultException.getRedirectLocation().startsWith("http://"));
        assertTrue(resultException.getRedirectLocation().endsWith(":8080/group/vgregion/start"));

        verify(mockDelegationService).approve(delegation, "Apa");
    }

    @Test
    @DirtiesContext
    public void testIncomingStoreFail() {
        final String body = "<?xml version=\"1.0\"?>" +
                "<signatureEnvelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://signera.vgregion.se/signature/1/\" >" +
                "<errorCode>0</errorCode>" +
                "<signatureName>A-123</signatureName>" +
                "<signature>Apa</signature>" +
                "</signatureEnvelope>";

        DelegationBlock delegation = mock(DelegationBlock.class);

        when(mockDelegationService.find(eq(456L))).thenThrow(new RuntimeException());

        Exchange result = template.send(serverEndPoint+"?correlationId=123&delegationId=456", new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                exchange.getIn().setBody(body);
                exchange.getIn().setHeader("CamelHttpMethod", "POST");
                System.out.println("Test start");

            }
        });

        HttpOperationFailedException resultException = result.getException(HttpOperationFailedException.class);

        assertEquals(302, resultException.getStatusCode());

        assertTrue(resultException.getRedirectLocation().startsWith("http://"));
        assertTrue(resultException.getRedirectLocation().endsWith(":8080/"));

        verify(mockDelegationService, never()).approve(delegation, "Apa");
    }
}
