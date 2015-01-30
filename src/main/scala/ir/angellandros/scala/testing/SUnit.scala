package ir.angellandros.scala.testing

class SUnit(name: String) {
	def assertEquals[T](x: T, y: T, message: String) = {
		if(x == y) println ("PASSED\t" + message)
		else println ("FAILED\t" + message)
	}
}