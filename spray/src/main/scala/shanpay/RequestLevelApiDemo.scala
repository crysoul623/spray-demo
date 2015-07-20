/**
 *
 */
package shanpay

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.Failure
import scala.util.Success

import akka.actor.ActorSystem
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import spray.can.Http
import spray.http._
import spray.http.HttpMethods._

/**
 * @author Zoro
 * request level api demo
 */
object RequestLevelApiDemo extends App {
	
    private implicit val timeout : Timeout = 5.seconds
    
    implicit val system = ActorSystem()
    import system.dispatcher
    
    val response: Future[HttpResponse] = (IO(Http) ? HttpRequest(GET, Uri("http://spray.io"))).mapTo[HttpResponse]
    
    response onComplete {
        case Success(httpResponse) => 
            println(httpResponse.entity.asString)
            shutdown()
        case Failure(exception) => 
            println(exception)
            shutdown()
    }
    
    def shutdown(): Unit = {
    		IO(Http).ask(Http.CloseAll)(1.seconds)
    		system.shutdown()
    }
}