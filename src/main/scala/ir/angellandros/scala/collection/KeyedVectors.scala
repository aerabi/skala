package ir.angellandros.scala.collection

import scala.collection.mutable.HashMap
	
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
	
	def dot[K](vec1: HashMap[K, Double], vec2: HashMap[K, Double]) = 
		reducePairs[K, Double](vec1, vec2, _*_, 0.0).map(_._2).reduce(_+_)
		
	def normInfinity[K](vec: HashMap[K, Double]) =
		vec.map(x => Math.abs(x._2)).reduce((x,y) => if(x>y) x else y)
		
	def normNN[K](vec: HashMap[K, Double], n: Int) =
		vec.map(x => Math.pow(Math.abs(x._2), n)).reduce(_+_)
		
	def distNN[K](vec1: HashMap[K, Double], vec2: HashMap[K, Double], n: Int) =
		normNN(reducePairs[K, Double](vec1, vec2, _-_, 0.0), n)
		
	def norm[K](vec: HashMap[K, Double]) =
		Math.sqrt(normNN(vec, 2))
		
	def eucDist[K](vec1: HashMap[K, Double], vec2: HashMap[K, Double]) =
		norm(reducePairs[K, Double](vec1, vec2, _-_, 0.0))
		
	def sqEucDist[K](vec1: HashMap[K, Double], vec2: HashMap[K, Double]) =
		reducePairs[K, Double](vec1, vec2, (x, y) => (x-y)*(x-y), 0.0).map(_._2).reduce(_+_)
		
	def cosineSim[K](vec1: HashMap[K, Double], vec2: HashMap[K, Double]) =
		dot(vec1, vec2) / (norm(vec1) * norm(vec2))
		
	def cosineDist[K](vec1: HashMap[K, Double], vec2: HashMap[K, Double]) =
		1 - cosineSim(vec1, vec2)
}
