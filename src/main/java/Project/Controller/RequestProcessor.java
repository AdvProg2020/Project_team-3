package Project.Controller;

import com.google.gson.JsonObject;

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

   public String process(JsonObject command){
   if( command.get("type").equals("1")){
      return loginMenuProcessor(command);
   }else if( command.get("type").equals("2")){
      return buyerMenuProcessor(command);
   }else if( command.get("type").equals("3")){
      return sellerMenuProcessor(command);
   }else if( command.get("type").equals("4")){
      return adminMenuProcessor(command);
   }else if( command.get("type").equals("G")){ //general commands that dont need token like show shop

   }

   return "Error: invalid command";
   }

   public String loginMenuProcessor(JsonObject command){
      return "Error: invalid command";
   }

   public String adminMenuProcessor(JsonObject command){
      return "Error: invalid command";
   }

   public String buyerMenuProcessor(JsonObject command){
      return "Error: invalid command";
   }

   public String sellerMenuProcessor(JsonObject command){
      return "Error: invalid command";
   }

   public String generalProcessor(JsonObject command){
      return "Error: invalid command";
   }

   public  Matcher getMatcher(String regex, String input) {
      Pattern pattern = Pattern.compile(regex);
      return pattern.matcher(input);
   }

}
