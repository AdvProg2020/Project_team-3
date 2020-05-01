package Model.Requests;

import Model.Users.Seller;

public class AccountRequest extends Request {
    private Seller seller;
    public AccountRequest(String requestId , Seller user) {
        super(requestId);
        this.seller=user;
        String news="the new seller with user Name"+user.getUsername()+"wants to create account in your System";
        this.setMessage(news);
        setType("AccountRequest");
    }

    public Seller getUser() {
        return this.seller;
    }

}
