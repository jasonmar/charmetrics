package charmetrics

import akka.actor.{Actor, ActorLogging, PoisonPill}
import spray.http.StatusCodes
import spray.httpx.SprayJsonSupport
import spray.routing.RequestContext
import charmetrics.Protocol._

class ResponseActor(requestContext: RequestContext) extends Actor with SprayJsonSupport with ActorLogging {

  def receive = {
    case r: Request =>
      val metrics = Metrics.characterFrequency(r)
      requestContext.complete(StatusCodes.OK, metrics)
      self ! PoisonPill
    case s: String =>
      requestContext.complete(StatusCodes.OK, s)
      self ! PoisonPill
    case _ =>
      requestContext.complete(StatusCodes.BadRequest)
  }

}