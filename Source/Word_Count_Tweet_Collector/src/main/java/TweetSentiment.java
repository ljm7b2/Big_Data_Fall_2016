import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.logging.RedwoodConfiguration;

import java.util.Properties;

public class TweetSentiment {

    public static int TweetSentimentFinder(String line) {
        RedwoodConfiguration.empty().capture(System.err).apply();
        Properties properties = new Properties();
        properties.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(properties);
        int mainSentiment = 0;
        if (line != null && line.length() > 0) {
            int longest = 0;
            Annotation annotation = pipeline.process(line);
            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = sentence.get(SentimentCoreAnnotations.AnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                String partText = sentence.toString();
                if (partText.length() > longest) {
                    mainSentiment = sentiment;
                    longest = partText.length();
                }
            }
        }
        RedwoodConfiguration.current().clear().apply();
        return EnumForSentiment(mainSentiment);

    }

    private static int EnumForSentiment(int sentiment) {
        switch (sentiment) {
            case 0:
                return 0; //"very negative";
            case 1:
                return 1; //"negative";
            case 2:
                return 2; //"neutral";
            case 3:
                return 3; //"positive";
            case 4:
                return 4; //"very positive";
            default:
                return 5; //"Error";
        }
    }

    public static void main(String[] args) {
        int tweetSent = TweetSentiment.TweetSentimentFinder("I love dogs so much!!");
        System.out.println(tweetSent);
    }
}