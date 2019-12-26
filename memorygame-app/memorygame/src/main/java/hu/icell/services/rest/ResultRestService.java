package hu.icell.services.rest;

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
import hu.icell.actions.ResultDataAction;
import hu.icell.common.logger.AppLogger;
import hu.icell.common.logger.ThisLogger;
import hu.icell.entities.ResultData;
import hu.icell.entities.User;
import hu.icell.exception.MyApplicationException;
import hu.icell.services.BaseService;
import hu.icell.xsdpojo.pojo.ResultResponse;
import hu.icell.xsdpojo.pojo.ResultRequest;
import hu.icell.xsdpojo.common.common.SuccessType;

import java.util.List;

@Path("/result")
public class ResultRestService extends BaseService {
    @Inject
    private ResultAction resultAction;

    @Inject
    private ResultDataAction resultDataAction;
    
    @Inject
    @ThisLogger
    private AppLogger log;
    
    public static final String XSD_POJO = "xsd_wsdl/hu/icell/xsdpojo/pojo.xsd";
    
    @POST
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResultResponse saveAction(@Context HttpServletRequest request,  ResultRequest resultRequest) throws MyApplicationException {
        
        log.debug("ResultRestService.saveAction >>>");
        try {
            validateByXSD(resultRequest, XSD_POJO);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            throw new MyApplicationException("Save unsuccessful: " + e.getMessage());
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
        resultDataAction.saveResultData(seconds, userId);
        List<ResultData> resultDatas = resultDataAction.getResultsDatas();
        log.info("ResultDatas: " + resultDatas);
        resultDatas = resultDataAction.getResults();
        log.info("ResultDatas by repository: " + resultDatas);
        resultResponse.setSuccess(SuccessType.SUCCESS);
        resultResponse.setUserId(userId);
        //validateByXSD(resultResponse, XSD_POJO);
        log.debug("<<< ResultRestService.saveAction");
        return resultResponse;
    }
}
