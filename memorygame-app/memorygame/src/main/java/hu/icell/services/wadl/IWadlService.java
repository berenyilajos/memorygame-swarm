package hu.icell.services.wadl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import hu.icell.exception.MyApplicationException;

@Path("/game")
@Produces(value = { MediaType.TEXT_XML, MediaType.APPLICATION_XML })
public interface IWadlService {

	@GET
	@Path("/application.wadl")
	public Response getApplicationWadl() throws MyApplicationException;

	@GET
	@Path("/{xsdName}.xsd")
	public Response getApplicationXsd(@PathParam("xsdName") String xsdName) throws MyApplicationException;
	
	@GET
    @Path("/testAlive")
    @Produces("text/plain")
    public String testAlive();
    
    @GET
    @Path("/versionInfo")
    @Produces("text/plain")
    public String getVersionInfo(@Context HttpServletRequest servletRequest);

}
