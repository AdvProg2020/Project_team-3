package Model.Requests;

import Model.Users.Seller;

public class AccountRequest extends Request {
    private Seller seller;

    public AccountRequest(String requestId, Seller user) {
        super(requestId);
        this.seller = user;
        String news = "Request to add new seller with username " + user.getUsername() + " and starting money of "+((Seller)user).getMoney();
        this.setMessage(news);
        setType("AccountRequest");
    }

    public Seller getUser() {
        return this.seller;
    }

    @Override
    public String toString() {
        return "id: " + getRequestId() + "\n" + "type: " + getType() + "\n" + "UserName:" + seller.getUsername() + "\n";
    }

}
