package Model.Users;

import junit.framework.TestCase;

public class AdminTest extends TestCase {

    public Admin addAdmin(){
        Admin admin=new Admin("asdfg","sdfgh"
        ,"sdfgh","rtfg","a@gmail.com","33824264");
        return admin;
    }


    public void testGetPersonalInfo() {
        System.out.println(addAdmin().getPersonalInfo());
    }

    public void testAddAdminAccount() {
    }

    public void testDeleteUser() {
    }
}