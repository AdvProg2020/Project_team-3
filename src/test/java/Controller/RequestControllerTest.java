package Controller;

import Model.Requests.Request;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;

public class RequestControllerTest {

    @Test
    public void getInstance() {
        Database.getInstance().initiate();
        RequestController requestController=RequestController.getInstance();
        assertNotNull(requestController);
    }

    public void deleteJunk(){
        UserController.getInstance().logout();
        UserController.getInstance().login("admin","12345");
        UserController.getInstance().deleteUser("Arman");
        ItemAndCategoryController.getInstance().removeCategory("lavazem manzel");
        UserController.getInstance().logout();
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
            Assert.assertTrue(RequestController.getInstance().isThereRequestWithId(request.getRequestId()));
        }
        declineRequest();
    }

    @Test
    public void acceptRequest() {
        UserController.getInstance().registerSeller(500,"Ali","alireza79",
                "reza","pishro","alireza@gmail.com","33824264","benz");
        ArrayList<Request>allRequests=RequestController.getInstance().getAllRequestFromDataBase();
        Assert.assertTrue(!allRequests.isEmpty());
        for(Request request:allRequests){
            RequestController.getInstance().acceptRequest(request.getRequestId());
        }
        Database.getInstance().deleteUser(UserController.getInstance().getUserByUsername("Ali"));
    }

    @Test
    public void declineRequest() {
        UserController.getInstance().registerSeller(500,"Ali","alireza79",
                "reza","pishro","alireza@gmail.com","33824264","benz");
        ArrayList<Request>allRequests=RequestController.getInstance().getAllRequestFromDataBase();
        Assert.assertTrue(!allRequests.isEmpty());
        for(Request request:allRequests){
            RequestController.getInstance().declineRequest(request.getRequestId());
        }
    }

    @Test
    public void getAllRequestFromDataBase() {
        ArrayList<Request> allRequests=RequestController.getInstance().getAllRequestFromDataBase();
        for(Request request:allRequests){
            Assert.assertTrue(RequestController.getInstance().isThereRequestWithId(request.getRequestId()));
            System.out.println(request.getType());
        }
    }

    @Test
    public void getRequestDetail() {
        UserController.getInstance().registerSeller(500,"AliTestRequest","alireza79",
                "reza","pishro","alireza@gmail.com","33824264","benz");
        ArrayList<Request>allRequests=RequestController.getInstance().getAllRequestFromDataBase();
        Assert.assertTrue(!allRequests.isEmpty());
        Request request=allRequests.get(0);
        Assert.assertNotNull(request);
        System.out.println(RequestController.getInstance().getRequestDetail(request.getRequestId()));
        declineRequest();
    }
}