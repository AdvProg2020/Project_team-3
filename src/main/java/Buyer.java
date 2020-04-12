import java.util.ArrayList;

public class Buyer extends User{

    private double money;
    private ArrayList<BuyLog> buyLogs;
    private Cart cart;

    public Buyer(double money,String username, String password, String name, String lastName, String email, String number) {
        this.money=money;
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.number = number;
        this.type = "Buyer";
        buyLogs=new ArrayList<>();
    }

    public void assignCart(Cart cart){
        this.cart=cart;
    }

    public boolean doesHaveEnoughMoneyToBuyCart(){
        if(this.money>=money){
            return true;
        }
           return false;
    }

    public void buy(){

    }


}
