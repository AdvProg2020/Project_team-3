import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Menu {
    public abstract void show();
    public abstract void execute(String command);
    public abstract void help();
    public int optionCount;
    //public Menu previousMenu;
    public int getOptionCount() {
        return optionCount;
    }


    public void login(){

    }


    public void register(){

    }

    public void logout(){

    }

    public void exit(){
      //must call Controller save function
    }

    public void back(){

    }



    public Matcher getMatcher(String input, String regex){
        Pattern pattern=Pattern.compile(regex);
        return pattern.matcher(input);
    }

}

