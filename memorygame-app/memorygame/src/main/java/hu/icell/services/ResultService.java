package hu.icell.services;

import java.io.IOException;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import hu.icell.common.dto.ResultDTO;
import hu.icell.common.dto.UserDTO;
import hu.icell.common.logger.AppLogger;
import hu.icell.common.logger.ThisLogger;
import hu.icell.exception.MyApplicationException;
import hu.icell.managers.interfaces.ResultManagerRemote;

@Stateless
@LocalBean
@Path("/result")
public class ResultService {
	
	@Inject
	private ResultManagerRemote resultManager;
    
    @Inject
    @ThisLogger
    private AppLogger log;
    
    
    @GET
    @Produces(MediaType.TEXT_HTML)
    public void listAction(@Context HttpServletRequest request, @Context HttpServletResponse response) throws ServletException, IOException {
        
        log.debug("ResultService.listAction >>>");
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("/memorygame/game/login");
            return;
        }
        List<ResultDTO> list = resultManager.getResultsData();
        
        request.setAttribute("list", list);
        log.debug("<<< ResultService.listAction");
        
        request.getRequestDispatcher("/WEB-INF/views/result/results.jsp")
        .forward(request, response);
    }
    
    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/{userId:\\d+}")
    public void showAction(@Context HttpServletRequest request, @Context HttpServletResponse response, @PathParam("userId") String userId)
            throws ServletException, IOException {
        log.debug("ResultService.showAction, userId=[{}] >>>", userId);
        HttpSession session = request.getSession(false);
        UserDTO user;
        if (session == null || (user = (UserDTO)session.getAttribute("user")) == null || user.getId() != Long.parseLong(userId)) {
            response.sendRedirect("/memorygame/game/login");
            return;
        }
        List<ResultDTO> list = resultManager.getResultsByUser(user);
        request.setAttribute("list", list);
        log.debug("<<< ResultService.showAction");
        
        request.getRequestDispatcher("/WEB-INF/views/result/userresult.jsp")
        .forward(request, response);
    }
    
}
