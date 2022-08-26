import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Base64.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/register")
public class RegisterUser {
    

    public RegisterUser(){

    }
    
    

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public String registermethod(User register){
        String jdbcURL = "jdbc:postgresql://localhost:5432/projektbaza";
        String username = "admin";
        String password = "admin";
        Encoder encoder = Base64.getEncoder();
        register.password = encoder.encodeToString(register.password.getBytes());
        register.first_name = "'" + register.first_name + "'";
        register.last_name = "'" + register.last_name + "'";
        register.organization = "'" + register.organization + "'";
        register.added = "'" + register.added + "'";
        register.username = "'" + register.username + "'";
        register.password = "'" + register.password + "'";
        

        try {
            Connection connection = DriverManager.getConnection(jdbcURL,username,password);
            PreparedStatement stmt;
            String sql = "INSERT INTO users (first_name,last_name,organization,added,username,password,role) VALUES (" + 
            register.first_name + "," 
            + register.last_name + ","
            + register.organization
            + "," + register.added
            + "," + register.username 
            + "," + register.password + 
            "," + "'helpdesk'" + ")";
            stmt = connection.prepareStatement(sql);
			stmt.executeUpdate();
            connection.close();
            return "Succesful registration";
            
        } catch (SQLException e) {
            e.printStackTrace();
            return "User with that username already exists";
            
        }
       // return "Failed registration";
    }

    
}
