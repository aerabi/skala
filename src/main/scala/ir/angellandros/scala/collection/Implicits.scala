package ir.angellandros.scala.collection
import scala.language.implicitConversions
import scala.collection.mutable.HashMap

object Implicits {
	implicit def iterableToPairedIterable[K, V](x: Iterable[(K, V)]) = { new PairedIterable(x) }
	implicit def hashMapToKeyedVector[K](x: HashMap[K, Double]) = { new KeyedVector[K](null, x) }
}
