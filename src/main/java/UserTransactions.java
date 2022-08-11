import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/transactions")
public class UserTransactions {
    String uuid = UUID.randomUUID().toString();
    String name;
    int id;

    String jdbcURL = "jdbc:postgresql://localhost:5432/prva_baza";
    String username = "postgres";
	String password = "student";
    
    Connection connection;
    PreparedStatement stmt;
    ResultSet rs;
    
    public UserTransactions(){        

    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String maketransaction(Deposit d){

        System.out.println(d.username + " " + d.value);
        String sql = "INSERT INTO transactions ";
       /*  connection =  DriverManager.getConnection(jdbcURL,username,password);
        stmt = connection.prepareStatement(sql);
        rs = stmt.executeQuery();
        */
        return "uspjesno izvrseno";
    }


    
}
