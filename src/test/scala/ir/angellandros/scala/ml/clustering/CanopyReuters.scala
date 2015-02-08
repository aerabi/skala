package ir.angellandros.scala.ml.clustering

import java.io.File
import scala.io._
import scala.collection.mutable.Set

import ir.angellandros.scala.collection.Implicits._
import ir.angellandros.scala.collection._
import ir.angellandros.scala.ml.clustering._

import java.sql.{Connection, DriverManager, ResultSet}
	
object CanopyReutersTest {
	def main(args: Array[String]) {
		// loading stop-words
		val stopWords = Source.fromFile("src/main/resources/stopwords.txt").getLines.toSet
	
		// loading Reuters dataset
		val dir = new File("/home/m.aerabi/Dev/DataSets/Reuters/C50/C50test")
		val paths = dir.listFiles().flatMap(x => x.listFiles().map(_.getPath()))
		println("Loaded " + paths.length + " files")

		def generateId(name: String) = name.substring(0, name.indexOf("n"))
		
		def mkVector(path: String) = new KeyedVector(generateId(path.split("/").last),
			Source.fromFile(path).mkString
				.toLowerCase()
				.replace("\t", " ")
				.replace("\n", " ")
				.replace("\"", "")
				.replace("\'", "")
				.replace(".", "")
				.replace(",", "")
				.replace(":", "")
				.replace(";", "")
				.split(" ")
				.filter(!stopWords.contains(_))
				.toList
				.map(_ -> 1.0)
				.reduceByKey(_+_)
		)
		
		println("Making vectors")
		// parallelized map
		val vectors = paths.par.map(mkVector).toList
		
		// canopy
		val canopy = new CanopyDriver(0.95, 0.5)
		println("Running canopy")
		
		val then1 = System.currentTimeMillis
		val results = canopy.mergedRun(vectors, KeyedVectors.cosineDist[String])
		val now1 = System.currentTimeMillis
		println("Done in " + (now1 - then1) + " milliseconds!")
		
		val centers = results.map(_._2).toSet
		println(centers)
		println(centers.toList.length)
    }
}
