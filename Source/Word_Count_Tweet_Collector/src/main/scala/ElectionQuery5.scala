import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Level, Logger}

object ElectionQuery5 {

  def main(args: Array[String]) {
    System.setProperty("hadoop.home.dir", "C:\\Users\\ljm7b\\Documents\\Hadoop");
    val sparkConf = new SparkConf().setAppName("ElectionQuery5").setMaster("local[*]")
    val context = new SparkContext(sparkConf)

    val rootLogger = Logger.getRootLogger()
    rootLogger.setLevel(Level.ERROR)
    val t1 = System.nanoTime
    //Create RDD of timestamps from when each tweet occurred
    val timeStamps = context.textFile("CLEAN_CREATED_AT_ONLY_1.txt")

    //map reduce the timestamps and sort by the the key which is the timestamp (ascending)
    val timeCounts = timeStamps
      .map(word => (word, 1))
      .reduceByKey(_ + _)
      .sortByKey()

    //reduce to one partition so  output file is easy to parse
    // NOTE: if this directory currently exists in output, the program will fail, it cannot overwrite a directory
    timeCounts.coalesce(1).saveAsTextFile("Q5_Largest_Density_of_Tweets_TimeStamp")
    println(timeCounts.first())
    val duration = (System.nanoTime - t1) / 1e9d
    println("Time Duration: " + duration)
  }
}