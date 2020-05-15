package Model.Requests;

import Model.Item;
import junit.framework.TestCase;

import java.util.HashMap;

public class ItemRequestTest extends TestCase {
    public ItemRequest addRequest(){
      HashMap <String,String> attributes=new HashMap<>();
     Item item=new Item("alireza","Benz","sdf","",500,
             "sdfsdf","asedf",attributes,600);
     ItemRequest itemRequest=new ItemRequest("sdf",item);
        return itemRequest;
    }

    public void testGetNewItem() {
        System.out.println(addRequest().getNewItem().getId());
    }

    public void testTestToString() {
        System.out.println(addRequest().toString());
    }
}