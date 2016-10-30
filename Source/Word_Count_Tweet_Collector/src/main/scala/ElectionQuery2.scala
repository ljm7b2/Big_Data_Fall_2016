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
    val t1 = System.nanoTime
    //make dataframe with 200,000 tweets - 800MB
    val dataFrame = sqlContext.read.json("COMBINED_Twitter_Debate_Data.json")
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
    //uniqueUsers.repartition(1).write.format("com.databricks.spark.csv").option("header", "true").save("Q2_Unique_Users.csv")

    // output avg tweet per user
    avgTweetPerUser.show()
    //avgTweetPerUser.repartition(1).write.format("com.databricks.spark.csv").option("header", "true").save("Q2_AVG_Twt_Per_User.csv")
    val duration = (System.nanoTime - t1) / 1e9d
    println("Time Duration: " + duration)
  }
}

