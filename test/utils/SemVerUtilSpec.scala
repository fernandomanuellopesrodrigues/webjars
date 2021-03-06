package utils

import org.specs2.mutable._

class SemVerUtilSpec extends Specification {

  // SemVer Docs: https://github.com/npm/node-semver

  "SemVerUtil.convertSemVerToMaven" should {
    "work with static versions" in {
      SemVerUtil.convertSemVerToMaven("1.2.3")       must be equalTo Some("1.2.3")
      SemVerUtil.convertSemVerToMaven("=1.2.3")      must be equalTo Some("1.2.3")
      SemVerUtil.convertSemVerToMaven("1.2.3-alpha") must be equalTo Some("1.2.3-alpha")
    }
    "work with basic ranges" in {
      SemVerUtil.convertSemVerToMaven(">1")           must be equalTo Some("(1,)")
      SemVerUtil.convertSemVerToMaven("> 1")          must be equalTo Some("(1,)")
      SemVerUtil.convertSemVerToMaven(">1.2")         must be equalTo Some("(1.2,)")
      SemVerUtil.convertSemVerToMaven(">1.2.3")       must be equalTo Some("(1.2.3,)")
      SemVerUtil.convertSemVerToMaven(">1.2.3-alpha") must be equalTo Some("(1.2.3-alpha,)")

      SemVerUtil.convertSemVerToMaven(">=1")           must be equalTo Some("[1,)")
      SemVerUtil.convertSemVerToMaven(">= 1")          must be equalTo Some("[1,)")
      SemVerUtil.convertSemVerToMaven(">=1.2")         must be equalTo Some("[1.2,)")
      SemVerUtil.convertSemVerToMaven(">=1.2.3")       must be equalTo Some("[1.2.3,)")
      SemVerUtil.convertSemVerToMaven(">=1.2.3-alpha") must be equalTo Some("[1.2.3-alpha,)")

      SemVerUtil.convertSemVerToMaven("<1")           must be equalTo Some("(,1)")
      SemVerUtil.convertSemVerToMaven("< 1")          must be equalTo Some("(,1)")
      SemVerUtil.convertSemVerToMaven("<1.2")         must be equalTo Some("(,1.2)")
      SemVerUtil.convertSemVerToMaven("<1.2.3")       must be equalTo Some("(,1.2.3)")
      SemVerUtil.convertSemVerToMaven("<1.2.3-alpha") must be equalTo Some("(,1.2.3-alpha)")

      SemVerUtil.convertSemVerToMaven("<=1")           must be equalTo Some("(,1]")
      SemVerUtil.convertSemVerToMaven("<= 1")          must be equalTo Some("(,1]")
      SemVerUtil.convertSemVerToMaven("<=1.2")         must be equalTo Some("(,1.2]")
      SemVerUtil.convertSemVerToMaven("<=1.2.3")       must be equalTo Some("(,1.2.3]")
      SemVerUtil.convertSemVerToMaven("<=1.2.3-alpha") must be equalTo Some("(,1.2.3-alpha]")
    }
    "work with range sets" in {
      SemVerUtil.convertSemVerToMaven(">=1.0.0 <1.4.0")  must be equalTo Some("[1.0.0,1.4.0)")
      SemVerUtil.convertSemVerToMaven(">= 0.10.1 < 0.12.0")  must be equalTo Some("[0.10.1,0.12.0)")
    }
    "work with hyphen ranges" in {
      SemVerUtil.convertSemVerToMaven("1 - 2")           must be equalTo Some("[1,2]")
      SemVerUtil.convertSemVerToMaven("1 - 2.4")         must be equalTo Some("[1,2.4]")
      SemVerUtil.convertSemVerToMaven("1 - 2.3.4")       must be equalTo Some("[1,2.3.4]")
      SemVerUtil.convertSemVerToMaven("1 - 2.3.4-alpha") must be equalTo Some("[1,2.3.4-alpha]")

      SemVerUtil.convertSemVerToMaven("1.2 - 2")           must be equalTo Some("[1.2,2]")
      SemVerUtil.convertSemVerToMaven("1.2 - 1.4")         must be equalTo Some("[1.2,1.4]")
      SemVerUtil.convertSemVerToMaven("1.2 - 1.2.3")       must be equalTo Some("[1.2,1.2.3]")
      SemVerUtil.convertSemVerToMaven("1.2 - 1.2.3-alpha") must be equalTo Some("[1.2,1.2.3-alpha]")

      SemVerUtil.convertSemVerToMaven("1.2.3 - 2")           must be equalTo Some("[1.2.3,2]")
      SemVerUtil.convertSemVerToMaven("1.2.3 - 1.4")         must be equalTo Some("[1.2.3,1.4]")
      SemVerUtil.convertSemVerToMaven("1.2.3 - 1.3.4")       must be equalTo Some("[1.2.3,1.3.4]")
      SemVerUtil.convertSemVerToMaven("1.2.3 - 1.3.4-alpha") must be equalTo Some("[1.2.3,1.3.4-alpha]")

      SemVerUtil.convertSemVerToMaven("1.2.3-alpha - 2")           must be equalTo Some("[1.2.3-alpha,2]")
      SemVerUtil.convertSemVerToMaven("1.2.3-alpha - 1.4")         must be equalTo Some("[1.2.3-alpha,1.4]")
      SemVerUtil.convertSemVerToMaven("1.2.3-alpha - 1.3.4")       must be equalTo Some("[1.2.3-alpha,1.3.4]")
      SemVerUtil.convertSemVerToMaven("1.2.3-alpha - 1.3.4-alpha") must be equalTo Some("[1.2.3-alpha,1.3.4-alpha]")
    }
    "work with X ranges" in {
      SemVerUtil.convertSemVerToMaven("")    must be equalTo Some("[0,)")
      SemVerUtil.convertSemVerToMaven("*")   must be equalTo Some("[0,)")
      SemVerUtil.convertSemVerToMaven("1")   must be equalTo Some("[1,2)")
      SemVerUtil.convertSemVerToMaven("1.x") must be equalTo Some("[1,2)")
      SemVerUtil.convertSemVerToMaven("1.X") must be equalTo Some("[1,2)")
      SemVerUtil.convertSemVerToMaven("1.*") must be equalTo Some("[1,2)")

      SemVerUtil.convertSemVerToMaven("1.2")   must be equalTo Some("[1.2,1.3)")
      SemVerUtil.convertSemVerToMaven("1.2.x") must be equalTo Some("[1.2,1.3)")
      SemVerUtil.convertSemVerToMaven("1.2.X") must be equalTo Some("[1.2,1.3)")
      SemVerUtil.convertSemVerToMaven("1.2.*") must be equalTo Some("[1.2,1.3)")
    }
    "work with tilde ranges" in {
      SemVerUtil.convertSemVerToMaven("~1")           must be equalTo Some("[1,2)")
      SemVerUtil.convertSemVerToMaven("~1.2")         must be equalTo Some("[1.2,1.3)")
      SemVerUtil.convertSemVerToMaven("~1.2.3")       must be equalTo Some("[1.2.3,1.3)")
      SemVerUtil.convertSemVerToMaven("~1.2.3-alpha") must be equalTo Some("[1.2.3-alpha,1.3)")

      SemVerUtil.convertSemVerToMaven("~1.x")         must be equalTo Some("[1.0.0,2)")
    }
    "work with caret ranges" in {
      SemVerUtil.convertSemVerToMaven("^1.2.3")        must be equalTo Some("[1.2.3,2)")
      SemVerUtil.convertSemVerToMaven("^1.2.3-beta.2") must be equalTo Some("[1.2.3-beta.2,2)")

      SemVerUtil.convertSemVerToMaven("^0.2.3")        must be equalTo Some("[0.2.3,0.3)")
      SemVerUtil.convertSemVerToMaven("^0.0.3")        must be equalTo Some("[0.0.3,0.0.4)")
      SemVerUtil.convertSemVerToMaven("^0.0.3-beta")   must be equalTo Some("[0.0.3-beta,0.0.4)")

      SemVerUtil.convertSemVerToMaven("^1.2.x") must be equalTo Some("[1.2.0,2)")
      SemVerUtil.convertSemVerToMaven("^0.0.x") must be equalTo Some("[0.0.0,0.1)")
      SemVerUtil.convertSemVerToMaven("^0.0")   must be equalTo Some("[0.0.0,0.1)")

      SemVerUtil.convertSemVerToMaven("^1.x") must be equalTo Some("[1.0.0,2)")
      SemVerUtil.convertSemVerToMaven("^0.x") must be equalTo Some("[0.0.0,1)")
    }
  }

}