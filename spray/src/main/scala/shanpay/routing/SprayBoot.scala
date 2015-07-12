/**
 *
 */
package shanpay.routing

import akka.actor.ActorSystem
import akka.actor.Props
import spray.can.Http
import akka.io.IO

/**
 * @author Zoro
 *
 */
object SprayBoot extends App {
    
	implicit val system = ActorSystem("spray-demo")
	
	val server = system.actorOf(Props[ItemServiceActor], "item-actor")
	
	val webConfig = system.settings.config.getConfig("spray.web")
	
	IO(Http) ! Http.Bind(server, webConfig.getString("interface"), port = webConfig.getInt("port"))
}