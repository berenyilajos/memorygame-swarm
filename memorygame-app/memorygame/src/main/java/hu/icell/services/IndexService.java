package hu.icell.services;

import java.io.IOException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.Models;
import javax.mvc.binding.BindingResult;
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
import javax.ws.rs.core.SecurityContext;

import hu.icell.encrypt.Encrypter;
import org.apache.commons.lang3.StringUtils;

import hu.icell.common.dto.UserDTO;
import hu.icell.common.logger.AppLogger;
import hu.icell.common.logger.ThisLogger;
import hu.icell.exception.UserAllreadyExistException;
import hu.icell.jwt.JwtUtil;
import hu.icell.managers.interfaces.IndexManagerRemote;

@Controller
@Stateless
@LocalBean
@Path("")
public class IndexService {

    @Inject
    Models models;

    @Inject
    BindingResult bindingResult;
    
    @Inject
    private JwtUtil jwtUtil;
	
	@Inject
	private IndexManagerRemote indexManager;
    
    @Inject
    @ThisLogger
    private AppLogger log;
    
    @GET
    @Produces(MediaType.TEXT_HTML)
//    @Path("")
    public String indexAction(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        log.debug("IndexService.indexAction >>>");
        HttpSession session = request.getSession(false);
        UserDTO user;
        if (session == null || (user = (UserDTO) session.getAttribute("user")) == null) {
            String url = request.getRequestURL().toString();
            if (url.endsWith("/")) {
                return "redirect:/login";
            } else {
                log.debug("redirect:/login doesn't work, we will try with //");
                return "redirect://login";
            }
        }
        String token = jwtUtil.generateToken(user.getUsername());
        models.put("token", token);
        log.debug("<<< IndexService.indexAction");
        return "index.html";
    }
    
    @GET
    @POST
    @Produces(MediaType.TEXT_HTML)
    @Path("/login")
    public String loginAction(@Context SecurityContext securityContext, @Context HttpServletRequest request, @Context HttpServletResponse response, @FormParam("username") String username,
                            @FormParam("password") String password) throws ServletException, IOException {
        log.debug("IndexService.loginAction, username=[{}] >>>", username);
        String msg = "";
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
            UserDTO user = indexManager.getUserByUsernameAndPassword(username, password);
            if (user == null) {
                msg = "Hibás felhasználónév vagy jelszó!";
            } else {
                if (request.isSecure()) {
                    request.logout();
                }
                HttpSession session = request.getSession(true);
                session.setAttribute("user", user);
                request.login(username, Encrypter.getMD5(password));
                log.info("Login successful. User principal after login: " + request.getUserPrincipal() + ", in role " + JwtUtil.USER_ROLE + ": " + securityContext.isUserInRole(JwtUtil.USER_ROLE));
                return "redirect:";
            }
        }
        models.put("msg", msg);
        log.debug("<<< IndexService.loginAction");

        return "login.html";

    }
    
    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/logout")
    public String logoutAction(@Context HttpServletRequest request, @Context HttpServletResponse response) throws IOException, ServletException {
        request.logout();
        HttpSession session = request.getSession(false);
        log.debug("IndexService.logoutAction, username=[{}] >>>", session != null ? (UserDTO)session.getAttribute("user") : null);
        if (session != null) {
            session.setAttribute("user", null);
            request.logout();
            log.info("Logout successful. User principal after login: " + request.getUserPrincipal());
            session.invalidate();
        }
        log.debug("<<< IndexService.logoutAction");
        return "redirect:/login";

    }
    
    @GET
    @POST
    @Produces(MediaType.TEXT_HTML)
    @Path("/register")
    public String registerAction(@Context HttpServletRequest request, @Context HttpServletResponse response,
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
                    indexManager.saveUser(username, password);
                    return "redirect:/login";
                } catch(UserAllreadyExistException ex) {
                    msg = ex.getMessage();
                }
            }
        }
        models.put("msg", msg);
        log.debug("<<< IndexService.registerAction");
        
        return "register.html";

    }
}
