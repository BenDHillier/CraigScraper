package com.ben;

import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.io.*;


/**
 * Created by benhillier on 2016-08-08.
 * The EmailSender class is a collection of static methods
 * for sending automated emails to craigslist posters.
 */
public class EmailSender {
    /*
        Creates a MimeMessage object given a username, password and Scanner
        object for a file containing the message text. Note that the top line should be the title
        of the message and the rest should be the body. The url is added to the end of the body.
        May have issues working for non-Gmail senders. Has not been tested.
     */

    public static MimeMessage getMessage(String theUsername, String thePassword, Scanner msgTxt, String url) throws FileNotFoundException{
        //honestly don't understand whats happening here. Copy n pasted. Look up later
        final String username = theUsername;
        final String password = thePassword;
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        //setting message subject and body using msgTxt scanner
        String subject = msgTxt.nextLine();
        String body = "";
        while(msgTxt.hasNext()) {
            body += msgTxt.nextLine()+"\n";
        }
        body += url;
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setSubject(subject);
            message.setText(body);
            return message;
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        System.out.println("could not create MimeMessage");
        return null;
    }
    /*
        Sends message to recipient and a carbon copy to cc.
        CC and BCC can be an empty string if you don't want to cc someone.
     */
    public static void sendEmailTo(String recipient, String cc, String bcc, MimeMessage message) throws FileNotFoundException {
        if(hasNotSentEmail(recipient)) {
            try {
                message.setRecipient(RecipientType.TO, new InternetAddress(recipient));
                if(cc.length()>0) {
                    message.addRecipient(RecipientType.CC, new InternetAddress(cc));
                }
                if(bcc.length()>0) {
                    message.addRecipient(RecipientType.BCC, new InternetAddress(bcc));
                }
                Transport.send(message);
                addEmailToSentList(recipient);
                System.out.println("message sent");
                return;
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        System.out.println("message to "+recipient+" not sent");
    }
    /*
        replaces the url found at the end of the Mimemessages body with a new url.
     */
    public static void changeMessageLink(MimeMessage message, String newUrl, String oldUrl) throws IOException, MessagingException {
        String body = message.getContent().toString();
        int urlIndex = body.indexOf(oldUrl);
        body = body.substring(0,urlIndex)+newUrl;
        message.setText(body);

    }

    /*
        Adds email to the sentemail.txt file which is used in the
        hasNotSentEmail method to prevent duplicate emails. Should change
        this to add url as some companies have multiple postings using the same email address.
     */
    private static void addEmailToSentList(String email) throws FileNotFoundException {
        Path file = Paths.get("data/sentemails.txt");
        Charset charset = Charset.forName("US-ASCII");
        try     (BufferedWriter writer = Files.newBufferedWriter(file, charset, StandardOpenOption.APPEND)) {
            writer.write(email+"\n", 0, email.length()+1);
            writer.close();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

    }
    /*
        returns false if String passed as parameter is in the sentemails.txt file.
        Emails are added to the text file after an email is sent to them to
        prevent duplicate emails being sent.
     */
    private static boolean hasNotSentEmail(String email) throws FileNotFoundException{
        File emailList = new File("data/sentemails.txt");
        Scanner emailScanner = new Scanner(emailList);
        while(emailScanner.hasNext()) {
            if(emailScanner.next().equals(email))
                return false;
        }
        return true;
    }
}
