import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.functions._
import org.apache.log4j.{Level, Logger}

object ElectionQuery4 {

  def main(args: Array[String]) {
    System.setProperty("hadoop.home.dir", "C:\\Users\\ljm7b\\Documents\\Hadoop");
    val sparkConf = new SparkConf().setAppName("ElectionQuery4").setMaster("local[*]")
    val context = new SparkContext(sparkConf)
    val sqlContext = new org.apache.spark.sql.SQLContext(context)

    val rootLogger = Logger.getRootLogger()
    rootLogger.setLevel(Level.ERROR)
    val t1 = System.nanoTime
    // create RDD from hashtags collected from debate tweets
    val tweetTextFile = context.textFile("CLEAN_HASHTAGS_ONLY.txt")
      //do word count on top hash tags
      .map(word => (word, 1))
      .reduceByKey(_ + _)
      //sort the tuples in descending by the value
      .map( item => item.swap)
      .sortByKey(false, 1)
      .map(item => item.swap)


    //load popular hashtags given by professor into RDD, do a basic map to create RDD pair
    val popularHashTags = context.textFile("HashTagTopics.txt").map(word => (word, 0))

    //Perform JOIN on the two RDDs
    val popularDebateHashTags = tweetTextFile
      .join(popularHashTags)
      //re-map to get rid of ther original value for popularHashTags tuple and keep original word count tuple value
      //for example this would change (#hash, (352, 0))  into -> (#hash, 352)
      .map(item => (item._1, item._2._1))
      //Sort by descending value
      .map(item => item.swap)
      .sortByKey(false, 1)
      .map(item => item.swap)

    println(popularDebateHashTags.first())
    popularDebateHashTags.saveAsTextFile("Q4 _popular_debate_hashtags_output");
    val duration = (System.nanoTime - t1) / 1e9d
    println("Time Duration: " + duration)
  }
}