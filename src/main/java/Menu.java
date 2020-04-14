public abstract class Menu {
    public abstract void show();
    public abstract void execute(String command);
    public abstract void login();
    public abstract void register();
    public abstract void logout();
    public abstract void help();
    public int optionCount;
    public int getOptionCount() {
        return optionCount;
    }
}
