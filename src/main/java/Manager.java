public class Manager {
    private static Manager manager;
    private Manager(){

    }
    public static Manager getInstance(){
        if(manager==null)
            manager = new Manager();
        return manager;
    }
}
