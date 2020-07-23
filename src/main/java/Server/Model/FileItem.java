package Server.Model;


public class FileItem extends Item {

   public FileItem(String name, String description,  double price, String sellerName,String image,String path) {
      super(name,path, description, "file", price, sellerName, "File", null, 0,image,"");
   }


}
