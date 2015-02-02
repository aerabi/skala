package ir.angellandros.scala.collection

import ir.angellandros.scala.collection.Implicits._
import ir.angellandros.scala.testing.SUnit
	
object KeyedVectorsTest {
	def main(args: Array[String]) {
		val tester = new SUnit("KeyedVectors")
		
		val m1 = List(1,1,1,2,2,3).map(_ -> 1.0).reduceByKey(_+_)
		val m2 = List(1,2,2,3,3,3,3).map(_ -> 1.0).reduceByKey(_+_)
		
		tester.assertEquals(11.0, KeyedVectors.dot(m1, m2), "dot method")
		tester.assertEquals(3.0, KeyedVectors.normInfinity(m1), "norm infinity method")
		tester.assertEquals(36.0, KeyedVectors.normNN(m1, 3), "Minkowski norm method")
		tester.assertEquals(35.0, KeyedVectors.distNN(m1, m2, 3), "Minkowski distance method")
		tester.assertEquals(3.7416573867739413, KeyedVectors.norm(m1), "Euclidean norm method")
		tester.assertEquals(3.605551275463989, KeyedVectors.eucDist(m1, m2), "Euclidean distance method")
		tester.assertEquals(13.0, KeyedVectors.sqEucDist(m1, m2), "squared Euclidean distance method")
		tester.assertEquals(0.6415330278717848, KeyedVectors.cosineSim(m1, m2), "cosine similarity method")
		tester.assertEquals(0.3584669721282152, KeyedVectors.cosineDist(m1, m2), "cosine distance method")
    }
}
