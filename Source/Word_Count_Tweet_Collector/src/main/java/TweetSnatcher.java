import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterObjectFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.User;

public class TweetSnatcher {
    static BufferedWriter bufferedWriter;
    static FileWriter fileWriter;

    public static void main(String[] args) {
        ConfigurationBuilder configurationBuilder = BuildConfig();
        File file = new File("Debate_Data_9_26_2016_Final_1_2.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fileWriter = new FileWriter(file, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bufferedWriter = new BufferedWriter(fileWriter);
        TwitterStream twitterStream = new TwitterStreamFactory(configurationBuilder.build()).getInstance();
        StatusListener listener = new StatusListener() {
            long count = 0;

            public void onException(Exception arg0) {
            }

            public void onDeletionNotice(StatusDeletionNotice arg0) {
            }

            public void onScrubGeo(long arg0, long arg1) {
            }

            public void onStallWarning(StallWarning arg0) {
            }

            public void onStatus(Status status) {

                OutputUserData(status);

                String jsonTweet = TwitterObjectFactory.getRawJSON(status);
                try {
                    System.out.println(count++ + "\n");
                    bufferedWriter.append(jsonTweet);
                    bufferedWriter.newLine();
                    if(count == 100000) //collect 100,000 Tweets Requirement
                    {
                        bufferedWriter.close();
                        fileWriter.close();

                        System.out.println("Collection of " + count + " tweets complete.");
                        System.exit(0);
                    }
                    System.out.println(count);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            public void onTrackLimitationNotice(int arg0) {
            }
        };
        FilterQuery filterQuery = new FilterQuery();
        String keywords[] = { "#Debates2016", "#debatenight", "#crookedHillary", "#Trump2016", "#Hillary2016" ,"#ImWithHer", "#NeverTrump",
                                "Hillary", "Clinton", "Hillary Clinton", "Donald", "Trump", "Donald Trump", "Tim Kaine", "Kaine", "Mike Pence", "Pence",
                                "#PresidentialElection2016", "#2016debate", "#presidentialdebate", "#debates"};
        filterQuery.track(keywords);
        twitterStream.addListener(listener);
        twitterStream.filter(filterQuery);

    }

    public static ConfigurationBuilder BuildConfig()
    {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setDebugEnabled(true);
        configurationBuilder.setJSONStoreEnabled(true);
        configurationBuilder.setOAuthConsumerKey("dvR8jCcFydJVMqXuU4C4kLLG8");
        configurationBuilder.setOAuthConsumerSecret("NkJ4F94xxXYXo5ss9y2Xlnm1hGkixZAw5Xs0HssPLly0Me4Ktr");
        configurationBuilder.setOAuthAccessToken("4323657393-jzFCbj6h2ljE60ZhgxR1VNGaOghe2Pfl4eowFdW");
        configurationBuilder.setOAuthAccessTokenSecret("4c384sMwiZTenqlzlRbD5knrBMglxxcDUDFd7mgHtYRr1");
        return configurationBuilder;
    }

    public static void OutputUserData(Status status)
    {
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
    }
}