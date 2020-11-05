package DAO;

import models.Food;
import models.Registration;

import java.util.List;

public interface RegistrationDao {

    void add(Registration registration);

    List<Registration> getAll();

    Registration donorById(int id);

    void deleteDonationById(int id);

    void updateDonation(String name, String email, String password, String location, String donationType);

}
