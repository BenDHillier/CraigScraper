package com.ben;
import java.lang.Math;

/**
 * Created by benhillier on 2016-08-08.
 * Listing objects represent Craigslist listings.
 *
 */
public class Listing {
    private int price;
    private String description;
    private String url;
    private String email;
    private String title;
    //google maps url for the approximate address of
    //the listing.
    private String address;
    private double latitude;
    private double longitude;
    private int travelTime;

    public Listing(String title,int price,String description,String email,String url, String address) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.email = email;
        this.url = url;
        this.address = address;
    }
    //Make private or restructure
    public void setLatAndLong(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {return this.latitude;}

    public double getLongitude() {return this.longitude;}

    public String getEmail() { return this.email;}

    public int getPrice() {return this.price;}

    public String getDescription() {return this.description;}

    public String getAddress() {return this.address;}

    public void setUrl(String url) {this.url = url;}

    public String getUrl() {return this.url;}

    public String toString() {
        return (title+": $"+price+"\n"+description+"\nEmail: "+email+"\nLink: "+url);
    }

    //deprecated function
    public double getDistanceFromOrigin(double orgLat, double orgLong) {
        final int R = 6371; // Radius of the earth

        Double latDistance = Math.toRadians(orgLat - this.latitude);
        Double lonDistance = Math.toRadians(orgLong - this.longitude);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(this.longitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        a = Math.abs(a);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;
        distance = Math.pow(distance, 2);
        return Math.sqrt(distance);
    }

    public int getTravelTime() { return travelTime;}
    //Make private or restructure.
    public void setTravelTime(int time) {travelTime=time;}

    public boolean equals(Listing ob) {
        if(ob.url.equals(url)) {
            return true;
        }
        return false;
    }

}
