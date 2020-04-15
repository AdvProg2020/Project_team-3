import java.util.ArrayList;

public class Buyer extends User{

    private double money;
    private ArrayList<BuyLog> buyLogs;
    private Cart cart;
    private ArrayList<String>allDiscounts;
    public Buyer(double money,String username, String password, String name, String lastName, String email, String number) {
        super(username,password,name,lastName,email,"Buyer",number);
        this.money=money;
        buyLogs=new ArrayList<>();
        allDiscounts=new ArrayList<>();
    }

    public ArrayList<BuyLog> getBuyLogs() {
        return buyLogs;
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

    public void addDiscount(String discountId){
        this.allDiscounts.add(discountId);
    }

    public void removeDiscount(String discountID){
        if(this.allDiscounts.contains(discountID)) this.allDiscounts.remove(discountID);
    }
    public boolean isBoughtInPast(){
        return false;
    }



}
