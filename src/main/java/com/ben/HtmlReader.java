package com.ben;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;

/**
 * HtmlReader class is a collection of static methods
 * for dealing with html retrieval and parsing.
 */

public class HtmlReader {

    /*
        returns string of html given a url.
     */
    public static String getHtml(String url) throws Exception{
        URL site = new URL(url);
        URLConnection c = site.openConnection();
        Scanner input = new Scanner(new InputStreamReader(c.getInputStream()));
        String html = "";
        while(input.hasNext()) {
            html+= input.nextLine();
        }
        return html;
    }
    /*
        Finds a specified string a given number of times
        within a body of html and returns an int array of
        indexes for the end of each string. If the string
        doesn't appear the specified number of times then
        those spots in the array will be zero. The given string is for
        scraping important data found directly after the string.
        As such I usually found the given string by looking through
        the html for what comes directly before the important data.
     */
    public static int[] findHtmlIndex(String word, String text, int num) {
        Pattern p = Pattern.compile(word);
        Matcher m = p.matcher(text);
        int[] indexes = new int[num];
        int counter = 0;
        while(m.find() && counter<num) {
            indexes[counter] = m.end();
            counter++;
        }

        return indexes;
    }
    /*
        Given html for a craigslist search and the number of postings
        to find, this method returns an array containing urls for the
        postings page.
     */
    public static String[] getPostingUrls(String html, int numofPostings) {
        int[] first = findHtmlIndex("<a href=\"/apa/", html, numofPostings);
        String[] postingUrls = new String[first.length];
        int urlLength = 15;
        for(int i=0; i<postingUrls.length;i++) {
            postingUrls[i] = ("https://victoria.craigslist.ca/apa/"+html.substring(first[i],first[i]+urlLength));
        }
        return postingUrls;

    }
    //removes everything after the end character from a string.
    public static String clean(String item, char end) {
        String cleanItem = "";
        for(int i=0; i<item.length();i++) {
            if(item.charAt(i)==end) {
                break;
            } else {
                cleanItem+=item.charAt(i);
            }
        }
        return cleanItem;
    }
}