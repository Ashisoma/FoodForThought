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
    public List<Donation> getAllDonationLocation(){
        String sql = "SELECT * FROM foodbank";
        try (Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .executeAndFetch(Donation.class);
        }catch (Sql2oException ex){
            System.out.println(ex);
            return null;
        }
    }

}
