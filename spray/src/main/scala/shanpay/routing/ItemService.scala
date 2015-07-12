/**
 *
 */
package shanpay.routing

import spray.routing.HttpService
import akka.actor.Actor
import spray.httpx.SprayJsonSupport
import spray.json.DefaultJsonProtocol
import spray.httpx.unmarshalling.Unmarshaller
import spray.http.HttpEntity
import spray.http.MediaTypes
import spray.http.MediaType
import spray.http.FormData

/**
 * @author Zoro
 * itemService, support CRUD operation
 */

class ItemServiceActor extends Actor with ItemService {
    
    def actorRefFactory = context
    
    def receive = runRoute(route)
}

case class Item(val name : String, val itemNo : Int)
    
trait ItemService extends HttpService {
    
    val route = {
        path("item" / IntNumber) {
            itemNo => 
                get{
	                complete {
	                    s"itemNo[$itemNo]"
	                }
                }
        } ~
        pathPrefix("item") {
            path("add") {
                post {
                    formFields('name.as[String], 'itemNo.as[Int]) {
                        (itemName, itemNo) =>
	                        complete {
	                            s"""itemName[$itemName] && itemNo[$itemNo] has add."""
	                        }
                    }
                }
            } ~
	        path("add") {
	            get {
	                parameters('name.as[String], 'itemNo.as[Int]).as(Item) {
	                    item => item match {
		                    	case Item(name, itemNo) =>
			                        complete {
			                            s"itemName[$name], itemNo[$itemNo] has add"
			                        }
		                    	case _ => complete("unknown type.")
	                    	}
	                }
	            }
	        } ~
	        path("del" / Rest) {
	            name => 
	                get {
	                    complete {
	                        s"item[$name] has delete."
	                    }
	                }
	        }
        }
    } 
}