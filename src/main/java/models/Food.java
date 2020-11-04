package models;

public class Food extends Donation{
    private String foodBankLocation;

    public Food(String donationType, String foodBankLocation) {
        super(donationType, donationType);
        this.foodBankLocation = foodBankLocation;
    }

    public String getFoodBankLocation() {
        return foodBankLocation;
    }

    public void setFoodBankLocation(String foodBankLocation) {
        this.foodBankLocation = foodBankLocation;
    }

}
