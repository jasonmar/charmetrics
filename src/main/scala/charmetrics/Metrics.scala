package charmetrics

import charmetrics.Protocol._

object Metrics {

  def characterFrequency(r: Request): CharacterFrequencyMetrics = {
    if (r.payload.isEmpty) CharacterFrequencyMetrics("","",0)
    else {
      val wordCount = r.payload.split(" ").count(_.nonEmpty)
      val characterCounts = countCharacters(r.payload)
      val maxFrequency = characterCounts.values.max
      val minFrequency = characterCounts.values.min
      val mostFrequentChars = characterCounts.filter(_._2 == maxFrequency).keys.toVector
      val leastFrequentChars = characterCounts.filter(_._2 == minFrequency).keys.toVector.sorted
      val frequentCharPerWordRatio: Float = maxFrequency.toFloat / wordCount.toFloat
      CharacterFrequencyMetrics(mostFrequentChars.head.toString, leastFrequentChars.head.toString, frequentCharPerWordRatio)
    }
  }

  def countCharacters(s: String): Map[Char,Long] = {
    val count = collection.mutable.Map[Char,Long]()
    s.iterator
      .filterNot(_ == ' ')
      .filterNot(_ == '\t')
      .map(_.toLower)
      .foreach{c =>
        count.get(c) match {
          case Some(x) => count.put(c, x + 1)
          case _ => count.put(c, 1)
      }
    }
    count.toMap
  }

}
