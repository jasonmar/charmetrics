package charmetrics

import akka.actor._
import akka.util.Timeout
import spray.routing._
import scala.concurrent.duration._
import charmetrics.Protocol._
import spray.httpx.SprayJsonSupport

trait RestApi extends HttpService with SprayJsonSupport with ActorLogging {actor: Actor =>
  implicit val timeout = Timeout(10.seconds)

  private def newActor(ctx: RequestContext) = context.actorOf(Props(new ResponseActor(ctx)))

  def routes: Route = {
    pathSingleSlash {
      pathEnd {
        post {
          entity(as[Request]){request => ctx =>
            newActor(ctx) ! request
          }
        }
      }
    } ~
    path(RestPath){id =>
      get {ctx =>
        // echoes the path as response
        newActor(ctx) ! id.toString()
      } ~
      post {
        entity(as[String]){s => ctx =>
          // echoes the post content as response
          newActor(ctx) ! s
        }
      }
    }
  }
}
