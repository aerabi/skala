# skala
Simple Extensions for Scala

## Current Things
Currently we have implemented two thing:

### PairedIterable
Actually, `ir.angellandros.scala.collection.PairedIterable`. The main reason for such a data structure is to have `reduceByKey`.
You can now do this:

```
import ir.angellandros.scala.collection.Implicits._
val l = List(1,1,1,2,2,3)
l.map(_ -> 1).reduceByKey(_+_)
```

### SUnit
Simple unit testing class. Now has `assertEquals`.