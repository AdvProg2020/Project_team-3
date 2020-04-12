public class AccountEdit extends  Request{
    private User user;

    ///over loaded constructors must added here
    public AccountEdit(String requestId, User user) {
        super(requestId);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
    
}
