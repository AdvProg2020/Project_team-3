import View.Menus.View;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


public class MainTest {

   @Test
   public void main() {
      View.setProgramRunning(false);
      Main.main(null);
   }
}
