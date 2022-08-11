
public class User {
	
	public int id;
	public String first_name;
	public String last_name;
	public String organization;
	public String added;
	public String username;
	public String password;
	public String role;
	public User(){
		
	}

	public User(String first_name, String last_name, String organization, String added, String username,String password){
	this.first_name = first_name;
	this.last_name = last_name;
	this.organization = organization;
	this.added = added;
	this.username = username;
	this.password = password;
	
}
	public User(String username,String password){
		this.username = username;
		this.password = password;

	}

} 
/*
public class User {

    public String name;
    public String description;

    public User() {
    }

    public User(String name, String description) {
        this.name = name;
        this.description = description;
    }
}*/
