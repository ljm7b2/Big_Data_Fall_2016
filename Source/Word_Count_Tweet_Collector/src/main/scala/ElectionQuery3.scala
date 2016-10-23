import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.functions._
import org.apache.log4j.{Level, Logger}

object ElectionQuery3 {
  def main(args: Array[String]) {
    System.setProperty("hadoop.home.dir","C:\\Users\\ljm7b\\Documents\\Hadoop");
    val sparkConf = new SparkConf().setAppName("ElectionQuery3").setMaster("local[*]")
    val context = new SparkContext(sparkConf)

    val sqlContext = new org.apache.spark.sql.SQLContext(context)

    val rootLogger = Logger.getRootLogger()
    rootLogger.setLevel(Level.ERROR)

    //make dataframe with 200,000 tweets - 800MB
    val dataFrame = sqlContext.read.json("COMBINED_Twitter_Debate_Data.json")
    //dataFrame.printSchema()

    dataFrame.registerTempTable("TweetText")

    val resultHillaryTweets = sqlContext
      .sql("SELECT text AS Text FROM TweetText " +
        "WHERE UPPER(text) LIKE '%HILLARY%' " +
        "OR UPPER(text) LIKE '%HILLARY CLINTON%' " +
        "OR UPPER(text) LIKE '%CLINTON%' " +
        "OR UPPER(text) LIKE '%HILLARY2016%' ")


    val resultTrumpTweets = sqlContext
      .sql("SELECT text AS Text FROM TweetText " +
        "WHERE UPPER(text) LIKE '%TRUMP%' " +
        "OR UPPER(text) LIKE '%DONALD TRUMP%' " +
        "OR UPPER(text) LIKE '%TRUMP2016%' " +
        "OR UPPER(text) LIKE '%DONALD%'")

    // Create User Defined Function for processing sentiment
    val coder: (String => Int) = (arg: String) => { TweetSentiment.TweetSentimentFinder(arg)}
    val sqlfunc = udf(coder)

    // process new column with sentiment value for Trump
    val trumpSentiment = resultTrumpTweets.withColumn("sentiment", sqlfunc(col("Text")))
    trumpSentiment.registerTempTable("trumpSentiment")

    // process new column with sentiment value for Hillary
    val hillarySentiment = resultHillaryTweets.withColumn("sentiment", sqlfunc(col("Text")))
    hillarySentiment.registerTempTable("hillarySentiment")

    //Count totals
    val totalTrumpRows = resultTrumpTweets.select("Text").count()
    val totalHillaryRows = resultHillaryTweets.select("Text").count()

    val sentimentResultTrump = sqlContext
      .sql("SELECT sentiment, COUNT(sentiment) AS sentIndividualTotal, " +
        "(COUNT(sentiment) * 100.0)/(" + totalTrumpRows * 100.0 + ") AS percentage, " +
        totalTrumpRows + " AS totalCount " +
        "FROM trumpSentiment " +
        "GROUP BY sentiment " )

    sentimentResultTrump.show()

    sentimentResultTrump.repartition(1).write.format("com.databricks.spark.csv").option("header","true").save("Q3_Sentiment_Trump.csv")


    val sentimentResultHillary = sqlContext
      .sql("SELECT sentiment, COUNT(sentiment) AS sentIndividualTotal, " +
        "(COUNT(sentiment) * 100.0)/(" + totalHillaryRows * 100.0 + ") AS percentage, " +
        totalHillaryRows + " AS totalCount " +
        "FROM hillarySentiment " +
        "GROUP BY sentiment " )

    sentimentResultHillary.show()

    sentimentResultHillary.repartition(1).write.format("com.databricks.spark.csv").option("header","true").save("Q3_Sentiment_Hillary.csv")
  }
}

