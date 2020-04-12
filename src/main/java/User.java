public abstract class User {
    String username;
    String password;
    String name;
    String lastName;
    String email;
    String number;
    String type;

    public boolean doesPasswordMatch(String password){
        if(this.password.equals(password)){
            return true;
        }
        return false;
    }

}
