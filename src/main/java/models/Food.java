package models;

public class Food {
    private String foodBankLocation;
    private int id;

    public Food(String donationType, String foodBankLocation) {
        this.foodBankLocation = foodBankLocation;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodBankLocation() {
        return foodBankLocation;
    }

    public void setFoodBankLocation(String foodBankLocation) {
        this.foodBankLocation = foodBankLocation;
    }

    public int getId() {
        return id;
    }
}
