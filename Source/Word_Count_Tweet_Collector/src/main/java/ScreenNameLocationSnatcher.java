import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;


public class ScreenNameLocationSnatcher {

    private ConfigurationBuilder configurationBuilder;
    private Twitter twitter;

    public ScreenNameLocationSnatcher()
    {
        configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setDebugEnabled(true);
        configurationBuilder.setJSONStoreEnabled(true);
        configurationBuilder.setOAuthConsumerKey("dvR8jCcFydJVMqXuU4C4kLLG8");
        configurationBuilder.setOAuthConsumerSecret("NkJ4F94xxXYXo5ss9y2Xlnm1hGkixZAw5Xs0HssPLly0Me4Ktr");
        configurationBuilder.setOAuthAccessToken("4323657393-jzFCbj6h2ljE60ZhgxR1VNGaOghe2Pfl4eowFdW");
        configurationBuilder.setOAuthAccessTokenSecret("4c384sMwiZTenqlzlRbD5knrBMglxxcDUDFd7mgHtYRr1");
        twitter = new TwitterFactory(configurationBuilder.build()).getInstance();
    }

    public String GetUserLocation(String screenName)
    {
        String loc = "";
        try {
            User user = twitter.showUser(screenName); // this line
            if (user.getStatus() != null) {
                System.out.println("@" + user.getScreenName() + " - " + user.getLocation());
                loc = user.getLocation();

            } else {
                // protected account
                System.out.println("@" + user.getScreenName());
                loc = user.getLocation();
            }
        }catch(TwitterException e){
            e.printStackTrace();
        }
        return loc;
    }
}
//User user = status.getUser();
// gets Username
//String username = status.getUser().getScreenName();
// System.out.println(username);
//String profileLocation = user.getLocation();
//System.out.println("location: " + profileLocation);
//long tweetId = status.getId();
//System.out.println(tweetId);
//String content = status.getText();
//System.out.println(content +"\n");