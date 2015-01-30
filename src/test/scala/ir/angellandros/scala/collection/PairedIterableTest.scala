package ir.angellandros.scala.collection

import ir.angellandros.scala.collection.Implicits._
import ir.angellandros.scala.testing.SUnit
	
object PairedIterableTest {
	def main(args: Array[String]) {
		val tester = new SUnit("PairedIterable")
		
		val l = List(1,1,1,2,2,3)
		val pi = new PairedIterable(l.map(_ -> 1))
		
		tester.assertEquals("PairedIterable((1,1), (1,1), (1,1), (2,1), (2,1), (3,1))", pi.toString, "toString method")
		tester.assertEquals("{1=3, 2=2, 3=1}", pi.reduceByKey(_+_).toString, "reduceByKey method")
		tester.assertEquals("{1=3, 2=2, 3=1}", l.map(_ -> 1).reduceByKey(_+_).toString, "implicit reduceByKey method")
    }
}