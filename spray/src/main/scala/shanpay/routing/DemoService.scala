/**
 *
 */
package shanpay.routing

import akka.actor.Actor
import spray.httpx.marshalling.ToResponseMarshallable.isMarshallable
import spray.routing.Directive.pimpApply
import spray.routing.HListDeserializer.hld2
import spray.routing.HttpService
import spray.routing.directives.FieldDefMagnet.apply
import spray.routing.directives.ParamDefMagnet.apply

/**
 * @author Zoro
 * DemoService, support CRUD operation
 */

class DemoServiceActor extends Actor with DemoService {
    
    def actorRefFactory = context
    
    def receive = runRoute(route)
}

case class Item(val itemName : String, val itemNo : Int)
    
trait DemoService extends HttpService {
    
    val route = {
        //query
        path("item" / IntNumber) {
            itemNo => 
                get{
	                complete {
	                    s"itemNo[$itemNo]"
	                }
                }
        } ~
        path("item" / Rest / IntNumber) {
           (itemName, itemNo) => 
               get {
                   complete {
                       s"itemName[$itemName], itemNo[$itemNo]"
                   }
               }
        } ~
        pathPrefix("item") {
            path("add") {
                post {
                    formFields('itemName.as[String], 'itemNo.as[Int]).as(Item) {
                        case Item(itemName, itemNo) =>
                            complete {
	                            s"itemName[$itemName] && itemNo[$itemNo] has add."
	                        }
                        case _ => complete {
                            "unknown type."
                        }
                    }
                }
            } ~
	        path("add") {
	            get {
	                parameters('itemName.as[String], 'itemNo.as[Int]).as(Item) {
                    	case Item(itemName, itemNo) =>
	                        complete {
	                            s"itemName[$itemName], itemNo[$itemNo] has add"
	                        }
                    	case _ => complete("unknown type.")
	                }
	            }
	        } ~
	        path("del" / Rest) {
	            itemName => 
	                get {
	                    complete {
	                        s"item[$itemName] has delete."
	                    }
	                }
	        }
        }
    } 
}