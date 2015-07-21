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
	
    private implicit val timeout : Timeout = 15.seconds
    
    implicit val system = ActorSystem()
    import system.dispatcher

    val response: Future[HttpResponse] = (IO(Http) ? HttpRequest(POST, Uri("http://localhost:8080/item/add"), entity = HttpEntity(MediaTypes.`application/x-www-form-urlencoded`, "itemName=test&itemNo=1000"))).mapTo[HttpResponse]
    
    response onComplete {
        case Success(httpResponse) => 
            println(httpResponse.entity.data.asString)
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