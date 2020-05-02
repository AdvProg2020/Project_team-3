package View.Menus;

public class ViewPersonalInfo extends UserMenu {
    private static ViewPersonalInfo viewPersonalInfo ;
    private int optionCount = 4;
    private ViewPersonalInfo(){ }

    public static ViewPersonalInfo getInstance(){
        if(viewPersonalInfo==null)
            viewPersonalInfo = new ViewPersonalInfo();
        return viewPersonalInfo;
    }

    @Override
    public void run(){

    }

    @Override
    public void execute(String command) {

    }

    @Override
    public void help() {
        System.out.println("Press E to edit personal info or X to go back. ");
    }

  /*  @Override
    public void show() {
        help();
        String command = View.read.nextLine();
        execute(command);
    }

    @Override
    public void execute(String command) {
        if(command.equals("E")){

        }else if(command.equals("X")){
            if(UserController.getInstance().getCurrentOnlineUser() == null){
                LoginRegisterMenu.getInstance().show();
            }
            if(UserController.getInstance().getCurrentOnlineUser() instanceof Admin){
                AdminMenu.getInstance().show();
            }else if(UserController.getInstance().getCurrentOnlineUser() instanceof Seller){
                SellerMenu.getInstance().show();
            }else{
                BuyerMenu.getInstance().show();
            }
        }else{
            System.out.println(View.ANSI_RED+"Invalid command."+View.ANSI_RESET);
            show();
        }
    } */

}
