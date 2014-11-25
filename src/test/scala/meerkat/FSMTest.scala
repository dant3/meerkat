package meerkat

import org.scalatest.{FlatSpec, Matchers}

class FSMTest extends FlatSpec with Matchers  {
  import TrafficLight._

  "An FSM" should "transit correctly" in {
    fsm ! Command.ready ! Command.go ! Command.stop ! Command.go
    fsm.currentState should be (Light.green)
  }


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
}
