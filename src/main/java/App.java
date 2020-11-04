import DAO.Sql2oDonationDAO;
import DAO.Sql2oFoodDAO;

import models.Donation;
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

        String connectionString = "jdbc:postgresql://localhost:5432/foodbank";
        Connection con;
        Sql2o sql2o = new Sql2o(connectionString, "moringa", "Access");

        Sql2oFoodDAO foodDOA = new Sql2oFoodDAO(sql2o);
        Sql2oDonationDAO donationDAO = new Sql2oDonationDAO(sql2o);
        Map<String, Object> model = new HashMap<>();
        List<String> myStrings = new ArrayList<>();

        get("/", (request, response) -> {
            model.put("foodBankLocations", foodDOA.getFoodBankLocation());
            model.put("donationTypes", donationDAO.getAllDonationType());
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/location/new", (request, response) -> {
            return new ModelAndView(model, "foodbank-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("/location/new", (request, response) -> {
            String foodBankLocation = request.queryParams("location").trim();
            String donationType = request.queryParams("foodItem").trim();
            Food newFoodLocation = new Food(donationType, foodBankLocation);
            foodDOA.addFoodBankLocation(newFoodLocation);
            model.put("foodBankLocations", foodDOA.getFoodBankLocation());
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/donation-type/new", (request, response) -> {
            model.put("donationTypes", donationDAO.getAllDonationType());
            return new ModelAndView(model, "donation-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("/donation-type/new", (request, response) -> {
            String foodBankLocation = request.queryParams("location").trim();
            String donationType = request.queryParams("foodItem").trim();
            Donation newDonation = new Donation(donationType, foodBankLocation);
            donationDAO.addDonation(newDonation);
            model.put("donationTypes", donationDAO.getAllDonationType());
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/location/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            Food food = foodDOA.getFoodbankLocationById(id);
            Donation donation = donationDAO.getDonationById(food.getId());
            model.put("foodBankLocations", foodDOA.getFoodbankLocationById(id));
            model.put("donationTypes", donation);
            return new ModelAndView(model, "foodbank-details.hbs");
        }, new HandlebarsTemplateEngine());

        get("/donation-type/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            model.put("foodBankLocations", donationDAO.getDonationById(id));
            model.put("donationTypes", foodDOA.getFoodbankLocationById(id));
            return new ModelAndView(model, "donationtype-details.hbs");
        }, new HandlebarsTemplateEngine());

        get("/deletelocation/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            foodDOA.deleteFoodBankLocation(id);
            model.put("foodBankLocations", foodDOA.getFoodBankLocation());
            model.put("donationTypes", donationDAO.getAllDonationType());
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/deletedonation-type/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            donationDAO.deleteDonationById(id);
            model.put("foodBankLocations", foodDOA.getFoodBankLocation());
            model.put("donationTypes", donationDAO.getAllDonationType());
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());
    }
}
