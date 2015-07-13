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
 * demo boot
 */
object SprayBoot extends App {
    
	implicit val system = ActorSystem("spray-demo")
	
	val server = system.actorOf(Props[DemoServiceActor], "demoActor")
	
	val webConfig = system.settings.config.getConfig("spray.demo.web")
	
	IO(Http) ! Http.Bind(server, webConfig.getString("interface"), port = webConfig.getInt("port"))
}