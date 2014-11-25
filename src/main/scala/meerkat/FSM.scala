package meerkat

abstract class FSM[StateID, EventType](val startState:StateID) {
  private[this] var _currentState:StateID = startState
  def currentState:StateID = _currentState
  private[this] val doNothing = {(any:Any) => Unit}


  type State = StateID
  type Event = EventType
  type Transition = ((State, State), Event)

  final def ! (event:Event) = receive(event)

  final def receive(event:Event):this.type = this.synchronized {
    if (canHandleEvent(event)) {
      val previousState = _currentState
      val newState = handleEvent(previousState -> event)
      onTransition(from = previousState, to = newState, event = event)
      _currentState = newState
    } else {
      onRejectedEvent(_currentState, event)
    }
    this
  }

  def canHandleEvent(event:Event) = handleEvent.isDefinedAt(_currentState -> event)
  def handleEvent:PartialFunction[(State, Event), State]

  private[this] final def onTransition(from:State, to:State, event:Event):Any = {
    onTransition.applyOrElse((from -> to) -> event, doNothing)
  }

  protected[this] def onRejectedEvent(state:State, event:Event):Any = {}

  protected[this] def onTransition:PartialFunction[Transition, Any] = {
    case _ =>
  }
}