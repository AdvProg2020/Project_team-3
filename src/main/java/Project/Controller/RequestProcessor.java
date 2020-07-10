package Project.Controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestProcessor {
   private static RequestProcessor requestProcessor;
   private Matcher matcher;
   private RequestProcessor(){

   }

   public static RequestProcessor getInstance(){
       if(requestProcessor==null) requestProcessor=new RequestProcessor();
       return requestProcessor;
   }

   public String process(String command){
   if(command.startsWith("type1")){
      return loginMenuProcessor(command);
   }else if(command.contains("type2")){
      return buyerMenuProcessor(command);
   }else if(command.contains("type3")){
      return sellerMenuProcessor(command);
   }else if(command.contains("type4")){
      return adminMenuProcessor(command);
   }else if(command.contains("typeG")){ //general commands that dont need token like show shop

   }
   return "Error: invalid command";
   }

   public String loginMenuProcessor(String command){
      matcher=getMatcher("type1 login (\\S+) (\\S+)",command);
      if(matcher.matches()){
         return UserController.getInstance().login(matcher.group(1),matcher.group(2));
      }
      if(command.startsWith("type1 create account seller")){

      }else if(command.startsWith("type1 create account buyer")){

      }else if(command.startsWith("type1 create account admin")){

      }
      return "Error: invalid command";
   }

   public String adminMenuProcessor(String command){
      return "Error: invalid command";
   }

   public String buyerMenuProcessor(String command){
      return "Error: invalid command";
   }

   public String sellerMenuProcessor(String command){
      return "Error: invalid command";
   }

   public String generalProcessor(String command){
      return "Error: invalid command";
   }

   public  Matcher getMatcher(String regex, String input) {
      Pattern pattern = Pattern.compile(regex);
      return pattern.matcher(input);
   }

}
