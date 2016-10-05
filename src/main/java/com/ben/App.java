package com.ben;
import spark.ModelAndView;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

/**
 * The App class is the main class that
 * runs all of the functionality.
 */

public class App {

    public static void main(String[] args) throws Exception, FileNotFoundException {
        /*System.out.print("City to search: ");
        String website = "https://victoria.craigslist.ca/search/apa";
        String html = HtmlReader.getHtml(website);
        String[] houseListUrls = HtmlReader.getPostingUrls(html, 5);
        ListingGenerator houseMaker = new ListingGenerator();
        //ArrayList<Listing> houses = houseMaker.makeHouseArrayWithUrls(houseListUrls);
        //String username = "thomaswatson127";
        //String password = "";
        Locations.setOrigin("University+of+Victoria");

        Locations l = new Locations();
        System.out.print(l.getOrigin());
*/      staticFileLocation("");
        File f = new File("views/Home.html");
        String layout = "/templates/layout.vsl";

        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", "templates/home.vsl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());
        String hi = "";
        String address = hi.queryParams("address");
        get("/result", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();

            String commute = request.queryParams("commute");
            model.put("commute", commute);
            model.put("address", address);
            model.put("template", "templates/result.vsl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());


    }

        //Scanner msgTxt = new Scanner(new File("data/messageText.txt"));
        //MimeMessage myMsg = EmailSender.getMessage(username, password, msgTxt, houses[0].getUrl());
        //System.out.println(myMsg.getContent());
        /*for(int i=1;i<houses.length;i++) {
            String recipient = houses[i].getEmail();
            EmailSender.changeMessageLink(myMsg, houses[i].getUrl(), houses[i-1].getUrl());
            System.out.println(myMsg.getContent());
            //EmailSender.sendEmailTo(recipient,"","bendhillier@gmail.com", myMsg);
        */
}
