package Controller;

import org.junit.Test;

import static org.junit.Assert.*;

public class DatabaseTest {

    @Test
    public void initiate() {
        Database.getInstance().initiate();
    }

    @Test
    public void printFolderContent() {
        Database.getInstance().printFolderContent("Categories");
    }
}