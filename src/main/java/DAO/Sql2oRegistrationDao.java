package DAO;

import models.Registration;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oRegistrationDao implements RegistrationDao {

    private final Sql2o sql2o;

    public Sql2oRegistrationDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Registration registration) {
        String sql = "INSERT INTO registration (name, email, password, location, donationType) VALUES (:name, :email, :password, :location, :donationType)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true).throwOnMappingFailure(false).bind(registration)
                    .executeUpdate().getKey();
            registration.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public List<Registration> getAll() {
        String sql = "SELECT * FROM registration";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql, true)
                    .executeAndFetch(Registration.class);
        }
    }


    @Override
    public Registration donorById(int id) {
        String sql = "SELECT * FROM registration WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Registration.class);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
        return null;
    }

    @Override
    public void deleteDonationById(int id) {
        String sql = "DELETE FROM registration WHERE id=:id";
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
    public void updateDonation(String name, String email, String password, String location, String donationType) {
        String sql = "UPDATE registration SET name = :name, email = :email, password = :password, location=:location, donationType=:donationType WHERE id=:id";
        try (Connection conn = sql2o.open()) {
            conn.createQuery(sql).addParameter("name", name)
                    .addParameter("email", email)
                    .addParameter("password", password)
                    .addParameter("location", location)
                    .addParameter("donatinType", donationType)
                    .executeUpdate();
        }catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }
}
