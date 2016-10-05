package com.ben;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;

import javax.mail.internet.MimeMessage;
import javax.swing.text.html.HTML;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;
import static spark.Spark.*;

/**
 * The App class is the main class that
 * runs all of the functionality.
 */

public class App {

    public static void main(String[] args) throws Exception, FileNotFoundException{
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
*/
        File f = new File("views/Home.html");

        Scanner in = new Scanner(f);
        String html = "";
        while(in.hasNext()) {
            html += in.nextLine();
        }
        final String h = html;
        get("/", (request, response) -> h );




        //Scanner msgTxt = new Scanner(new File("data/messageText.txt"));
        //MimeMessage myMsg = EmailSender.getMessage(username, password, msgTxt, houses[0].getUrl());
        //System.out.println(myMsg.getContent());
        /*for(int i=1;i<houses.length;i++) {
            String recipient = houses[i].getEmail();
            EmailSender.changeMessageLink(myMsg, houses[i].getUrl(), houses[i-1].getUrl());
            System.out.println(myMsg.getContent());
            //EmailSender.sendEmailTo(recipient,"","bendhillier@gmail.com", myMsg);
        }*/
    }
}
