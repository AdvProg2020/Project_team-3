
public class AccountRequest extends  Request{
    private User user;
    public AccountRequest(String requestId , User user) {
        super(requestId);
        this.user=user;
        String news="the new seller with user Name"+user.getUsername()+"wants to create account in your System";
        this.setMessage(news);
    }

    public User getSeller() {
        return this.user;
    }

}
