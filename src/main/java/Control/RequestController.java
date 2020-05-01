package Control;

import Model.Comment;
import Model.Item;
import Model.Requests.*;
import Model.Sale;
import Model.Users.Buyer;
import Model.Users.Seller;
import Model.Users.User;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
        String path="Resource"+ File.separator+"Requests";
        String name=id+".json";
        File file=new File(path+File.separator+name);
        if(!file.exists()){
            return null;
        }
        Gson gson=new Gson();
        try {
            String content=new String(Files.readAllBytes(file.toPath()));
            if(content.contains("\"type\": \"AccountRequest\"")){
                return gson.fromJson(content, AccountRequest.class);}
            if(content.contains("\"type\": \"CommentRequest\"")){
                return gson.fromJson(content, CommentRequest.class);}
            if(content.contains("\"type\": \"ItemEdit\"")){
                return gson.fromJson(content, ItemEdit.class);}
            if(content.contains("\"type\": \"SaleEdit\"")){
                return gson.fromJson(content, SaleEdit.class);}
            if(content.contains("\"type\": \"SaleRequest\"")){
                return gson.fromJson(content, SaleRequest.class);}
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void giveDiscountCodeToUser(String discountID,String username){
        User user=UserController.getInstance().getUserByUsername(username);
        if(SaleAndDiscountCodeController.getInstance().isThereDiscountCodeWithId(discountID)==false) return;
        if(!(user instanceof Buyer)) return;
        ((Buyer) user).addDiscount(discountID);
    }

    public void addUserRequest(String requestID, Seller newUser) throws IOException {
        AccountRequest newRequest = new AccountRequest(requestID, newUser);
        Gsonsaveload.saveRequest(newRequest);
    }

    public void addSaleRequest(String requestId, Sale newSale) throws IOException {
        SaleRequest newRequest = new SaleRequest(requestId, newSale);
        Gsonsaveload.saveRequest(newRequest);
    }

    public void addItemRequest(String requestId, Item newItem) throws IOException {
        ItemRequest newRequest = new ItemRequest(requestId, newItem);
        Gsonsaveload.saveRequest(newRequest);
    }

    public void addCommentRequest(String requestId , Comment newComment) throws IOException {
        CommentRequest commentRequest=new CommentRequest(requestId,newComment);
        Gsonsaveload.saveRequest(commentRequest);
    }

    public void editSaleRequest(String requestId, String saleID, String changedFiled, String newFieldValue) throws IOException {
        SaleEdit newRequest = new SaleEdit(requestId, saleID, changedFiled, newFieldValue);
        Gsonsaveload.saveRequest(newRequest);
    }

    public void editItemRequest(String requestId, String saleID, String changedFiled, String newFieldValue) throws IOException {
        ItemEdit newRequest = new ItemEdit(requestId, saleID, changedFiled, newFieldValue);
        Gsonsaveload.saveRequest(newRequest);
    }
    ///after accept or decline
    public void acceptRequest(String requestID) throws IOException {
        Request accepted=getRequestById(requestID);
        if(accepted==null) return;
            if(accepted instanceof AccountRequest){
                ((AccountRequest) accepted).getUser().Validate();
            }else if(accepted instanceof SaleRequest){
                Gsonsaveload.saveSale(((SaleRequest) accepted).getNewSale());
            }else if(accepted instanceof ItemRequest){
               Gsonsaveload.saveItem(((ItemRequest) accepted).getNewItem());
            }else if(accepted instanceof ItemEdit){
                requestController.ItemEditing((ItemEdit)accepted);
            }else if(accepted instanceof SaleEdit){
                requestController.SaleEditing((SaleEdit)accepted);
            }
            Gsonsaveload.deleteRequest(accepted);
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

    public void declineRequest(String requestID){
        Request declined=getRequestById(requestID);
        if(declined==null) return;
        Gsonsaveload.deleteRequest(declined);
    }

}
