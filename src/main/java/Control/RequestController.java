package Control;

import Model.Item;
import Model.Requests.*;
import Model.Sale;
import Model.Users.User;

import java.util.ArrayList;

public class RequestController {
    Controller controller = Controller.getInstance();

    public boolean isThereRequestWithId(String id){
        for(Request getRequest:controller.allRequests){
            if(getRequest.getRequestId().equals(id)) return true;
        }
        return false;
    }

    public Request getRequestById(String id){
        for(Request getRequest:controller.allRequests){
            if(getRequest.getRequestId().equals(id)){
                return getRequest;
            }
        }
        return null;
    }


    public void giveDiscountCodeToUser(String discountID,String username){
    }

    public void addUserRequest(String requestID, User newUser) {
        AccountRequest newRequest = new AccountRequest(requestID, newUser);
        controller.allRequests.add(newRequest);
    }

    public void addSaleRequest(String requestId, Sale newSale) {
        SaleRequest newRequest = new SaleRequest(requestId, newSale);
        controller.allRequests.add(newRequest);
    }

    public void addItemRequest(String requestId, Item newItem) {
        ItemRequest newRequest = new ItemRequest(requestId, newItem);
        controller.allRequests.add(newRequest);
    }

    public void editSaleRequest(String requestId, String saleID, String changedFiled, String newFieldValue) {
        SaleEdit newRequest = new SaleEdit(requestId, saleID, changedFiled, newFieldValue);
        controller.allRequests.add(newRequest);
    }

    public void editItemRequest(String requestId, String saleID, String changedFiled, String newFieldValue) {
        ItemEdit newRequest = new ItemEdit(requestId, saleID, changedFiled, newFieldValue);
        controller.allRequests.add(newRequest);
    }

    public void acceptRequest(){
        ///inja ba chand rikhti va moshakhas kardan request haye accept shode amaliat marbote ra anjam midim!
    }

    public void declineRequest(){

        ArrayList<Request> toBeRemoved = new ArrayList<>();
        for(Request request:controller.allRequests){
            if(!request.isIsAccepted()){
                toBeRemoved.add(request);
            }
        }
        controller.allRequests.removeAll(toBeRemoved);
    }

}
