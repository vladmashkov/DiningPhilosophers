import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}

trait Philosopher {
  val name: String

  final val fork_1 = 1
  final val fork_2 = 2
  final val fork_3 = 3
  final val fork_4 = 4
  final val fork_5 = 5

  val withLeft: Int
  val withRight: Int

  final def takeLeft(body: => Unit): Unit = {
    withLeft.synchronized {
      println(s"$name is take left fork $withLeft!")
      body
    }
  }

  final def takeRight(body: => Unit): Unit = {
    withRight.synchronized {
      println(s"$name is take Right fork $withRight!")
      body
    }
  }

  final def live: Unit = {
    takeLeft {
      takeRight {
        eat
      }
    }
  }
  final def eat: Unit = println(s"$name is eating...")
}
class Aristotle extends Philosopher {
  override val name: String = "Aristotle"
  override val withLeft: Int = fork_1
  override val withRight: Int = fork_2
}
class Plato extends Philosopher {
  override val name: String = "Plato"
  override val withLeft: Int = fork_2
  override val withRight: Int = fork_3
}
class Confucius extends Philosopher {
  override val name: String = "Confucius"
  override val withLeft: Int = fork_3
  override val withRight: Int = fork_4
}
class Socrates extends Philosopher {
  override val name: String = "Socrates"
  override val withLeft: Int = fork_4
  override val withRight: Int = fork_5
}
class Machiavelli extends Philosopher {
  override val name: String = "Machiavelli"
  override val withLeft: Int = fork_1
  override val withRight: Int = fork_5
}
object Main extends App {
  val aristotle = new Aristotle
  val plato = new Plato
  val confucius = new Confucius
  val socrates = new Socrates
  val machiavelli = new Machiavelli
  val aristotleLive = Future {
    aristotle.live
  }
  val platoLive = Future {
    plato.live
  }
  val confuciusLive = Future {
    confucius.live
  }
  val socratesLive = Future {
    socrates.live
  }
  val machiavelliLive = Future {
    machiavelli.live
  }
}