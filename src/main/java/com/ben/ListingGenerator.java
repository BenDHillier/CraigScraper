package com.ben;
import java.util.ArrayList;

/**
 * Created by benhillier on 2016-08-08.
 */
public class ListingGenerator {
    public String baseUrl = "https://victoria.craigslist.ca";

    public ArrayList<Listing> makeHouseArrayWithUrls(String[] urls) throws Exception {
        ArrayList<Listing> houseArray = new ArrayList<Listing>();
        for(int i=1;i<urls.length;i++) {
            Listing h = makeHouseWithUrl(urls[i]);
            generateLatAndLong(h);
            if(h!= null) {
                houseArray.add(h);
            }
        }
        return houseArray;
    }
    /*
    *	given the url for a posting, this method creates and
    *	returns a house object.
    */
    public Listing makeHouseWithUrl(String url) throws Exception{
        String html = HtmlReader.getHtml(url);
        String email = getLandlordEmail(html);
        int price = getPrice(html);
        String description = getDescription(html);
        String title = getTitle(html);
        String address = getAddress(html);
        Listing house = new Listing(title, price, description, email, url, address);
        return house;
    }
    /*
    *	given a postings html, this method returns the posters email if
    *	he has one and an empty string otherwise.
    */
    private String getLandlordEmail(String html) throws Exception{
        int emailLinkIndex = HtmlReader.findHtmlIndex("replylink\" href=\"", html, 1)[0];
        String email = "";
        //if there is a reply link
        if(emailLinkIndex != 0) {
            int emailLinkLength = 25;
            String emailLink = (this.baseUrl+html.substring(
                    emailLinkIndex,emailLinkIndex+emailLinkLength));
            String emailHtml = HtmlReader.getHtml(emailLink);

            if(emailHtml.contains("mailapp")) {
                int emailIndex = HtmlReader.findHtmlIndex("mailapp\">", emailHtml, 1)[0];
                int emailLength = 50;
                String emailDirty = emailHtml.substring(emailIndex,emailIndex+emailLength);
                //removing extra stuff at end of emailDirty
                email = HtmlReader.clean(emailDirty, '<');
            }
        }
        return email;
    }
    //given the html for a posting, returns the price.
    private int getPrice(String html) {
        int priceIndex = HtmlReader.findHtmlIndex("price\">", html, 1)[0];
        String sPrice = html.substring(priceIndex+1,priceIndex+6);
        int price = Integer.parseInt(HtmlReader.clean(sPrice, '<'));
        return price;
    }

    private String getDescription(String html) {
        int descIndex = HtmlReader.findHtmlIndex("content=\"", html, 1)[0];
        int descLength = 200;
        String description = html.substring(descIndex, descIndex+descLength);
        return HtmlReader.clean(description, '"');
    }

    private String getTitle(String html) {
        int titleIndex = HtmlReader.findHtmlIndex("title>", html, 1)[0];
        int titleLength = 40;
        String title = html.substring(titleIndex, titleIndex+titleLength);
        return HtmlReader.clean(title, '<');
    }

    private String getAddress(String html) {
        int addressIndex = HtmlReader.findHtmlIndex("<a target=\"_blank\" href=\"", html, 1)[0];
        int addressLength = 100;
        String address = html.substring(addressIndex, addressIndex+addressLength);
        if(!address.startsWith("http"))
            return null;
        return HtmlReader.clean(address, '"');
    }
    /*
        Takes in a listing object and finds and sets the objects lat and long,
        if the objects address field is not null.
     */
    public void generateLatAndLong(Listing listing) throws Exception {
        if(listing.getAddress()!=null) {
            String h = HtmlReader.getHtml(listing.getAddress());
            int index = HtmlReader.findHtmlIndex("cacheResponse\\(\\[\\[\\[", h, 1)[0];
            String coords = h.substring(index, index+50);
            String[] coordList = HtmlReader.clean(coords, ']').split(",");
            double longitude = Double.parseDouble(coordList[1]);
            double latitude = Double.parseDouble(coordList[2]);
            listing.setLatAndLong(latitude, longitude);
        }
    }

}