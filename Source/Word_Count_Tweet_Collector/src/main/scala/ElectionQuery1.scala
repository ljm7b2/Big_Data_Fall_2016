import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Level, Logger}

object ElectionQuery1 {
  def main(args: Array[String]) {
    System.setProperty("hadoop.home.dir","C:\\Users\\ljm7b\\Documents\\Hadoop");
    val sparkConf = new SparkConf().setAppName("ElectionQuery1").setMaster("local[*]")
    val context = new SparkContext(sparkConf)

    val sqlContext = new org.apache.spark.sql.SQLContext(context)

    val rootLogger = Logger.getRootLogger()
    rootLogger.setLevel(Level.ERROR)
    val t1 = System.nanoTime
    //make dataframe with 200,000 tweets - 800MB
    val dataFrame = sqlContext.read.json("COMBINED_Twitter_Debate_Data.json")
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

    //query new temp tables to format for nice output
    val totalMentions = sqlContext
      .sql("SELECT h.ct AS Hillary, k.ct AS Kaine, t.ct AS Trump, p.ct AS Pence " +
        "FROM hillary AS h " +
        "JOIN kaine AS k " +
        "JOIN trump AS t " +
        "JOIN pence AS p ")

    // output total mentions of presidents versus their vice president
    totalMentions.show()
    totalMentions.repartition(1).write.format("com.databricks.spark.csv").option("header", "true").save("Q1_Total_Mentions.csv")
    val duration = (System.nanoTime - t1) / 1e9d
    println("Time Duration: " + duration)
  }
}

