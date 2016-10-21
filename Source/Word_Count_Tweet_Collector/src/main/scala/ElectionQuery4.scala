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


    val distFile = context.textFile("All_User_Names_Twitter.txt")

    val k = distFile.map(MyFunctions.func1)


    println(k.first())
  }
  object MyFunctions {
    def func1(s: String): String = { s + " " + snl.GetUserLocation(s) }
  }
}