package DAO;


import models.Donation;
import models.Food;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oFoodDAO implements FoodDAO{

    private final Sql2o sql2o;

    public Sql2oFoodDAO(Sql2o sql2o) {
        this.sql2o = sql2o;
    }
    @Override
    public List<Food> getFoodBankLocation(){
        String sql = "SELECT * FROM foodBank";
        try (Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .executeAndFetch(Food.class);
        }catch (Sql2oException ex){
            System.out.println(ex);
            return null;
        }
    }

    @Override
    public void addFoodBankLocation(Food location){
        String sql = "INSERT INTO foodBankLocations (location) VALUES (:location)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql,true).bind(location)
                    .executeUpdate().getKey();
            location.setId(id);
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public Food getFoodbankLocationById(int id) {
        String sql = "SELECT * FROM foodBankLocations WHERE id=:id";
        try(Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .addParameter("id",id)
                    .executeAndFetchFirst(Food.class);
        }catch (Sql2oException ex) {
            System.out.println(ex);
            return null;
        }

    }

    @Override
    public void deleteFoodBankLocation(int id) {
        String sql = "DELETE FROM foodBankLocations WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id",id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public void updateFoodBankLocation(int id, String location) {
        String sql = "UPDATE foodBankLocations SET location = :location WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id",id)
                    .addParameter("location",location)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }



}
