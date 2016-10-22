import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.functions._
import org.apache.log4j.{Level, Logger}

object ElectionQuery4 {

  val snl = new ScreenNameLocationSnatcher();

  def main(args: Array[String]) {
    System.setProperty("hadoop.home.dir", "C:\\Users\\ljm7b\\Documents\\Hadoop");
    val sparkConf = new SparkConf().setAppName("ElectionQuery4").setMaster("local[*]")
    val context = new SparkContext(sparkConf)
    val sqlContext = new org.apache.spark.sql.SQLContext(context)

    val rootLogger = Logger.getRootLogger()
    rootLogger.setLevel(Level.ERROR)


    val tweetTextFile = context.textFile("Combined_Tweet_Text.txt")
    val popularHashTags = context.textFile("HashTagTopics.txt")


    //val k = tweetTextFile.map(MyFunctions.func1)

    val lineWithTrump = tweetTextFile.filter(line => line.contains("trump"))
    println(lineWithTrump.count())



    //println(k.first())
  }
  object MyFunctions {
    def func1(s: String): String = { s + " " + snl.GetUserLocation(s) }
  }
}