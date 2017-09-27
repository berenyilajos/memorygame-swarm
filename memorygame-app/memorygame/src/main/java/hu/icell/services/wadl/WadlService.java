package hu.icell.services.wadl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.apache.commons.io.IOUtils;

import hu.icell.common.logger.AppLogger;
import hu.icell.common.logger.ThisLogger;
import hu.icell.app.RestApplication;
import hu.icell.exception.MyApplicationException;

import javax.inject.Inject;

@Model
public class WadlService  implements IWadlService {
	
	@Inject
	EntityManager em;
    
    @Inject
    @ThisLogger
    private AppLogger log;
	
	@Override
	public Response getApplicationWadl() throws MyApplicationException {
        log.debug("WadlService.getApplicationWadl >>>");
		InputStream is = null;
		String path = "/application.wadl";

		final byte[] buffer = getResourceBuffer(is, path);

		StreamingOutput so = new StreamingOutput() {
			public void write(OutputStream os) throws IOException {
				os.write(buffer);
			}
		};
        log.debug("<<< WadlService.getApplicationWadl");
        
		return Response.ok(so).header("content-type", MediaType.APPLICATION_XML).build();
	}

	@Override
	public Response getApplicationXsd(String xsdName) throws MyApplicationException {
        log.debug("WadlService.getApplicationXsd, xsdName[{}] >>>", xsdName);
		InputStream is = null;
		String path = null;
		
		Pattern p = Pattern.compile("[a-zA-Z0-9_]");
		Matcher m = p.matcher(xsdName);
		if (m.find()) {
			path = "/" + xsdName + ".xsd";
		} else {
			return null;
		}

		final byte[] buffer = getResourceBuffer(is, path);

		StreamingOutput so = new StreamingOutput() {
			public void write(OutputStream os) throws IOException {
				os.write(buffer);
			}
		};
        log.debug("<<< WadlService.getApplicationXsd");
        
		return Response.ok(so).header("content-type", MediaType.APPLICATION_XML).build();
	}
	
	@Override
    public String testAlive() {
        log.debug("WadlService.testAlive >>>");
        return "OK";
    }
    
    @Override
    public String testConnection() {
        log.debug("WadlService.testConnection >>>");
        em.createNativeQuery("select 1 from dual").getResultList();
        return "OK";
    }
    
    @Override
    public String getVersionInfo(@Context HttpServletRequest servletRequest) {
        log.debug("WadlService.getVersionInfo >>>");
        InputStream inputStream = servletRequest.getServletContext().getResourceAsStream("/META-INF/MANIFEST.MF");
        String version = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = br.readLine()) != null) {
               version += line + "\n";
            }
        } catch (IOException e) {
            version = "undefinied";
        }
        log.debug("<<< WadlService.getVersionInfo:\n{}", version);
        
        return version;
    }

	private byte[] getResourceBuffer(InputStream is, String path) throws MyApplicationException {
		byte[] buffer = null;
		try {
			is = RestApplication.class.getResourceAsStream(path);
			if (is == null) {
				throw new MyApplicationException("Resource not found.");
			}
			buffer = IOUtils.toByteArray(is);
		} catch (IOException e) {
			throw new MyApplicationException(e.getMessage(), e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					throw new MyApplicationException(e.getMessage(), e);
				}
			}
		}
		return buffer;
	}

}
