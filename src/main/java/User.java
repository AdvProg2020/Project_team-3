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

}
