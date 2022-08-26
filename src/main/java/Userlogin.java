import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.Builder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;

import java.util.Map;
import java.util.Optional;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.inject.Inject;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

//import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import io.netty.handler.codec.http.HttpStatusClass;
import io.vertx.mutiny.ext.web.sstore.LocalSessionStore;



@Path("/login")
public class Userlogin {
    String jdbcURL = "jdbc:postgresql://localhost:5432/projektbaza";
    String username = "admin";
	String password = "admin";
    String sql = "SELECT username, password FROM USERS"; 
   public HashMap<String,String> DBUsers = new HashMap<>();
   Connection connection;
   PreparedStatement stmt;
    ResultSet rs;

    @Inject
    BankJwtService service;
    public Userlogin(){
        
    try {
        connection =  DriverManager.getConnection(jdbcURL,username,password);
        stmt = connection.prepareStatement(sql);
        rs = stmt.executeQuery();
        while(rs.next()){
        String USER = rs.getString("username");
        String PASS = rs.getString("password");
        DBUsers.put(USER,PASS);
        
        }
        connection.close();
    } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
   

    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
     public Response loginTheUser(LoginUser u){
        
        Encoder encoder = Base64.getEncoder();
        u.password = encoder.encodeToString(u.password.getBytes());
        if(u != null && DBUsers.containsKey(u.username) && (DBUsers.get(u.username).equals(u.password))){
        
            String jwt = service.generatejwt();
        System.out.println(jwt);
        return Response.ok(jwt).build();
            
            
            
        
        }


        return  Response.ok("Wrong username or password").build();

        
    }
      
      
   
    


}
