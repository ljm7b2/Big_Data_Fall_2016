import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.functions._
import org.apache.log4j.{Level, Logger}

object ElectionQuery1 {
  def main(args: Array[String]) {
    System.setProperty("hadoop.home.dir","C:\\Users\\ljm7b\\Documents\\Hadoop");
    val sparkConf = new SparkConf().setAppName("ElectionQuery1").setMaster("local[*]")
    val context = new SparkContext(sparkConf)

    val sqlContext = new org.apache.spark.sql.SQLContext(context)

    val rootLogger = Logger.getRootLogger()
    rootLogger.setLevel(Level.ERROR)

    //make dataframe with 200,000 tweets - 800MB
    val dataFrame = sqlContext.read.json("SMALL_Debate_data.json")

    //dataFrame.printSchema()

    dataFrame.registerTempTable("TweetText")

    //count of Trump themed tweets
    val resultTrump = sqlContext
      .sql("SELECT COUNT(*) as ct FROM TweetText " +
        "WHERE UPPER(text) LIKE '%TRUMP%' " +
        "OR UPPER(text) LIKE '%DONALD TRUMP%' " +
        "OR UPPER(text) LIKE '%TRUMP2016%' " +
        "OR UPPER(text) LIKE '%DONALD%'")

    //count of Pence themed tweets
    val resultPence = sqlContext
      .sql("SELECT COUNT(*) as ct FROM TweetText " +
        "WHERE UPPER(text) LIKE '%MIKE PENCE%' " +
        "OR UPPER(text) LIKE '%PENCE%' ")

    //count of Kaine themed tweets
    val resultKaine = sqlContext
      .sql("SELECT COUNT(*) as ct FROM TweetText " +
        "WHERE UPPER(text) LIKE '%TIM KAINE%' " +
        "OR UPPER(text) LIKE '%KAINE%' ")

    //count of hillary themed tweets
    val resultHillary = sqlContext
      .sql("SELECT COUNT(*) as ct FROM TweetText " +
        "WHERE UPPER(text) LIKE '%HILLARY%' " +
        "OR UPPER(text) LIKE '%HILLARY CLINTON%' " +
        "OR UPPER(text) LIKE '%CLINTON%' " +
        "OR UPPER(text) LIKE '%HILLARY2016%' ")

    //register results of above queries as temp tables
    resultHillary.registerTempTable("hillary")
    resultKaine.registerTempTable("kaine")
    resultTrump.registerTempTable("trump")
    resultPence.registerTempTable("pence")

    //query new temp tables
    val totalMentions = sqlContext
      .sql("SELECT h.ct AS Hillary, k.ct AS Kaine, t.ct AS Trump, p.ct AS Pence " +
        "FROM hillary AS h " +
        "JOIN kaine AS k " +
        "JOIN trump AS t " +
        "JOIN pence AS p ")


    //top tweeters for data collected
    val uniqueUsers = sqlContext
      .sql("SELECT user.screen_name as screenName, COUNT(user.screen_name) as total " +
            "FROM TweetText " +
            "GROUP BY user.screen_name " +
            "ORDER BY COUNT(user.screen_name) DESC")

    uniqueUsers.registerTempTable("topUsers")

    //get AVG number of tweets per user
    val avgTweetPerUser = sqlContext
      .sql("SELECT AVG(total) as AverageTweetPerUser FROM topUsers")


    // output total mentions of president and vice president
    // totalMentions.show()

    // output top tweeters
    //uniqueUsers.show()

    // output avg tweet per user
    //avgTweetPerUser.show()


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

    val coder: (String => Int) = (arg: String) => { TweetSentiment.TweetSentimentFinder(arg)}

    val sqlfunc = udf(coder)

    val trumpSentiment = resultTrumpTweets.withColumn("sentiment", sqlfunc(col("Text")))

    trumpSentiment.registerTempTable("trumpSentiment")

    val hillarySentiment = resultHillaryTweets.withColumn("sentiment", sqlfunc(col("Text")))

    hillarySentiment.registerTempTable("hillarySentiment")

    val totalTrumpRows = resultTrumpTweets.select("Text").count()
    val totalHillaryRows = resultHillaryTweets.select("Text").count()

    val sentimentResultTrump = sqlContext
      .sql("SELECT sentiment, COUNT(sentiment) AS sentIndividualTotal, " +
           "(COUNT(sentiment) * 100.0)/(" + totalTrumpRows * 100.0 + ") AS percentage, " +
           totalTrumpRows + " AS totalCount " +
           "FROM trumpSentiment " +
           "GROUP BY sentiment " )

    sentimentResultTrump.show()

    val sentimentResultHillary = sqlContext
      .sql("SELECT sentiment, COUNT(sentiment) AS sentIndividualTotal, " +
        "(COUNT(sentiment) * 100.0)/(" + totalHillaryRows * 100.0 + ") AS percentage, " +
        totalHillaryRows + " AS totalCount " +
        "FROM hillarySentiment " +
        "GROUP BY sentiment " )

    sentimentResultHillary.show()


  }
}

