import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class BasicSimulation extends Simulation {

  /*	val httpConf = http.baseURL("http://localhost:8080/mock");
    object Query{
      val test = repeat(4, "i") { // Note how we force the counter name so we can reuse it
        exec(http("request ${i}")
          .get("/queryById?id=${i}")
          //.check(status.is(200)).check(jsonPath("$..page").exists))
          .check(status.is(200)));
      }
    }
    val scn = scenario("Users").exec(Query.test);
    setUp(scn.inject(atOnceUsers(500))).protocols(httpConf)*/


  val httpConf1 =http.baseURL("http://www.baidu.com")
  val scn1 = scenario("BaiduSimulation").during(100){
    exec(http("baidu_home").get("/"))
  }
  //设置线程数
  //  setUp(scn.inject(rampUsers(500) over(10 seconds)).protocols(httpConf))
  setUp(scn1.inject(atOnceUsers(10)).protocols(httpConf1))


  val httpConf = http.baseURL("https://192.168.221.190/forum_v7.9.5/forum/club/topiccontent-a2-pm1-v8.1.0-t63744415-o0-p2-s20-c1-nt0-fs0-sp0-al0-cw414-i0-ct1-rct1.json")
  val scn = scenario("tiezi").during(10){
    exec(http("tiezi_home").get("/"))
  }
  //设置线程数
  //  setUp(scn.inject(rampUsers(500) over(10 seconds)).protocols(httpConf))
  setUp(scn.inject(atOnceUsers(10)).protocols(httpConf))
}