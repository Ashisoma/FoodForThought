import DAO.Sql2oDonationDAO;
import DAO.Sql2oFoodDAO;

import models.Food;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.*;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) {
        staticFileLocation("/public");

        String connectionString = "jdbc:postgresql://localhost:5432/moringa";
        Connection con;
        Sql2o sql2o = new Sql2o(connectionString, "moringa", "Access");

        Sql2oFoodDAO foodDOA = new Sql2oFoodDAO(sql2o);
        Sql2oDonationDAO donationDAO = new Sql2oDonationDAO(sql2o);
        Map<String, Object> model = new HashMap<>();
        List<String> myStrings = new ArrayList<>();

        get("/", (request, response) -> {
            model.put("foodBankLocations", foodDOA.getFoodBankLocation());
            model.put("donationTypes", donationDAO.getAllDonationLocation());
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());
    }
}
