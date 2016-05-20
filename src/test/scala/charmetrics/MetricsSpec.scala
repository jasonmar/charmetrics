package charmetrics

import charmetrics.Protocol._

class MetricsSpec extends UnitSpec {
  def fixture = new {
    val request = Request(id=1, payload="the eye", time=1458853122409L)
    val response = CharacterFrequencyMetrics("e","t",1.5f)
  }

  def fixture2 = new {
    val request = Request(id=2, payload="    11111!    22\t\t22    ", time=1458853122410L)
    val response = CharacterFrequencyMetrics("1","!",2.5f)
  }

  "CharMetrics" should "count characters" in {
    val f = fixture
    val counts = Metrics.countCharacters(f.request.payload)
    counts.get('e') should be (Some(3))
    counts.get('t') should be (Some(1))
    counts.get('h') should be (Some(1))
    counts.get('y') should be (Some(1))
  }

  it should "identify most and least frequent characters" in {
    val f = fixture
    val freq = Metrics.characterFrequency(f.request)
    freq.most_frequent_char should be ("e")
    freq.least_frequent_char should be ("h")
    freq.ratio_most_used_char_per_word should be (1.5)
  }

  it should "handle numeric and symbolic payload" in {
    val f = fixture2
    val freq = Metrics.characterFrequency(f.request)
    freq.most_frequent_char should be ("1")
    freq.least_frequent_char should be ("!")
    freq.ratio_most_used_char_per_word should be (2.5)
  }

  it should "handle empty payload" in {
    val r = Request(3,"",1)
    Metrics.characterFrequency(r) should be (CharacterFrequencyMetrics("","",0))
  }

  it should "treat capital characters as lowercase" in {
    val r = Request(4,"X",2)
    Metrics.characterFrequency(r) should be (CharacterFrequencyMetrics("x","x",1))
  }
}
