package DAO;

import models.Food;

import java.util.List;

public interface FoodDAO {

    List<Food> getFoodBankLocation();
    Food getFoodbankLocationById(int id);
    void addFoodBankLocation(Food location);
    void deleteFoodBankLocation(int id);
    void updateFoodBankLocation(int id, String location);
}
