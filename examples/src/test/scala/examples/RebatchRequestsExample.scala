import org.junit.Test
import org.scalatest.junit.JUnitSuite
import rx.lang.scala._

class RebatchRequestsExample extends JUnitSuite {

  @Test def rebatchRequestsExample(): Unit = {
    val o = (1 to 100).toObservable
    o.doOnRequest(r => println(s"Requesting $r via rebatchRequests"))
      .rebatchRequests(10)
      .doOnRequest(r => println(s"Requesting $r via Subscriber"))
      .subscribe(new Subscriber[Int]() {
        override def onStart(): Unit = request(1)

        override def onNext(value: Int): Unit = {
          println(s"Receive $value")
          request(1)
        }

        override def onError(error: Throwable): Unit = error.printStackTrace()

        override def onCompleted(): Unit = println("Done")
      })
  }

}


