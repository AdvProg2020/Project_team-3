package Model.Requests;

import Model.Users.Seller;
import junit.framework.TestCase;

public class AccountRequestTest extends TestCase {

    public AccountRequest addRequest(){
        Seller seller=new Seller(500,"alireza"
        ,"sdf","alireza","eiji","sdf@gmail.com"
        ,"33824264","benz");
        AccountRequest accountRequest=new AccountRequest("sdf",seller);
        return accountRequest;
    }

    public void testGetUser() {
        System.out.println(addRequest().getUser().getUsername());
    }

    public void testTestToString() {
        System.out.println(addRequest());
    }
}