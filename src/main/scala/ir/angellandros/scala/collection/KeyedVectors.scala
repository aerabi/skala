package ir.angellandros.scala.collection

import scala.collection.mutable.HashMap
import Implicits._
	
object KeyedVectors {
	def reducePairs[K, V](vec1: HashMap[K, V], vec2: HashMap[K, V], func: (V,V) => V, defValue: V) = {
		val map: HashMap[K, V] = new HashMap[K, V]()
		(vec1.keySet ++ vec2.keySet).foreach { k =>
			vec1.get(k) match {
				case None => vec2.get(k) match {
					case None => map.put(k, func(defValue, defValue)) // this won't happen, but scala compiler is idiot! :D
					case Some(v2) => map.put(k, func(defValue, v2))
				}
				case Some(v1) => vec2.get(k) match {
					case None => map.put(k, func(v1, defValue))
					case Some(v2) => map.put(k, func(v1, v2))
				}
			}
		}
		map
	}
	
	def reducePairs[K](vec1: KeyedVector[K], vec2: KeyedVector[K], func: (Double, Double) => Double) = {
		val map: HashMap[K, Double] = new HashMap[K, Double]()
		(vec1.keySet ++ vec2.keySet).foreach { k =>
			vec1.get(k) match {
				case None => vec2.get(k) match {
					case None => map.put(k, func(0.0, 0.0)) // this won't happen, but scala compiler is idiot! :D
					case Some(v2) => map.put(k, func(0.0, v2))
				}
				case Some(v1) => vec2.get(k) match {
					case None => map.put(k, func(v1, 0.0))
					case Some(v2) => map.put(k, func(v1, v2))
				}
			}
		}
		map
	}
	
	def dot[K](vec1: KeyedVector[K], vec2: KeyedVector[K]) = 
		reducePairs[K](vec1, vec2, _*_).map(_._2).reduce(_+_)
		
	def normInfinity[K](vec: KeyedVector[K]) =
		vec.toMap.map(x => Math.abs(x._2)).reduce((x,y) => if(x>y) x else y)
		
	def normNN[K](vec: KeyedVector[K], n: Int) =
		vec.toMap.map(x => Math.pow(Math.abs(x._2), n)).reduce(_+_)
		
	def distNN[K](vec1: KeyedVector[K], vec2: KeyedVector[K], n: Int) =
		normNN(reducePairs[K](vec1, vec2, _-_), n)
		
	def norm[K](vec: KeyedVector[K]) =
		Math.sqrt(normNN(vec, 2))
		
	def eucDist[K](vec1: KeyedVector[K], vec2: KeyedVector[K]) =
		norm(reducePairs[K](vec1, vec2, _-_))
		
	def sqEucDist[K](vec1: KeyedVector[K], vec2: KeyedVector[K]) =
		reducePairs[K](vec1, vec2, (x, y) => (x-y)*(x-y)).map(_._2).reduce(_+_)
		
	def cosineSim[K](vec1: KeyedVector[K], vec2: KeyedVector[K]) =
		dot(vec1, vec2) / (norm(vec1) * norm(vec2))
		
	def cosineDist[K](vec1: KeyedVector[K], vec2: KeyedVector[K]) =
		1 - cosineSim(vec1, vec2)
}
