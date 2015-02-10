# skala
Simple Extensions for Scala

## Build
We are using SBT to build the project. Although, you can build it just using Scalac.


### SBT
Simple Build Tool is the most popular build tool for scala. We respect that. So for compiling the code:

```
$ sbt compile
```

And for testing:

```
$ sbt test:run
```

SBT respects our own SUnit tests.
For making a jar out of the project, do:

```
$ sbt package
```

## Current Things
Currently we have implemented two thing:

### PairedIterable
Actually, `ir.angellandros.scala.collection.PairedIterable`. The main reason for such a data structure is to have `reduceByKey`.
You can now do this:

```scala
import ir.angellandros.scala.collection.Implicits._
val l = List(1,1,1,2,2,3)
l.map(_ -> 1).reduceByKey(_+_)
```

### Keyed Vector
The idea behind `KeyedVector` is that getting rid of dictionary. You don't need a dictionary, because the indices could be `String` or any other thing that you want. Although it could be `Int` and you can use it with some dictionary.
Anyway, `KeyedVector` has a close relation with `HashMap`. Every `KeyedVector` has an ID, and this is the most important difference:

```scala
val vector = new KeyedVector(id, map)
```

and with `vector.id` or `vector.toMap` you can get either one of them. You can also do `vector.get(key)` or `vector.keySet`.

The other class implemented here is `KeyedVectors`, that provides some tools for `KeyedVector`s:

```scala
val i1 = KeyedVectors.dot(v1, v2)
val i2 = KeyedVectors.normInfinity(v1)
val i3 = KeyedVectors.normNN(v1, n) 	// Minkowski norm ^n, which is squared Euclidean norm for n=2
val i4 = KeyedVectors.distNN(v1, v2, n) // distance function induces from norm
val i5 = KeyedVectors.norm(v1) 			// Euclidean norm
val i6 = KeyedVectors.eucDist(v1, v2)
val i7 = KeyedVectors.sqEucDist(v1, v2) // squared Euclidean distance
val i8 = KeyedVectors.cosineSim(v1, v2)
val i9 = KeyedVectors.cosineDist(v1, v2)
```

### Canopy Clustering Algorithm
Canopy algorithm on pure scala. There are two implementations for canopy algorithm. First, the simple implementation with $O(n^2)$, and the other, the merged implementation with $O(n \sqrt{n})$ running time.

```scala
val canopy = new CanopyDriver(0.95, 0.5)
val results = canopy.mergedRun(vectors, KeyedVectors.cosineDist[String])
```

As you can see, the distance function is an input argument.

### SUnit
Simple unit testing class. Now has `assertEquals`.
