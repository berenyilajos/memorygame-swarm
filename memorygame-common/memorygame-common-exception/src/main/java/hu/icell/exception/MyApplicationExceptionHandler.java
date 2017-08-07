package hu.icell.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import hu.icell.xsdpojo.ResultResponseType;
import hu.icell.xsdpojo.common.SuccessType;
 
@Provider
public class MyApplicationExceptionHandler implements ExceptionMapper<MyApplicationException> {
    
    @Override
    public Response toResponse(MyApplicationException exception) 
    {
        ResultResponseType rrt = new ResultResponseType();
        rrt.setSuccess(SuccessType.ERROR);
        rrt.setMessage(exception.getMessage());
        return Response.status(Status.BAD_REQUEST).entity(rrt).build();  
    }
}
