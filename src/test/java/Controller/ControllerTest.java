package Controller;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ControllerTest {

    @Test
    public void getInstance() {
        Database.getInstance().initiate();
       Controller controller=Controller.getInstance();
       assertNotNull(controller);
    }
    @Test
    public void getCurrentShoppingCart() {
        Assert.assertNotNull(Controller.getInstance().getCurrentShoppingCart());
    }

    @Test
    public void getAlphaNumericString() {
        String ans=Controller.getInstance().getAlphaNumericString(4,"Requests");
        assertEquals(ans.length(),4);
    }
}