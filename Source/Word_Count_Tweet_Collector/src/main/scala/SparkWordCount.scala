import org.apache.spark.{SparkConf, SparkContext}

object SparkWordCount {
  def main(args: Array[String]) {
    System.setProperty("hadoop.home.dir","C:\\Users\\ljm7b\\Documents\\Hadoop");
    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")
    val context = new SparkContext(sparkConf)
    val input = context.textFile("output_twitter_text.txt")
    val wrdCnt = input.flatMap(line=>{line.split(" ")}).map(word=>(word,1)).cache()
    val output = wrdCnt.reduceByKey(_+_)
    output.saveAsTextFile("output_word_count")
    val out = output.collect()
    var str:String = "Words:Count \n"
    out.foreach{case(word,count)=>{ str += word + " : " + count + "\n" }}
  }

//  object Sentiment extends Enumeration {
//    type Sentiment = Value
//    val POSITIVE, NEGATIVE, NEUTRAL = Value
//
//    def toSentiment(sentiment: Int): Sentiment = sentiment match {
//      case x if x == 0 || x == 1 => Sentiment.NEGATIVE
//      case 2 => Sentiment.NEUTRAL
//      case x if x == 3 || x == 4 => Sentiment.POSITIVE
//    }
//  }
}
