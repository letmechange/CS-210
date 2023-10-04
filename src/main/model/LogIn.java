package model;


public abstract class LogIn {
    private String name;

    //EFFECTS: log in our store
    public LogIn(String name) {
        this.name = name;
    }

    // getter
    public String getName() {
        return name;
    }

}
