package com.ben;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by benhillier on 2016-09-17.
 * The Locations class was made with the intention of having
 * an object attached to each Listing object.
 * Locations will acquire and store location based information for Listings.
 */
public class Locations {
    /*
        given a string of json from Google's distance matrix,
        returns the travel time between the origin and the Listings
        address
    */
    private final String KEY = "AIzaSyDws3Sleo4VQrsSWRZ8vsokd87z-iutBk0";
    private final String URLBASE = "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&" +
            "&key="+KEY+"&origins=";
    //All location objects should have same origin so it's static.
    private static String origin;
    private String json;
    private int travelTime = -1;
    private String destination;
    private String latitude;
    private String longitude;

    public Locations() {
    }

    private int calculateTravelTime() throws IOException{
        JsonParser jsonParser = new JsonParser();
        JsonObject j1 = (JsonObject) jsonParser.parse(json);
        j1 = (JsonObject) ((JsonArray) j1.get("rows")).get(0);
        j1 = (JsonObject) ((JsonArray) j1.get("elements")).get(0);
        return Integer.parseInt(((JsonObject) j1.get("duration")).get("value").toString());
    }

    public int getTravelTime() throws IOException{
        if(travelTime==-1) {
            return calculateTravelTime();
        } else {
            return travelTime;
        }
    }

    public String getJson() throws Exception{
        if(json!=null) {
            return json;
        }
        return HtmlReader.getHtml(URLBASE+origin+"&destinations="+destination);
    }

    public static void setOrigin(String address) throws Exception {
        String mapsUrl = "https://www.google.ca/maps/place/"+address;
        double[] coord = Locations.generateLatAndLong(mapsUrl);
        System.out.print(coord[0]+","+coord[1]);

        origin = address;
    }

    public String getOrigin() { return origin; }

    private static double[] generateLatAndLong(String address) throws Exception {
        if (address != null) {
            String h = HtmlReader.getHtml(address);
            int index = HtmlReader.findHtmlIndex("cacheResponse\\(\\[\\[\\[", h, 1)[0];
            String coords = h.substring(index, index + 50);
            String[] coordList = HtmlReader.clean(coords, ']').split(",");
            double longitude = Double.parseDouble(coordList[1]);
            double latitude = Double.parseDouble(coordList[2]);
            double[] result = {latitude, longitude};
            return result;
        }
        return null;
    }


}
