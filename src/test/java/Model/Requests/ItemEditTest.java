package Model.Requests;

import junit.framework.TestCase;

public class ItemEditTest extends TestCase {

    public ItemEdit addRequest(){
        ItemEdit itemEdit=new ItemEdit("sdf","sdfd","Name"
        ,"sdfsdf");
        return itemEdit;
    }

    public void testGetItemID() {
        System.out.println(addRequest().getItemID());
    }

    public void testGetChangedField() {
        System.out.println(addRequest().getChangedField());
    }

    public void testGetNewFieldValue() {
        System.out.println(addRequest().getNewFieldValue());
    }

    public void testTestToString() {
        System.out.println(addRequest().toString());
    }
}