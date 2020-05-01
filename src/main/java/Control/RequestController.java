package Control;

import Model.Comment;
import Model.Item;
import Model.Requests.*;
import Model.Sale;
import Model.Users.Buyer;
import Model.Users.Seller;
import Model.Users.User;

import java.io.IOException;
import java.util.ArrayList;

public class RequestController {
    Controller controller = Controller.getInstance();

    private static RequestController requestController;
    private RequestController(){}

    public static RequestController getInstance(){
        if(requestController==null)
            requestController=new RequestController();
        return requestController;
    }


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
        User user=UserController.getInstance().getUserByUsername(username);
        if(SaleAndDiscountCodeController.getInstance().isThereDiscountCodeWithId(discountID)==false) return;
        if(!(user instanceof Buyer)) return;
        ((Buyer) user).addDiscount(discountID);
    }

    public void addUserRequest(String requestID, Seller newUser) {
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

    public void addCommentRequest(String requestId , Comment newComment){
        CommentRequest commentRequest=new CommentRequest(requestId,newComment);
        controller.allRequests.add(commentRequest);
    }

    public void editSaleRequest(String requestId, String saleID, String changedFiled, String newFieldValue) {
        SaleEdit newRequest = new SaleEdit(requestId, saleID, changedFiled, newFieldValue);
        controller.allRequests.add(newRequest);
    }

    public void editItemRequest(String requestId, String saleID, String changedFiled, String newFieldValue) {
        ItemEdit newRequest = new ItemEdit(requestId, saleID, changedFiled, newFieldValue);
        controller.allRequests.add(newRequest);
    }
    ///after accept or decline
    public void acceptRequest() throws IOException {
        requestController.declineRequest();
        for(Request request:controller.allRequests){
            if(request instanceof AccountRequest){
                ((AccountRequest) request).getUser().Validate();
            }else if(request instanceof SaleRequest){
                Gsonsaveload.saveSale(((SaleRequest) request).getNewSale());
            }else if(request instanceof ItemRequest){
               Gsonsaveload.saveItem(((ItemRequest) request).getNewItem());
            }else if(request instanceof ItemEdit){
                requestController.ItemEditing((ItemEdit)request);
            }else if(request instanceof SaleEdit){
                requestController.SaleEditing((SaleEdit)request);
            }
        }
        controller.allRequests.clear();
    }
    ///after accepting requests
    public void SaleEditing(SaleEdit saleEdit){
        Sale sale=SaleAndDiscountCodeController.getInstance().getSaleById(saleEdit.getSaleID());
        if(sale==null)return;
        String changedField=saleEdit.getChangedFiled();
        String newFieldValue=saleEdit.getNewFieldValue();

        if(changedField.equals("start Time")){
            sale.setStartTime(Integer.parseInt(newFieldValue));
        }else if(changedField.equals("end Time")){
            sale.setEndTime(Integer.parseInt(newFieldValue));
        }else if(changedField.equals("off Percentage")){
            sale.setOffPercentage(Integer.parseInt(newFieldValue));
        }
    }
    ///after accepting requests
    public void ItemEditing(ItemEdit itemEdit){
        Item item=ItemAndCategoryController.getInstance().getItemById(itemEdit.getItemID());
        if(item==null)return;
        String changedField=itemEdit.getChangedField();
        String newFieldValue=itemEdit.getNewFieldValue();
        if(changedField.equals("price")){
            item.setPrice(Double.parseDouble(newFieldValue));
        }else if(changedField.equals("name")){
            item.setName(newFieldValue);
        }else if(changedField.equals("brand")){
            item.setBrand(newFieldValue);
        }else if(changedField.equals("description")){
            item.setDescription(newFieldValue);
        }else if(changedField.equals("state")){
            item.setState(newFieldValue);
        }else if(changedField.equals("category Name")){
            item.setCategoryName(newFieldValue);
        }else if(changedField.equals("in stock")){
            item.setInStock(Double.parseDouble(newFieldValue));
        }
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
