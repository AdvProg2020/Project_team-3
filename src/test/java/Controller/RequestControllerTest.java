package Controller;

import Model.Requests.Request;
import org.junit.Assert;
import org.junit.Test;

public class RequestControllerTest {

    @Test
    public void getInstance() {
    }

    @Test
    public void isThereRequestWithId() {
        Assert.assertTrue(RequestController.getInstance().isThereRequestWithId("0qCgu"));
        Assert.assertFalse(RequestController.getInstance().isThereRequestWithId("sdf"));
        System.out.println("sdf");
    }

    @Test
    public void getRequestById() {
       Request request=RequestController.getInstance().getRequestById("0qCgu");
       Request request1=RequestController.getInstance().getRequestById("g9gYa");
       Assert.assertNotNull(request);
       Assert.assertNotNull(request1);
    }

    @Test
    public void giveDiscountCodeToUser() {
    }

    @Test
    public void addUserRequest() {
    }

    @Test
    public void addSaleRequest() {
    }

    @Test
    public void addItemRequest() {
    }

    @Test
    public void addCommentRequest() {
    }

    @Test
    public void editSaleRequest() {
    }

    @Test
    public void editItemRequest() {
        ItemAndCategoryController.getInstance().editItem("price","800","MH2LP");
    }

    @Test
    public void acceptRequest() {
     RequestController.getInstance().acceptRequest("OEiPS");
    }

    @Test
    public void saleEditing() {
    }

    @Test
    public void itemEditing() {
    }

    @Test
    public void declineRequest() {
        RequestController.getInstance().declineRequest("ZYhJN");
    }

    @Test
    public void getAllRequestFromDataBase() {
    }

    @Test
    public void getRequestDetail() {
    }
}