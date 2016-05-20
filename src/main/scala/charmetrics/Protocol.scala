package charmetrics

import spray.json._

object Protocol {
  case class Request(id: Long, payload: String, time: Long)
  case class CharacterFrequencyMetrics(most_frequent_char: String, least_frequent_char: String, ratio_most_used_char_per_word: Float)

  object Request extends DefaultJsonProtocol {
    implicit val format = jsonFormat3(Request.apply)
  }

  object CharacterFrequencyMetrics extends DefaultJsonProtocol {
    implicit val format = jsonFormat3(CharacterFrequencyMetrics.apply)
  }
}
