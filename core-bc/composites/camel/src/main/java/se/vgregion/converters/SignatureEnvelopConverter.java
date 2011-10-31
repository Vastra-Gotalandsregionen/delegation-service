package se.vgregion.converters;

import org.apache.camel.Converter;
import se.vgregion.proxy.signera.signature.SignatureEnvelope;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-10-31 17:26
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
@Converter
public class SignatureEnvelopConverter {

    @Converter
    public static SignatureEnvelope toSignatureEnvelope(String xml) {
        try {
            JAXBContext jc = JAXBContext.newInstance("se.vgregion.proxy.signera.signature");
            //Create marshaller
            Unmarshaller m = jc.createUnmarshaller();
            //Marshal object into file.
            return (SignatureEnvelope)m.unmarshal(new StringReader(xml));
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to serialize message", e);
        }

    }
}
