import org.apache.spark.{SparkConf, SparkContext}

object ElectionQuery1 {
  def main(args: Array[String]) {
    System.setProperty("hadoop.home.dir","C:\\Users\\ljm7b\\Documents\\Hadoop");
    val sparkConf = new SparkConf().setAppName("ElectionQuery1").setMaster("local[*]")
    val context = new SparkContext(sparkConf)

    val sqlContext = new org.apache.spark.sql.SQLContext(context)

    val dataFrame = sqlContext.read.json("Debate_Data_9_26_2016_Final_1.json")

    //dataFrame.printSchema()

    dataFrame.registerTempTable("TweetText")

    //count of Trump themed tweets
    val resultTrump = sqlContext
      .sql("SELECT COUNT(*) FROM TweetText " +
        "WHERE UPPER(text) LIKE '%TRUMP%' " +
        "OR UPPER(text) LIKE '%DONALD TRUMP%' " +
        "OR UPPER(text) LIKE '%TRUMP2016%' " +
        "OR UPPER(text) LIKE '%DONALD%'")

    //count of hillary themed tweets
    val resultHillary = sqlContext
      .sql("SELECT COUNT(*) FROM TweetText " +
        "WHERE UPPER(text) LIKE '%HILLARY%' " +
        "OR UPPER(text) LIKE '%HILLARY CLINTON%' " +
        "OR UPPER(text) LIKE '%CLINTON%' " +
        "OR UPPER(text) LIKE '%HILLARY2016%' ")


    //top tweeters for data collected
    val uniqueUsers = sqlContext
      .sql("SELECT user.screen_name, COUNT(user.screen_name) as total " +
            "FROM TweetText " +
            "GROUP BY user.screen_name " +
            "ORDER BY COUNT(user.screen_name) DESC")


    uniqueUsers.show()
    //println("Hillary Count: " + hillaryCt.toString)
    println("Trump Tradition Q : " )
    //resultTrump.show()
    println("Hillary Tradition Q: ")
    //resultHillary.show()
    //println("Trump Count: " + trumpCt.toString)
    //println("Clinton Count: " + clintonCt.toString)
  }
}