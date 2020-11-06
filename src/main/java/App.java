import DAO.Sql2oRegistrationDao;
import com.google.gson.Gson;
import models.Registration;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.*;

import static spark.Spark.*;

public class App {
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
    public static void main(String[] args) {

        port(getHerokuAssignedPort());
        staticFileLocation("/public");

        Gson gson = new Gson();

        //String connectionString = "jdbc:postgresql://localhost:5432/foodbank";
        Connection con;

        String connectionString = "jdbc:postgresql://ec2-54-158-222-248.compute-1.amazonaws.com:5432/d66u5i4ru73bt6";
        Sql2o sql2o = new Sql2o(connectionString,"ivflkzpzdtzxch","a451779a3ce5b5bcbf0758c25c3e1a811c2cba0df4358e3f7348d3fbcca70e41");

       // Sql2o sql2o = new Sql2o(connectionString, "moringa", "Access");

        Map<String, Object> model = new HashMap<>();
        List<String> myStrings = new ArrayList<>();
        Sql2oRegistrationDao sql2oRegistrationDao = new Sql2oRegistrationDao(sql2o);
        //Registration registration = new Registration();

        //String connectionString = "jdbc:postgresql://ec2-18-211-86-133.compute-1.amazonaws.com:5432/d8seknpio0qb3o";

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
            System.out.println(sql2oRegistrationDao.donorById(id));
            model.put("registration", sql2oRegistrationDao.donorById(id));
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

    }
}
