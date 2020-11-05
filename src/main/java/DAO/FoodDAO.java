package DAO;

import models.Food;

import java.util.List;

public interface FoodDAO {

    void addFoodDonation(Food food);
    List<Food> getFoodBankLocation();
    Food getFoodBankLocationById(int id);
    void addFoodBankLocation(Food location);
    void deleteFoodBankLocation(int id);
    void updateFoodBankLocation(int id, String location);
}
