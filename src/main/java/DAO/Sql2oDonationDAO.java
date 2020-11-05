package DAO;

import models.Donation;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oDonationDAO implements DonationDAO{


    private final Sql2o sql2o;

    public Sql2oDonationDAO(Sql2o sql2o) {
        this.sql2o = sql2o;
    }
    @Override
    public List<Donation> getAllDonationType(){
        String sql = "SELECT * FROM donationTypes";
        try (Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .executeAndFetch(Donation.class);
        }catch (Sql2oException ex){
            System.out.println(ex);
            return null;
        }
    }

    @Override
    public void addDonation(Donation donation){
        String sql = "INSERT INTO donationTypes (foodItem, cash) VALUES (:foodItem, :cash)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql,true).throwOnMappingFailure(false).bind(donation)
                    .executeUpdate().getKey();
            donation.setId(id);
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public Donation getDonationById(int id) {
        String sql = "SELECT * FROM donationTypes WHERE id=:id";
        try(Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .addParameter("id",id)
                    .executeAndFetchFirst(Donation.class);
        }catch (Sql2oException ex) {
            System.out.println(ex);
            return null;
        }

    }

    @Override
    public void deleteDonationById(int id) {
        String sql = "DELETE FROM donationTypes WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .addParameter("id",id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public void updateDonation(int id, String donationType) {
        String sql = "UPDATE donationTypes SET foodItem = :foodItem WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .addParameter("id",id)
                    .addParameter("foodItem",donationType)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

}
