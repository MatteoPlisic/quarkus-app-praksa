
import javax.enterprise.context.ApplicationScoped;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/jwt")
@ApplicationScoped
public class BankJwtResource {

    @Inject
    BankJwtService service;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getJwt(){
        String jwt = service.generatejwt();
        System.out.println(jwt);
        return Response.ok(jwt).build();
        
    }

    public void sendjwt(){
           
    }
    
}