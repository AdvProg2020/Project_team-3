package Controller;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ControllerTest {
    @Test
    public void getCurrentShoppingCart() {
        Assert.assertNotNull(Controller.getInstance().getCurrentShoppingCart());
    }

    @Test
    public void getAlphaNumericString() {
        System.out.println(Controller.getInstance().getAlphaNumericString(4,"Requests"));
    }

    @Test
    public void updateDateAndTime() {
    }
}