package Model.Requests;

import Model.Users.Seller;

public class AccountRequest extends Request {
    private Seller seller;

    public AccountRequest(String requestId, Seller user) {
        super(requestId);
        this.seller = user;
        String news = "request id: "+requestId+" Request to add new seller with username " + user.getUsername() + " and starting money of "+((Seller)user).getMoney();
        this.setMessage(news);
        setType("AccountRequest");
    }

    public Seller getUser() {
        return this.seller;
    }

    @Override
    public String toString() {
        return "id: " + getRequestId() + "   " + "type: " + getType();
    }

    @Override
    public String getAcceptedMessage() {
        return "id: "+getRequestId()+" state:accepted "+" info:your request to register your account has been confirmed by the admin";
    }

    @Override
    public String getDeclineMessage() {
        return null; //seller account wont exist to have any message
    }

    @Override
    public void accept() {
      seller.addRequest(getRequestId(),getAcceptedMessage());
    }

    @Override
    public void decline() {
    }

}
