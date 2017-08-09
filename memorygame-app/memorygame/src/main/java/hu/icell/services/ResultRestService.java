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
import hu.icell.xsdpojo.ResultResponse;
import hu.icell.xsdpojo.ResultRequest;
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
    public ResultResponse saveAction(@Context HttpServletRequest request,  ResultRequest resultRequest) throws MyApplicationException {
        
        try {
            validateByXSD(resultRequest, XSD_POJO);
        } catch (Exception e) {
            throw new MyApplicationException(e.getMessage());
        }
        
        ResultResponse resultResponse = new ResultResponse();
//        Integer seconds = Integer.parseInt(wrapperObject.get("seconds").toString());
//        Long userId = Long.parseLong(wrapperObject.get("userId").toString());
        Integer seconds = resultRequest.getSeconds();
        Long userId = resultRequest.getUserId();
        resultResponse.setSeconds(seconds);
        HttpSession session = request.getSession(false);
        User user;
        if (userId <= 0 || seconds <= 0) {
            resultResponse.setSuccess(SuccessType.ERROR);
            resultResponse.setMessage("Invalid input!");
            return resultResponse;
        } else if (session == null || (user = (User)session.getAttribute("user")) == null || user.getId() != userId) {
            resultResponse.setSuccess(SuccessType.ERROR);
            resultResponse.setMessage("User is not logged in!");
            return resultResponse;
        }
        resultAction.saveResult(seconds, userId);
        resultResponse.setSuccess(SuccessType.SUCCESS);
        //validateByXSD(resultResponse, XSD_POJO);
        return resultResponse;
    }
}
