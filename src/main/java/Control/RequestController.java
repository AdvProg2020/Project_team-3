package Control;

import Model.Comment;
import Model.Item;
import Model.Requests.*;
import Model.Sale;
import Model.Users.Buyer;
import Model.Users.Seller;
import Model.Users.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
        String path="Resource"+File.separator+"Requests";
        String name=id+".json";
        File file=new File(path+File.separator+name);
        if(!file.exists()){
            return false;
        }
        return true;
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
        try {
            Database.getInstance().saveUser(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addUserRequest(String requestID, Seller newUser)  {
        AccountRequest newRequest = new AccountRequest(requestID, newUser);
        try {
            Database.getInstance().saveRequest(newRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addSaleRequest(String requestId, Sale newSale)  {
        newSale.addStatus();
        SaleRequest newRequest = new SaleRequest(requestId, newSale);
        try {
            Database.getInstance().saveRequest(newRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addItemRequest(String requestId, Item newItem)  {
        ItemRequest newRequest = new ItemRequest(requestId, newItem);
        try {
            Database.getInstance().saveRequest(newRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addCommentRequest(String requestId , Comment newComment) {
        newComment.inProcess();
        CommentRequest commentRequest=new CommentRequest(requestId,newComment);
        try {
            Database.getInstance().saveRequest(commentRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editSaleRequest(String requestId, String saleID, String changedFiled, String newFieldValue)  {
        Sale newSale=SaleAndDiscountCodeController.getInstance().getSaleById(saleID);
        newSale.editStatus();
        SaleEdit newRequest = new SaleEdit(requestId, saleID, changedFiled, newFieldValue);
        try {
            Database.getInstance().saveRequest(newRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editItemRequest(String requestId, String saleID, String changedFiled, String newFieldValue)  {
        ItemEdit newRequest = new ItemEdit(requestId, saleID, changedFiled, newFieldValue);
        try {
            Database.getInstance().saveRequest(newRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    ///after accept or decline
    public String acceptRequest(String requestID)  {
        Request accepted=getRequestById(requestID);
        if(accepted==null) {return "Error: request doesnt exist";}
            if(accepted instanceof AccountRequest){
                User user=UserController.getInstance().getUserByUsername(((AccountRequest) accepted).getUser().getUsername());
                if(user instanceof  Seller) ((Seller) user).Validate();
                try {
                    Database.getInstance().saveUser(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if(accepted instanceof SaleRequest){
                try {
                    ((SaleRequest) accepted).getNewSale().acceptStatus();
                    Database.getInstance().saveSale(((SaleRequest) accepted).getNewSale());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if(accepted instanceof ItemRequest){
                try {
                    Database.getInstance().saveItem(((ItemRequest) accepted).getNewItem());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if(accepted instanceof ItemEdit){
                requestController.ItemEditing((ItemEdit)accepted);
            }else if(accepted instanceof SaleEdit){
                requestController.SaleEditing((SaleEdit)accepted);
            }else if(accepted instanceof CommentRequest){
                Comment comment=((CommentRequest) accepted).getNewComment();
                comment.accept();
                Item item=ItemAndCategoryController.getInstance().getItemById(comment.getItemId());
                item.addComment(comment);
                try {
                    Database.getInstance().saveItem(item);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Database.getInstance().deleteRequest(accepted);
            return "Successful: request accepted";
    }
    ///after accepting requests
    public void SaleEditing(SaleEdit saleEdit){
        Sale sale=SaleAndDiscountCodeController.getInstance().getSaleById(saleEdit.getSaleID());
        if(sale==null)return;
        String changedField=saleEdit.getChangedFieled();
        String newFieldValue=saleEdit.getNewFieldValue();
        if(changedField.equals("start Time")){
            sale.setStartTime(Integer.parseInt(newFieldValue));
        }else if(changedField.equals("end Time")){
            sale.setEndTime(Integer.parseInt(newFieldValue));
        }else if(changedField.equals("off Percentage")){
            sale.setOffPercentage(Integer.parseInt(newFieldValue));
        }
        try {
            Database.getInstance().saveSale(sale);
        } catch (IOException e) {
            e.printStackTrace();
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
        }else if(changedField.equals("inStock"))
            item.setInStock(Integer.parseInt(newFieldValue));
        try {
            Database.getInstance().saveItem(item);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String declineRequest(String requestID){
        Request declined=getRequestById(requestID);
        if(declined instanceof AccountRequest){
            Database.getInstance().deleteUser(((AccountRequest) declined).getUser());
        }
        if(declined==null){ return "Error: request doesnt exist"; }
        Database.getInstance().deleteRequest(declined);
        return "Successful: request declined";
    }

    public ArrayList<Request> getAllRequestFromDataBase(){
        String path="Resource"+File.separator+"Requests";
        File file=new File(path);
        File [] allFiles=file.listFiles();
        String fileContent = null;
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        ArrayList<Request>allRequests=new ArrayList<>();
            for(File file1:allFiles){
                try {
                    fileContent=new String(Files.readAllBytes(file1.toPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(fileContent.contains("\"type\": \"AccountRequest\"")){
                    allRequests.add(gson.fromJson(fileContent, AccountRequest.class));}
                if(fileContent.contains("\"type\": \"CommentRequest\"")){
                    allRequests.add(gson.fromJson(fileContent, CommentRequest.class));}
                if(fileContent.contains("\"type\": \"ItemEdit\"")){
                    allRequests.add(gson.fromJson(fileContent, ItemEdit.class));}
                if(fileContent.contains("\"type\": \"SaleEdit\"")){
                    allRequests.add(gson.fromJson(fileContent, SaleEdit.class));}
                if(fileContent.contains("\"type\": \"SaleRequest\"")){
                    allRequests.add(gson.fromJson(fileContent, SaleRequest.class));}
            }
            return allRequests;
    }
    public String getRequestDetail(String id){
        Request request=getRequestById(id);
        if(request==null){
            return "Error: request doesnt exist";
        }
        return request.getMessage();
    }
}
