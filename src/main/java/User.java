public abstract class User {

    private String username;
    private String password;
    private String name;
    private String lastName;
    private String email;
    private String number;
    private String type;
    public boolean doesPasswordMatch(String password){
        if(this.password.equals(password)){
            return true;
        }
        return false;
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
}
