package Controller;

import org.junit.Test;

public class RequestControllerTest {

    @Test
    public void getInstance() {
    }

    @Test
    public void isThereRequestWithId() {
    }

    @Test
    public void getRequestById() {
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
        RequestController.getInstance().acceptRequest("Of5IO");
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