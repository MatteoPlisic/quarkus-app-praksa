import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.print.attribute.standard.Media;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class UserResource{
	String jdbcURL = "jdbc:postgresql://localhost:5432/projektbaza";
	
	//izlistavanje svih usera (guessam da sam trebao sve informacije ne samo imena? not sure)
	@RolesAllowed("admin")
	@Path("/list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public  Response queryUsers(){
	
		String username = "postgres";
		String password = "doggo";
		
		 Set<User> Users = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));
	
		

	try{	
	Connection connection = DriverManager.getConnection(jdbcURL,username,password);
	PreparedStatement stmt;
	ResultSet rs;
	String sql = "SELECT * FROM users";
	try{	
		
		stmt = connection.prepareStatement(sql);
		rs = stmt.executeQuery();
		while(rs.next()){
			User tmp = new User();
		System.out.printf("%s\t%s\t%s\t%s\t%s\t%s\t%s",
		
		tmp.first_name = rs.getString("first_name"),
		tmp.last_name = rs.getString("last_name"),
		tmp.organization = rs.getString("organization"),
		tmp.added =rs.getString("added"),
		tmp.username =rs.getString("username"),
		tmp.password =rs.getString("password"),
		tmp.role = rs.getString("role"));
			Users.add(tmp);
		}
		rs.close();
		stmt.close();
		

	    }	
	catch(SQLException e){
		e.printStackTrace();
	}
	
	connection.close();
	}catch(SQLException e){
		e.printStackTrace();
	}
	return Response.ok(Users).build();
}	
	//davanje informacija o useru sa nekim imenom
	@Path("/list/{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listSpecificUser(String name){
		
		String username = "postgres";
		String password = "doggo";
		
		 Set<User> Users = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));
	


	try{	
	Connection connection = DriverManager.getConnection(jdbcURL,username,password);
	PreparedStatement stmt;
	ResultSet rs;
	name = "'" + name + "'";
	
	String sql = "SELECT * FROM users WHERE username = "  + name;
	try{	
		
		stmt = connection.prepareStatement(sql);
		rs = stmt.executeQuery();
		while(rs.next()){
			User tmp = new User();
		
		
		tmp.first_name = rs.getString("first_name");
		tmp.last_name = rs.getString("last_name");
		tmp.organization = rs.getString("organization");
		tmp.added =rs.getString("added");
		tmp.id = rs.getInt("user_id");
		tmp.username =rs.getString("username");
		tmp.password =rs.getString("password");
		tmp.role = rs.getString("role");
		System.out.println(tmp.first_name);
		
		Users.add(tmp);
		}
		rs.close();
		stmt.close();
		

	    }	
	catch(SQLException e){
		e.printStackTrace();
	}
	
	connection.close();
	}catch(SQLException e){
		e.printStackTrace();
	}
	return Response.ok(Users).build();
	}
	//brisanje korisnika po imenu
	@Path("/delete/{name}")
	@DELETE
	@Produces(MediaType.TEXT_PLAIN)
	public String DeleteUser(String name){
		
		String username = "postgres";
		String password = "doggo";
		name = "'" + name + "'";
		String sql = "DELETE FROM users WHERE first_name = " + name;

		try {
			Connection connection = DriverManager.getConnection(jdbcURL,username,password);
			PreparedStatement stmt;
			
			stmt = connection.prepareStatement(sql);
			int deleted = stmt.executeUpdate();
			if(deleted>0)
			return "succesful deletion of user " + name;

			else
				return "user does not exist";
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "failed deletion of user "+name;
	}

	//kreiranje novog usera
	@Path("/create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response CreateUser(User user){
		
		String username = "postgres";
		String password = "doggo";
		user.first_name = "'" + user.first_name + "'";
		user.last_name = "'" + user.last_name + "'";
		user.organization = "'" + user.organization + "'";
		user.added = "'" + user.added + "'";
		user.username = "'" + user.username + "'";
		user.password = "'" + user.password+ "'";
		user.role = "'" + user.role+ "'";
		//System.out.println(user.added);
		String sql = "INSERT INTO users (first_name,last_name,organization,added,username,password,role) VALUES (" + user.first_name + "," + user.last_name + "," + user.organization + "," + user.added + "," + user.username + "," + user.password + "," + user.role + ")";
		try {
			Connection connection = DriverManager.getConnection(jdbcURL,username,password);
			PreparedStatement stmt;

			stmt = connection.prepareStatement(sql);
			stmt.executeUpdate();
			System.out.println("Uspjeh");
			return Response.ok(user).build();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	//updatea userovu organizaciju, treba poslati tocno old:nesto i nova:nesto inace ne radi
	@Path("/update")
	@PATCH
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public String UpdateUser(Update u){
		
		String username = "postgres";	
		String password = "doggo";
		u.nova = "'" + u.nova + "'";
		u.old = "'" + u.old + "'";
		
		String sql = "UPDATE users SET organization = " + u.nova + " WHERE organization =" + u.old;

		try {
			Connection connection = DriverManager.getConnection(jdbcURL,username,password);
			PreparedStatement stmt;

			stmt = connection.prepareStatement(sql);
			
			stmt.executeUpdate();
			return "Succesful update";
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Failed update";
		
	}
	/* 
	@Path("/register")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String Register(){



	}*/

	
}
