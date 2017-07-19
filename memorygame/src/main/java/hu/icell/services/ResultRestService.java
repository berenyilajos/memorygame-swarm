package hu.icell.services;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import hu.icell.actions.ResultAction;
import hu.icell.entities.User;
import hu.icell.exception.*;
import hu.icell.xsdpojo.ResultResponseType;
import hu.icell.xsdpojo.WrapperObject;
import hu.icell.xsdpojo.common.SuccessType;

@Path("/result")
public class ResultRestService extends BaseService {
    @Inject
    private ResultAction resultAction;
    
    public static final String XSD_POJO = "xsd_wsdl/hu/icell/xsdpojo/pojo.xsd";
    
    @POST
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResultResponseType saveAction(@Context HttpServletRequest request,  WrapperObject wrapperObject) throws MyApplicationException {
        
        try {
            validateByXSD(wrapperObject, XSD_POJO);
        } catch (Exception e) {
            throw new MyApplicationException(e.getMessage());
        }
        
        ResultResponseType resultResponse = new ResultResponseType();
//        Integer seconds = Integer.parseInt(wrapperObject.get("seconds").toString());
//        Long userId = Long.parseLong(wrapperObject.get("userId").toString());
        Integer seconds = wrapperObject.getSeconds();
        Long userId = wrapperObject.getUserId();
        resultResponse.setSeconds(seconds);
        HttpSession session = request.getSession(false);
        User user;
        if (userId <= 0 || seconds <= 0 || session == null || (user = (User)session.getAttribute("user")) == null || user.getId() != userId) {
            resultResponse.setSuccess(SuccessType.ERROR);
            resultResponse.setMessage("Invalid input");
            return resultResponse;
        }
        resultAction.saveResult(seconds, userId);
        resultResponse.setSuccess(SuccessType.SUCCESS);
        //validateByXSD(resultResponse, XSD_POJO);
        return resultResponse;
    }
}
