package shanpay.routing

import akka.actor.ActorSystem
import spray.routing.SimpleRoutingApp

object SprayBoot extends App with SimpleRoutingApp {
    
	implicit val system = ActorSystem("my-system")
	
	case class OrderItem(val size : Int, val dangerous : String);
	
	startServer(interface = "localhost", port = 8080) {
	    path("hello") {
	        get {
	            complete {
	                <h1>Say hello to spray</h1>
	            }
	        }
	    } ~
	    path("items") {
	        get {
        		parameters('size.as[Int], 'dangerous ? "no").as(OrderItem) {
	                orderItem => orderItem match {
	                    case OrderItem(a, b) => complete(s"orderItem size[$a], dangerous[$b]")
	                    case _ => complete("unknown")
	                } 
                }
	        }
	    } ~
	    pathPrefix("items" / IntNumber) {
	        orderId => complete(s"items[$orderId]")
	    }
	}
}