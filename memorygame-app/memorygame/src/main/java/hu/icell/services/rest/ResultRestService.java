package hu.icell.services.rest;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import hu.icell.common.dto.UserDTO;
import hu.icell.common.logger.AppLogger;
import hu.icell.common.logger.ThisLogger;
import hu.icell.ejb.EjbFinder;
import hu.icell.exception.MyApplicationException;
import hu.icell.managers.interfaces.ResultDataManagerRemote;
import hu.icell.managers.interfaces.ResultManagerRemote;
import hu.icell.services.BaseService;
import hu.icell.xsdpojo.pojo.ResultResponse;
import hu.icell.xsdpojo.pojo.ResultRequest;
import hu.icell.xsdpojo.common.common.SuccessType;

import java.util.List;

@Stateless
@LocalBean
@Path("/result")
public class ResultRestService extends BaseService {
    
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
        UserDTO user;
        if (userId <= 0 || seconds <= 0) {
            resultResponse.setSuccess(SuccessType.ERROR);
            resultResponse.setMessage("Invalid input!");
            return resultResponse;
        } else if (session == null || (user = (UserDTO)session.getAttribute("user")) == null || user.getId() != userId) {
            resultResponse.setSuccess(SuccessType.ERROR);
            resultResponse.setMessage("User is not logged in!");
            return resultResponse;
        }
        EjbFinder.getResultManager().saveResult(seconds, userId);
//        ResultDataManagerRemote resultDataManager = EjbFinder.getResultManager()
//        resultDataManager.saveResultData(seconds, userId);
//        List<ResultDataDTO> resultDatas = resultDataManager.getResultsDatas();
//        log.info("ResultDatas: " + resultDatas);
//        resultDatas = resultDataManager.getResults();
//        log.info("ResultDatas by repository: " + resultDatas);
        resultResponse.setSuccess(SuccessType.SUCCESS);
        resultResponse.setUserId(userId);
        //validateByXSD(resultResponse, XSD_POJO);
        log.debug("<<< ResultRestService.saveAction");
        return resultResponse;
    }

    @Override
    protected AppLogger log() {
        return log;
    }
}
