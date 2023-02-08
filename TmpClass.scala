//package JNI

import org.apache.spark.sql.{DataFrame, SparkSession}

class TmpClass {
  // --- Native methods
  @native def intSumMethod(n: Int, m : Int): Int
  @native def booleanMethod(b: Boolean): Boolean
  @native def stringMethod(s: String): String
  @native def intArrayMethod(a: Array[Int]): Int
}

object TmpClass {

    // --- Main method to test our native library
    def main(args: Array[String]): Unit = {
    val sparkSession:SparkSession = SparkSession.builder().master("local[*]").appName("SparkTestSuite1").getOrCreate()
    import sparkSession.implicits._
    /*
    val testDf1 = Seq(("dummy_val1","dummy_val2"),("dummy_val3","dummy_val4"),("dummy_val5","dummy_val6"),("dummy_val7","dummy_val8"),("dummy_val9","dummy_val10"),("dummy_val11","dummy_val12"),("dummy_val13","dummy_val14"),("dummy_val15","dummy_val16"),("dummy_val17","dummy_val18"),("dummy_val19","dummy_val20"),("dummy_val21","dummy_val22")).toDF("key","col_2")

    testDf1.show()
    */
    //val df = createDummyEmptyDataFrame()
/*
    System.loadLibrary("TmpClass")
    val tmp = new TmpClass
    val add = tmp.intSumMethod(5,6)
    val bool = tmp.booleanMethod(true)
    val text = tmp.stringMethod("java")
    val sum = tmp.intArrayMethod(Array(1, 1, 2, 3, 5, 8, 13))

    println(s"intMethod: $add")
    println(s"booleanMethod: $bool")
    println(s"stringMethod: $text")
    println(s"intArrayMethod: $sum")
*/
    val testData = Seq((1,11),(2,12),(3,13))

    val df = testData.toDF("Num1","Num2")

    df.show()

    val df2 = df.rdd.mapPartitions( partition => {

        val newPartition = partition.map( record => {

          System.loadLibrary("TmpClass")
          val tmp = new TmpClass
          // Get first number from the partition
          val num1 = record.getInt(record.fieldIndex("Num1"))

          // Get second number from the partition
          val num2 = record.getInt(record.fieldIndex("Num2"))

          // Add two numbers
          val res =  tmp.intSumMethod(num1,num2)
          // val res = num1 + num2
          (num1,num2,res)
        }).toList

        newPartition.iterator
    })

      val newDf = df2.toDF("Num1","Num2","Result")
      newDf.show()
  }
/*
  def createDummyEmptyDataFrame(): DataFrame= {
    import sparkSession.implicits._
    

    testDf1
  }
*/
}

