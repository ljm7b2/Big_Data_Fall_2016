import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Level, Logger}

object ElectionQuery2 {
  def main(args: Array[String]) {
    System.setProperty("hadoop.home.dir","C:\\Users\\ljm7b\\Documents\\Hadoop");
    val sparkConf = new SparkConf().setAppName("ElectionQuery2").setMaster("local[*]")
    val context = new SparkContext(sparkConf)
    val sqlContext = new org.apache.spark.sql.SQLContext(context)

    val rootLogger = Logger.getRootLogger()
    rootLogger.setLevel(Level.ERROR)

    //make dataframe with 200,000 tweets - 800MB
    val dataFrame = sqlContext.read.json("SMALL_Debate_data.json")
    //dataFrame.printSchema()

    dataFrame.registerTempTable("TweetText")

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

    // output top tweeters
    uniqueUsers.show()

    // output avg tweet per user
    avgTweetPerUser.show()

  }
}

