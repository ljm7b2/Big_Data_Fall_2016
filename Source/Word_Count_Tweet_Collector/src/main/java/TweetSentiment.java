import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

import java.util.Properties;

public class TweetSentiment {

    public static String TweetSentimentFinder(String line) {

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
        return line.toString() + ' ' + EnumForSentiment(mainSentiment);

    }

    private static String EnumForSentiment(int sentiment) {
        switch (sentiment) {
            case 0:
                return " : very negative";
            case 1:
                return " : negative";
            case 2:
                return " : neutral";
            case 3:
                return " : positive";
            case 4:
                return " : very positive";
            default:
                return " : Error";
        }
    }

    public static void main(String[] args) {
        String tweetSent = TweetSentiment.TweetSentimentFinder("I hate dogs so much!!");
        System.out.println(tweetSent);
    }
}