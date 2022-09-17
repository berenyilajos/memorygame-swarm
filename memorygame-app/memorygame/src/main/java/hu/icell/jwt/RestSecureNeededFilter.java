package hu.icell.jwt;

import hu.icell.common.logger.AppLogger;
import hu.icell.common.logger.ThisLogger;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import java.io.IOException;

import static javax.ws.rs.core.Response.Status;

@Provider
@RestSecure
@Priority(Priorities.AUTHENTICATION)
public class RestSecureNeededFilter implements ContainerRequestFilter {

    @Inject
    @ThisLogger
    private AppLogger log;
    
    @Inject
    private JwtUtil jwtUtil;
    
    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        log.info("running filter ContainerRequestContext method...");
        RestSecure filter = resourceInfo.getResourceMethod().getAnnotation(RestSecure.class);
        String userName = null;
        boolean userInRole = false;
        if (requestContext.getSecurityContext() != null && requestContext.getSecurityContext().getUserPrincipal() != null) {
            userName = requestContext.getSecurityContext().getUserPrincipal().getName();
            userInRole = requestContext.getSecurityContext().isUserInRole(filter.role());
            log.debug("Username: " + userName + ", in role " + filter.role() + ": " + userInRole);
        }
        if (userName == null) {
            log.debug("JwtFilter: user not logged in, path: " + requestContext.getUriInfo().getPath());
            requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
        } else if (!userInRole) {
        	log.debug("JwtFilter: user has no role: " + filter.role() + ", path: " + requestContext.getUriInfo().getPath());
            requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
        } else {
        	String authorization = (String) requestContext.getHeaderString(JwtUtil.AUTHORIZATION);
        	if (authorization == null || !authorization.startsWith(JwtUtil.BEARER)) {
        		log.debug("JwtFilter: user user " + userName + " has no Bearer token, path: " + requestContext.getUriInfo().getPath());
                requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
        	} else if (!jwtUtil.isValidToken(authorization.substring(JwtUtil.BEARER.length()), userName)) {
        		log.debug("JwtFilter: user user " + userName + " has no valid token, path: " + requestContext.getUriInfo().getPath());
                requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
        	} else {
        		log.debug("JwtFilter: user " + userName + " logged in, has valid token, path: " + requestContext.getUriInfo().getPath());
        	}
        }
    }
}
