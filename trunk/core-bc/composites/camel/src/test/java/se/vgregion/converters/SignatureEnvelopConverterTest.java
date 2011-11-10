package se.vgregion.converters;

import org.junit.Test;
import se.vgregion.signera.signature._1.SignatureEnvelope;

import static org.junit.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-10-31 17:52
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class SignatureEnvelopConverterTest {
    @Test
    public void testToSignatureEnvelope() throws Exception {
        String xml = "<?xml version=\"1.0\"?>" +
                "<signatureEnvelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://signera.vgregion.se/signature/1/\" >" +
                "<errorCode>0</errorCode>" +
                "<signature>Apa</signature>" +
                "</signatureEnvelope>";

        SignatureEnvelope signatureEnvelope = SignatureEnvelopConverter.toSignatureEnvelope(xml);

        assertEquals("Apa", signatureEnvelope.getSignature());
    }

    @Test(expected = RuntimeException.class)
    public void testToSignatureEnvelopeFail() throws Exception {
        String xml = "<?xml version=\"1.0\"?>" +
                "<signatureEnvelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://signera.vgregion.se/signature/1/\" >" +
                "<errorCod>0</errorCode>" +
                "<signature>Apa</signature>" +
                "</signatureEnvelope>";

        SignatureEnvelope signatureEnvelope = SignatureEnvelopConverter.toSignatureEnvelope(xml);
    }
}
