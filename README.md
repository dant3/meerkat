Meerkat
===========

A minimalistic FSM library written with scala in mind.

Example
-----------

Here is your typical traffic lights fsm:
```scala
import meerkat._

object TrafficLight {
  case class Light private(name:String)
  object Light {
    val red = new Light("red")
    val yellow = new Light("yellow")
    val green = new Light("green")
  }

  case class Command private(name:String)
  object Command {
    val stop = new Command("stop")
    val ready = new Command("ready")
    val go = new Command("go")
  }


  val fsm = new FSM[Light, Command](Light.red) {
    override def handleEvent = {
      case Light.yellow -> Command.stop  => Light.red
      case _            -> Command.ready => Light.yellow
      case Light.yellow -> Command.go    => Light.green
    }

    override def onTransition = {
      case (from -> to) -> cmd => println(s"${from.name} --(${cmd.name})--> ${to.name}")
    }

    override def onRejectedEvent(state:State, event:Event):Any = println(s"${state.name} --(${event.name})--x")
  }
}

/*use-case example:*/
import TrafficLight._

fsm ! Command.ready ! Command.go /*those will not change state: */ ! Command.stop ! Command.go

/**
 * this should yield in console:
 *    red --(ready)--> yellow
 *    yellow --(go)--> green
 *    green --(stop)--x
 *    green --(go)--x
 */
```
