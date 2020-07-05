package Project.Controller;

import Project.Model.*;
import Project.Model.Requests.*;
import Project.Model.Users.Seller;
import Project.Model.Users.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RequestController {
    Controller controller = Controller.getInstance();

    private static RequestController requestController;

    private RequestController() {
    }

    public static RequestController getInstance() {
        if (requestController == null)
            requestController = new RequestController();
        return requestController;
    }


    public boolean isThereRequestWithId(String id) {
        String path = "Resource" + File.separator + "Requests";
        String name = id + ".json";
        File file = new File(path + File.separator + name);
        if (!file.exists()) {
            return false;
        }
        return true;
    }

    public Request getRequestById(String id) {
        String path = "Resource" + File.separator + "Requests";
        String name = id + ".json";
        File file = new File(path + File.separator + name);
        if (!file.exists()) {
            return null;
        }
        Gson gson = new Gson();
        try {
            String content = new String(Files.readAllBytes(file.toPath()));
            if (content.contains("\"type\": \"AccountRequest\"")) {
                return gson.fromJson(content, AccountRequest.class);
            }
            if (content.contains("\"type\": \"CommentRequest\"")) {
                return gson.fromJson(content, CommentRequest.class);
            }
            if (content.contains("\"type\": \"ItemEdit\"")) {
                return gson.fromJson(content, ItemEdit.class);
            }
            if (content.contains("\"type\": \"ItemRequest\"")) {
                return gson.fromJson(content, ItemRequest.class);
            }
            if (content.contains("\"type\": \"SaleEdit\"")) {
                return gson.fromJson(content, SaleEdit.class);
            }
            if (content.contains("\"type\": \"SaleRequest\"")) {
                return gson.fromJson(content, SaleRequest.class);
            }
            if(content.contains("\"type\": \"ItemDelete\"")){
                return gson.fromJson(content,ItemDelete.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    public void addUserRequest(String requestID, Seller newUser) {
        AccountRequest newRequest = new AccountRequest(requestID, newUser);
        Database.getInstance().saveRequest(newRequest);
    }

    public void addSaleRequest(String requestId, Sale newSale) {
        newSale.addStatus();
        SaleRequest newRequest = new SaleRequest(requestId, newSale);
        Database.getInstance().saveRequest(newRequest);
    }

    public void addItemRequest(String requestId, Item newItem) {
        ItemRequest newRequest = new ItemRequest(requestId, newItem);
        Database.getInstance().saveRequest(newRequest);
    }

    public void addCommentRequest(String requestId, Comment newComment) {
        newComment.inProcess();
        CommentRequest commentRequest = new CommentRequest(requestId, newComment);
        Database.getInstance().saveRequest(commentRequest);
    }

    public void editSaleRequest(String requestId, String saleID, String changedFiled, String newFieldValue) {
        Sale newSale = SaleAndDiscountCodeController.getInstance().getSaleById(saleID);
        newSale.editStatus();
        SaleEdit newRequest = new SaleEdit(requestId, saleID, changedFiled, newFieldValue);
        Database.getInstance().saveRequest(newRequest);
    }

    public void editItemRequest(String requestId, String itemID, String changedFiled, String newFieldValue) {
        ItemEdit newRequest = new ItemEdit(requestId, itemID, changedFiled, newFieldValue);
        Database.getInstance().saveRequest(newRequest);
    }

    public void deleteItemRequest(String requestId,String itemId){
        ItemDelete newRequest=new ItemDelete(requestId,itemId);
        Database.getInstance().saveRequest(newRequest);
    }

    ///after accept or decline
    public String acceptRequest(String requestID) {
        Request accepted = getRequestById(requestID);
        if (accepted == null) {
            return "Error: Request doesn't exist";
        }
        accepted.accept();
        if (accepted instanceof AccountRequest) {
            User user = UserController.getInstance().getUserByUsername(((AccountRequest) accepted).getUser().getUsername());
            if (user instanceof Seller) ((Seller) user).validate();
            Database.getInstance().saveUser(user);
        } else if (accepted instanceof SaleRequest) {
                Sale sale=((SaleRequest) accepted).getNewSale();
                sale.acceptStatus();
            for (String id : sale.getAllItemId()) {
                Item item=ItemAndCategoryController.getInstance().getItemById(id);
                if(item==null) continue;
                item.setSale(sale.getId());
                Database.getInstance().saveItem(item);
            }
                Database.getInstance().saveSale(((SaleRequest) accepted).getNewSale());

        } else if (accepted instanceof ItemRequest) {
                Item item = ((ItemRequest) accepted).getNewItem();
                item.setAddedTime(LocalDateTime.now());
                Database.getInstance().saveItem(item);
                ItemAndCategoryController.getInstance().addItemToCategory(item.getId(), item.getCategoryName());
        } else if (accepted instanceof ItemEdit) {
            requestController.ItemEditing((ItemEdit) accepted);
        } else if (accepted instanceof SaleEdit) {
            requestController.SaleEditing((SaleEdit) accepted);
        } else if (accepted instanceof CommentRequest) {
            Comment comment = ((CommentRequest) accepted).getNewComment();
            comment.accept();
            Item item = ItemAndCategoryController.getInstance().getItemById(comment.getItemId());
            item.addComment(comment);
                Database.getInstance().saveItem(item);
        }  else if(accepted instanceof  ItemDelete){
            String id=((ItemDelete) accepted).getItemId();
            Item removed=ItemAndCategoryController.getInstance().getItemById(id);
            if(removed!=null) {
                UserController.getInstance().deleteItemFromSeller(id, removed.getSellerName());
                Database.getInstance().deleteItem(removed);
            }
        }
        Database.getInstance().deleteRequest(accepted);
        return "Successful: Request accepted";
    }

    ///after accepting requests
    public void SaleEditing(SaleEdit saleEdit) {
        Sale sale = SaleAndDiscountCodeController.getInstance().getSaleById(saleEdit.getSaleID());
        if (sale == null) return;
        String changedField = saleEdit.getChangedField();
        String newFieldValue = saleEdit.getNewFieldValue();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        if (changedField.equalsIgnoreCase("start Time")) {
            LocalDateTime date = LocalDateTime.parse(newFieldValue);
            sale.setStartTime(date);
        } else if (changedField.equalsIgnoreCase("end Time")) {
            LocalDateTime date = LocalDateTime.parse(newFieldValue);
            sale.setEndTime(date);
        } else if (changedField.equalsIgnoreCase("off Percentage")) {
            sale.setOffPercentage(Integer.parseInt(newFieldValue));
        }
            Database.getInstance().saveSale(sale);
    }

    ///after accepting requests
    public void ItemEditing(ItemEdit itemEdit) {
        Item item = ItemAndCategoryController.getInstance().getItemById(itemEdit.getItemID());
        if (item == null) return;
        String changedField = itemEdit.getChangedField();
        String newFieldValue = itemEdit.getNewFieldValue();
        if (changedField.equalsIgnoreCase("price")) {
            item.setPrice(Double.parseDouble(newFieldValue));
        } else if (changedField.equalsIgnoreCase("name")) {
            item.setName(newFieldValue);
        } else if (changedField.equalsIgnoreCase("brand")) {
            item.setBrand(newFieldValue);
        } else if (changedField.equalsIgnoreCase("description")) {
            item.setDescription(newFieldValue);
        } else if (changedField.equalsIgnoreCase("inStock")) {
            item.setInStock(Integer.parseInt(newFieldValue));
        } else {
            item.setAttribute(changedField, newFieldValue);
        }
            Database.getInstance().saveItem(item);
    }

    public String declineRequest(String requestID) {
        Request declined = getRequestById(requestID);
        if (declined instanceof AccountRequest) {
            String username=((AccountRequest) declined).getUser().getUsername();
            String imagePath=UserController.getInstance().userImagePath(username);
            File file=new File(imagePath);
            if(!imagePath.equals("src/main/resources/Images/default.jpg")) file.delete();
            Database.getInstance().deleteUser(((AccountRequest) declined).getUser());
        }
        if (declined == null) {
            return "Error: Request doesn't exist";
        }
        Database.getInstance().deleteRequest(declined);
        declined.decline();
        return "Successful: Request declined";
    }

    public ArrayList<Request> getAllRequestFromDataBase() {
        String path = "Resource" + File.separator + "Requests";
        File file = new File(path);
        File[] allFiles = file.listFiles();
        String fileContent = null;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ArrayList<Request> allRequests = new ArrayList<>();
        for (File file1 : allFiles) {
            try {
                fileContent = new String(Files.readAllBytes(file1.toPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (fileContent.contains("\"type\": \"AccountRequest\"")) {
                allRequests.add(gson.fromJson(fileContent, AccountRequest.class));
            }
            if (fileContent.contains("\"type\": \"CommentRequest\"")) {
                allRequests.add(gson.fromJson(fileContent, CommentRequest.class));
            }
            if (fileContent.contains("\"type\": \"ItemEdit\"")) {
                allRequests.add(gson.fromJson(fileContent, ItemEdit.class));
            }
            if (fileContent.contains("\"type\": \"SaleEdit\"")) {
                allRequests.add(gson.fromJson(fileContent, SaleEdit.class));
            }
            if (fileContent.contains("\"type\": \"SaleRequest\"")) {
                allRequests.add(gson.fromJson(fileContent, SaleRequest.class));
            }
            if (fileContent.contains("\"type\": \"ItemRequest\"")) {
                allRequests.add(gson.fromJson(fileContent, SaleRequest.class));
            }
            if(fileContent.contains("\"type\": \"ItemDelete\"")){
                allRequests.add(gson.fromJson(fileContent,ItemDelete.class));
            }
        }
        return allRequests;
    }

    public String getRequestDetail(String id) {
        Request request = getRequestById(id);
        if (request == null) {
            return "Error: Request doesn't exist";
        }
        return request.getMessage();
    }
}
