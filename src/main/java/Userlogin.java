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
import java.util.HashMap;

import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;



@Path("/login")
public class Userlogin{
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
     public String loginTheUser(LoginUser u){
       /*  Iterator it = DBUsers.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " " + pair.getValue());
            it.remove();
        }*/
       

        if(u != null && DBUsers.containsKey(u.username) && (DBUsers.get(u.username).equals(u.password))){
            
            String jwt = service.generatejwt();
            HttpURLConnection connection = null;
            Cookie cookie = new Cookie("jwtoken", jwt);
            try {
                URL url = new URL("http://localhost:8080");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", 
                "application/json");
                connection.setRequestProperty("Authorization", "Bearer" + jwt);
                connection.setDoOutput(true);
          //      DataOutputStream wr = new DataOutputStream (
           // connection.getOutputStream());
            
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        return "logged in";
        }


        return "username or password incorrect";
        
    }
   
    
}
