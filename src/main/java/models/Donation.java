package models;

public class Donation {

    private String donationType;

    public Donation(String donationType) {
        this.donationType = donationType;
    }

    public String getDonationType() {
        return donationType;
    }

    public void setDonationType(String donationType) {
        this.donationType = donationType;
    }


}
