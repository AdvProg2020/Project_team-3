
public class AccountRequest extends  Request{
    private Seller seller;
    public AccountRequest(String requestId , Seller seller) {
        super(requestId);
        this.seller=seller;
        String news="the new seller with user Name"+seller.getUsername()+"wants to create account in your System";
        this.setMessage(news);
        Request.addRequest(this);
    }

    public Seller getSeller() {
        return seller;
    }

}
