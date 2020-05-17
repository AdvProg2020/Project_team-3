package Controller;

import Model.Requests.Request;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class RequestControllerTest {

    @Test
    public void getInstance() {
    }

    @Test
    public void isThereRequestWithId() {
        UserController.getInstance().registerSeller(500,"Ali","alireza79",
                "reza","pishro","alireza@gmail.com","33824264","benz");
        ArrayList<Request> allRequest=RequestController.getInstance().getAllRequestFromDataBase();
        if(allRequest.isEmpty()) return;
        for(Request request:allRequest){
            Assert.assertTrue(RequestController.getInstance().isThereRequestWithId(request.getRequestId()));
        }
        Assert.assertFalse(RequestController.getInstance().isThereRequestWithId("BehaeenFrj"));
        declineRequest();
    }

    @Test
    public void getRequestById() {
        UserController.getInstance().registerSeller(500,"Ali","alireza79",
                "reza","pishro","alireza@gmail.com","33824264","benz");
        ArrayList<Request>allRequests=RequestController.getInstance().getAllRequestFromDataBase();
        if(allRequests.isEmpty()) return;
        for(Request request:allRequests){
            System.out.println(RequestController.getInstance().getRequestById(request.getRequestId()));
        }
        declineRequest();
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

    }

    @Test
    public void acceptRequest() {
        ArrayList<Request>allRequests=RequestController.getInstance().getAllRequestFromDataBase();
        for(Request request:allRequests){
            RequestController.getInstance().acceptRequest(request.getRequestId());
        }
    }

    @Test
    public void saleEditing() {
    }

    @Test
    public void itemEditing() {
    }

    @Test
    public void declineRequest() {
        UserController.getInstance().registerSeller(500,"Ali","alireza79",
                "reza","pishro","alireza@gmail.com","33824264","benz");
        ArrayList<Request>allRequests=RequestController.getInstance().getAllRequestFromDataBase();
        for(Request request:allRequests){
            RequestController.getInstance().declineRequest(request.getRequestId());
        }
        for(Request request:allRequests){
            RequestController.getInstance().declineRequest(request.getRequestId());
        }
    }

    @Test
    public void getAllRequestFromDataBase() {
        ArrayList<Request> allRequests=RequestController.getInstance().getAllRequestFromDataBase();
        for(Request request:allRequests){
            System.out.println(request.getType());
        }
    }

    @Test
    public void getRequestDetail() {
        UserController.getInstance().registerSeller(500,"AliTestRequest","alireza79",
                "reza","pishro","alireza@gmail.com","33824264","benz");
        ArrayList<Request>allRequests=RequestController.getInstance().getAllRequestFromDataBase();
        Request request=allRequests.get(0);
        System.out.println(RequestController.getInstance().getRequestDetail(request.getRequestId()));
        declineRequest();
    }
}