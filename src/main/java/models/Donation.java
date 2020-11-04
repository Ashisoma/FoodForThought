package models;

public class Donation {

    private String donationType;
    private int id;

    public Donation(String type, String donationType) {
        this.donationType = donationType;
    }

    public String getDonationType() {
        return donationType;
    }

    public void setDonationType(String donationType) {
        this.donationType = donationType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
