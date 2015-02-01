# skala
Simple Extensions for Scala

## Build
There are multiples ways to build the package.

### Scalac
Scala compiler is the simplest way to build the thing.

```
$ mkdir classes
$ cd classes
$ scalac ../src/main/scala/ir/angellandros/scala/collection/PairedIterable.scala
$ scalac ../src/main/scala/ir/angellandros/scala/collection/Implicits.scala
$ scalac ../src/main/scala/ir/angellandros/scala/testing/SUnit.scala
$ scalac ../src/test/scala/ir/angellandros/scala/collection/PairedIterableTest.scala
```

And test it using

```
$ scala ir.angellandros.scala.collection.PairedIterableTest
```

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

### SUnit
Simple unit testing class. Now has `assertEquals`.
