package hu.icell.services;

import java.io.IOException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import hu.icell.common.dto.UserDTO;
import hu.icell.common.logger.AppLogger;
import hu.icell.common.logger.ThisLogger;
import hu.icell.ejb.EjbFinder;
import hu.icell.exception.MyApplicationException;
import hu.icell.exception.UserAllreadyExistException;
import hu.icell.managers.interfaces.IndexManagerRemote;

@Stateless
@LocalBean
@Path("")
public class IndexService {
    
    @Inject
    @ThisLogger
    private AppLogger log;
    
    @GET
    @Produces(MediaType.TEXT_HTML)
//    @Path("")
    public void indexAction(@Context HttpServletRequest request, @Context HttpServletResponse response) throws IOException, ServletException {
        log.debug("IndexService.indexAction >>>");
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("/memorygame/game/login");
            return;
        }
        log.debug("<<< IndexService.indexAction");
        request.getRequestDispatcher("/WEB-INF/views/index.jsp")
        .forward(request, response);
    }
    
    @GET
    @POST
    @Produces(MediaType.TEXT_HTML)
    @Path("/login")
    public void loginAction(@Context HttpServletRequest request, @Context HttpServletResponse response, @FormParam("username") String username,
            @FormParam("password") String password) throws ServletException, IOException {
        log.debug("IndexService.loginAction, username=[{}] >>>", username);
        String msg = "";
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
            UserDTO user = EjbFinder.getIndexManager().getUserByUsernameAndPassword(username, password);
            if (user == null) {
                msg = "Hibás felhasználónév vagy jelszó!";
            } else {
                HttpSession session = request.getSession(true);
                session.setAttribute("user", user);
                response.sendRedirect("/memorygame/game");
                return;
            }
        }
        request.setAttribute("msg", msg);
        log.debug("<<< IndexService.loginAction");
        
        request.getRequestDispatcher("/WEB-INF/views/login.jsp")
        .forward(request, response);

    }
    
    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/logout")
    public void logoutAction(@Context HttpServletRequest request, @Context HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        log.debug("IndexService.logoutAction, username=[{}] >>>", session != null ? (UserDTO)session.getAttribute("user") : null);
        if (session != null) {
            session.setAttribute("user", null);
            session = null;
        }
        log.debug("<<< IndexService.logoutAction");
        response.sendRedirect("/memorygame/game/login");

    }
    
    @GET
    @POST
    @Produces(MediaType.TEXT_HTML)
    @Path("/register")
    public void registerAction(@Context HttpServletRequest request, @Context HttpServletResponse response,
            @FormParam("username") String username,
            @FormParam("password") String password, @FormParam("password2") String password2) throws ServletException, IOException {
        log.debug("IndexService.registerAction, username=[{}] >>>", username);
        String msg = "";
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password) && StringUtils.isNotBlank(password2)) {
//            if (indexAction.getUserByUsername(username) != null) {
//                msg = "Ez a felhasználónév már létezik!";
//            }
            if (!password.equals(password2)) {
                msg = "A jelszó és megerősítése nem egyezik!";
            }
            if (msg.isEmpty()) {
                try {
                    EjbFinder.getIndexManager().saveUser(username, password);
                    response.sendRedirect("/memorygame/game/login");
                    return;
                } catch(UserAllreadyExistException ex) {
                    msg = ex.getMessage();
                }
            }
        }
        request.setAttribute("msg", msg);
        log.debug("<<< IndexService.registerAction");
        
        request.getRequestDispatcher("/WEB-INF/views/register.jsp")
        .forward(request, response);

    }
}
