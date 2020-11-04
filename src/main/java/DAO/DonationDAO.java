package DAO;

import models.Donation;

import java.util.List;

public interface DonationDAO {
//
//    List<Donation> getAllDonationLocation();
//
//    void addLocation(Donation donation);
//
//    Donation getDonationById(int id);
//

    List<Donation> getAllDonationType();

    void addDonation(Donation donation);

    Donation getDonationById(int id);

    void deleteDonationById(int id);

    void updateDonation(int id, String donationType);
}

