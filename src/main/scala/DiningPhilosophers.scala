import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future}
trait Philosopher {
  val name: String

  final def takeLeft: Unit = println(s"$name is take left fork!")

  final def takeRight: Unit = println(s"$name is take Right fork!")

  final def live: Unit = {
    takeLeft

    {
      takeRight

      {
        eat
      }
    }
  }

  final def eat: Unit = println(s"$name is eating...")
}

class Aristotle extends Philosopher {
  override val name: String = "Aristotle"
}

class Plato extends Philosopher {
  override val name: String = "Plato"
}

class Confucius extends Philosopher {
  override val name: String = "Confucius"
}

class Socrates extends Philosopher {
  override val name: String = "Socrates"
}

class Machiavelli extends Philosopher {
  override val name: String = "Machiavelli"
}

object Main extends App {
  final val fork_1 = 1
  final val fork_2 = 2
  final val fork_3 = 3
  final val fork_4 = 4
  final val fork_5 = 5
  val aristotle = new Aristotle
  val plato = new Plato
  val confucius = new Confucius
  val socrates = new Socrates
  val machiavelli = new Machiavelli

  val aristotleLive = Future {
    fork_1.synchronized{
      fork_2.synchronized(aristotle.live)
    }
  }

  val platoLive = Future {
    fork_2.synchronized {
      fork_3.synchronized(plato.live)
    }
  }

  val confuciusLive = Future {
    fork_3.synchronized {
      fork_4.synchronized(confucius.live)
    }
  }

  val socratesLive = Future {
    fork_4.synchronized {
      fork_5.synchronized(socrates.live)
    }
  }

  val machiavelliLive = Future {
    fork_5.synchronized {
      fork_1.synchronized(machiavelli.live)
    }
  }
}