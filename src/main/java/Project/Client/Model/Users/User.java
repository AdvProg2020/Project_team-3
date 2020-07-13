package Project.Client.Model.Users;



import java.util.HashMap;

public abstract class User {

    public String username;
    public String password;
    public String name;
    public String lastName;
    public String email;
    public String number;
    public String type;
    public HashMap<String,String> allRequests=new HashMap<>();

    public User(String username, String password, String name, String lastName, String email, String number, String type) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.number = number;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNumber() {
        return number;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public abstract String getPersonalInfo();

    public String getAllRequests() {
        return allRequests.toString().replace("{","").replace("}","").replace(",","\n");
    }

    public HashMap<String,String> getReqMap() {return allRequests;}

    public void setAllRequests(HashMap<String, String> allRequests) {
        this.allRequests = allRequests;
    }
}