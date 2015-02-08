package ir.angellandros.scala.ml.clustering

import scala.collection.mutable.HashMap
import scala.collection.mutable.Set

import ir.angellandros.scala.collection._
import ir.angellandros.scala.collection.Implicits._
	
class CanopyDriver(t1: Double, t2: Double) {
	
	def run[K](vs: Iterable[KeyedVector[K]], f: (KeyedVector[K], KeyedVector[K]) => Double): HashMap[KeyedVector[K], KeyedVector[K]] = {
		val map = new HashMap[KeyedVector[K], KeyedVector[K]] // map from data to clusters
		val set: Set[KeyedVector[K]] = Set() // the set
		var count = 0
		for(v <- vs) set += v
		for(v <- vs) {
			count += 1
			print(count + " ")
			if(set.contains(v)) {
				vs
					.map{ x => (x, f(x, v)) }
					.foreach { case (x, d) =>
						if(d < t1) map.put(x, v)
						if(d < t2) set -= x
					}
			}
		}
		
		println()
		map
	}
	
	def mergedRun[K](vs: List[KeyedVector[K]], f: (KeyedVector[K], KeyedVector[K]) => Double) = {
		val partSize: Int = Math.sqrt(vs.length).ceil.toInt
		
		def runPartially(vs: List[KeyedVector[K]]): HashMap[KeyedVector[K], KeyedVector[K]] = {
			if(vs.drop(partSize).isEmpty) run(vs.take(partSize), f)
			else run(vs.take(partSize), f) ++ runPartially(vs.drop(partSize))
		}
		
		val map = runPartially(vs)
		val centers = map.map(_._2).toSet
		
		println("Merge run")
		val merge = run(centers, f)
		
		println("Merging")
		map mapValues merge
	}
}

class LooseCanopyDriver(t1: Double, t2: Double) {
	
	def train[K](vs: Iterable[KeyedVector[K]]) = {
		val map = new HashMap[KeyedVector[K], KeyedVector[K]] // map from data to clusters
		val set: Set[KeyedVector[K]] = Set() // the set
		val par = vs.par
		val nvs = par.map{ x => (x, KeyedVectors.normNN(x, 2)) }
		var count = 0
		for(v <- vs) set += v
		for(v <- vs) {
			count += 1
			println(count)
			if(set.contains(v)) {
				var secondCount = 0
				val norm = KeyedVectors.normNN(v, 2)
				nvs.foreach { case (x, n) =>
					secondCount += 1
					print(" " + secondCount)
					val d = Math.abs(n - norm)
					if(d < t1) map.put(x, v)
					if(d < t2) set -= x
				}
			}
		}
		
		map
	}
}
