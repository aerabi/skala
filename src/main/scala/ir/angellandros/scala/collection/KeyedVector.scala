package ir.angellandros.scala.collection

import scala.collection.mutable.HashMap
	
class KeyedVector[K](m: HashMap[K, Double]) {
	def get(key: K): Option[Double] = m.get(key)
	
	val keySet = m.keySet
	
	val toMap = m

	override def toString(): String ={
		def toStringEl(y: Iterable[(K, Double)]): String = {
			if(y.tail.isEmpty) y.head.toString
			else y.head.toString + ", " + toStringEl(y.tail)
		}
		
		"KeyedVector(" + toStringEl(m) + ")"
	}
}
