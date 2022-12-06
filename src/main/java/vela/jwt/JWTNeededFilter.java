package vela.jwt;

import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.crypto.MACVerifier;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import vela.resource.LoginResource;

@Provider
@Priority(Priorities.AUTHORIZATION)
@JWTNeeded
@Slf4j
public class JWTNeededFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        String jwsStr = containerRequestContext.getHeaderString("Authorization");
        try{
            jwsStr = jwsStr.replace("Bearer", "");
            JWSObject jwsObject = JWSObject.parse(jwsStr);
            boolean verified = jwsObject.verify(new MACVerifier(LoginResource.JWT_SECRET));
            if(verified){
                containerRequestContext.setProperty("user", jwsObject.getPayload());
            } else{
                log.info("verification failed");
            }
        } catch(Exception e){
            log.info("verification failed");
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(e).build());
        }
    }
}
