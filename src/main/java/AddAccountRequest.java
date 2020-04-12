
public class AddAccountRequest extends  Request{
    private Seller seller;
    public AddAccountRequest(String requestId , Seller seller) {
        super(requestId);
        this.seller=seller;
        Request.addRequest(this);
        String news="the new seller with user Name"+seller.getUsername()+"wants to create account in your System";
        this.setMassage(news);
    }
    
    public Seller getSeller() {
        return seller;
    }

}