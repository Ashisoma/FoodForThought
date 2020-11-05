import DAO.RegistrationDao;
import DAO.Sql2oDonationDAO;
import DAO.Sql2oFoodDAO;

import DAO.Sql2oRegistrationDao;
import com.google.gson.Gson;
import models.Donation;
import models.Food;
import models.Registration;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.*;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) {
        staticFileLocation("/public");


        Gson gson = new Gson();

        String connectionString = "jdbc:postgresql://localhost:5432/foodbank";
        Connection con;
        Sql2o sql2o = new Sql2o(connectionString, "moringa", "Access");

        Sql2oFoodDAO foodDOA = new Sql2oFoodDAO(sql2o);
        Sql2oDonationDAO donationDAO = new Sql2oDonationDAO(sql2o);
        Map<String, Object> model = new HashMap<>();
        List<String> myStrings = new ArrayList<>();
        Sql2oRegistrationDao sql2oRegistrationDao = new Sql2oRegistrationDao(sql2o);
        //Registration registration = new Registration();


        // API ROUTES



        post("/registration/new", "application/json", (req, res) -> {// accept a request in format JSON from app
            Registration registration = gson.fromJson(req.body(), Registration.class);// make java from JSON from an app
            sql2oRegistrationDao.add(registration);//A-OK! But why 201??
            res.status(201);
            res.type("application/json");// send it back to be displayed
            return gson.toJson(registration);
        });

        get("/registration", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            return gson.toJson(sql2oRegistrationDao.getAll());//send it back to be displayed
        });

        get("/registration/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            int registrationId = Integer.parseInt(req.params("id"));
            res.type("application/json");
            return gson.toJson(sql2oRegistrationDao.donorById(registrationId));
        });

        // UI ROUTES







        get("/", (request, response) -> {
            model.put("foodBankLocations", foodDOA.getFoodBankLocation());
            model.put("donationTypes", donationDAO.getAllDonationType());
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/location/new", (request, response) -> {
            return new ModelAndView(model, "foodbank-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("/location/new", (request, response) -> {
            String foodBankLocation = request.queryParams("location");
            String donationType = request.queryParams("foodItem");
            Food newFoodLocation = new Food(donationType, foodBankLocation);
            foodDOA.addFoodBankLocation(newFoodLocation);
            model.put("foodBankLocations", foodDOA.getFoodBankLocation());
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/registration/new", (request, response) -> {
            model.put("registration", sql2oRegistrationDao.getAll());
            return new ModelAndView(model, "registration-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("/registration/new", (request, response) -> {
            String name = request.queryParams("name");
            String email = request.queryParams("email");
            String password = request.queryParams("password");
            String location = request.queryParams("location");
            String donationType = request.queryParams("donationType");
            Registration registration = new Registration(name, email, password, location,donationType);
            sql2oRegistrationDao.add(registration);
            model.put("registration",sql2oRegistrationDao.getAll());
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/registration/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            model.put("donationTypes", sql2oRegistrationDao.donorById(id));
            return new ModelAndView(model, "registration-details.hbs");
        }, new HandlebarsTemplateEngine());

        get("/deleteRegistration/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            sql2oRegistrationDao.deleteDonationById(id);
            model.put("registration",sql2oRegistrationDao.getAll());
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        post("/edit-registration/:id", (req, res) -> {
            int id = Integer.parseInt(req.queryParams("id"));
            String name = req.queryParams("name");
            String email = req.queryParams("email");
            String password = req.queryParams("password");
            String location = req.queryParams("location");
            String donationType = req.queryParams("donationTypes");
            sql2oRegistrationDao.updateDonation(name, email, password, location, donationType);
            model.put("registration", sql2oRegistrationDao.donorById(id));
            return new ModelAndView(model, "registration-details.hbs");
        }, new HandlebarsTemplateEngine());

        get("/edit-registration/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            model.put("edit-registration", true);
            model.put("registration", sql2oRegistrationDao.donorById(id));
            return new ModelAndView(model, "registration-form.hbs");
        }, new HandlebarsTemplateEngine());


        get("/donation-type/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            model.put("foodBankLocations", donationDAO.getDonationById(id));
            model.put("donationTypes", foodDOA.getFoodBankLocationById(id));
            return new ModelAndView(model, "donationtype-details.hbs");
        }, new HandlebarsTemplateEngine());

        get("/deletelocation/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            foodDOA.deleteFoodBankLocation(id);
            sql2oRegistrationDao.deleteDonationById(id);
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
