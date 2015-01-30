package ir.angellandros.scala.collection
import scala.language.implicitConversions

object Implicits {
	implicit def iterableToPairedIterable[K, V](x: Iterable[(K, V)]) = { new PairedIterable(x) }
}