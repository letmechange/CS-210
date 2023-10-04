package model;

public class Assistant extends LogIn {
    private final int accessCode;
    private Flower flower;

    public Assistant(String name, int accessCode) {
        super(name);
        this.accessCode = accessCode;
        flower = new Flower(0, "", "", 0);
    }

    // getter & setter
    public int getAccessCode() {
        return accessCode;
    }

    public Flower getFlower() {
        return flower;
    }

    public void setFlower(double price, String fm, String name, int stock) {
        flower = new Flower(price, fm, name, stock);
    }
}
