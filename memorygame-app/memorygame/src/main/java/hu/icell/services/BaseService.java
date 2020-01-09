package hu.icell.services;

import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import hu.icell.common.logger.AppLogger;
import hu.icell.error.MarshallingUtil;
import hu.icell.error.MyErrorHandler;
import hu.icell.error.ValidationErrorCollector;

public abstract class BaseService {
    
    protected void validateByXSD(Object xmlObject, String xsd) throws Exception {
        StringWriter stringWriter = new StringWriter();
        MyErrorHandler eh = new MyErrorHandler();
        ValidationErrorCollector errorCollector = new ValidationErrorCollector();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(xmlObject
                    .getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            stringWriter.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            StreamSource src = null;
            SchemaFactory sf = null;
            InputStream stream = null;

            stream = this.getClass().getClassLoader()
                    .getResourceAsStream(xsd);
            src = new StreamSource(stream);
            if (stream == null) {
                throw new Exception("cannot find schema to validate");
            }
            sf = SchemaFactory
                    .newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
            sf.setResourceResolver(new hu.icell.error.ResourceResolver(
                    xsd, eh.getErrorBuffer()));
            Schema schema = sf.newSchema(src);
            marshaller.setEventHandler(errorCollector);
            marshaller.setSchema(schema);
            marshaller.marshal(xmlObject, stringWriter);
            if (errorCollector.getErrors().size() > 0) {
                log().warn("xml validation error(s) occured!");
                if (!log().isTraceEnabled()) {
                    String xml = MarshallingUtil.marshall(xmlObject);
                    log().info("xml content: {} ", new Object[]{(xml == null ? "" : xml)});
                }
                Exception ire = new Exception(
                        errorCollector.getErrors().get(0).getError());
                throw ire;
            }
        } finally {
            
        }
    }

    protected abstract AppLogger log();
    
}
