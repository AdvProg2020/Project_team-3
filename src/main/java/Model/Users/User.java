package Model.Users;

public abstract class User {

    private String username;
    private String password;
    private String name;
    private String lastName;
    private String email;
    private String number;
    private String type;


    public boolean doesPasswordMatch(String password){
        return this.password.equals(password);
    }
    public User(String username,String password,String name,String lastName,String email,String type,String number){
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.number = number;
        this.type = type;
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

}
