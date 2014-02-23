   package ekinox.api

import akka.actor.Actor
import spray.routing._
import scala.slick.driver.MySQLDriver.simple._
import spray.httpx.SprayJsonSupport
import spray.json.DefaultJsonProtocol

class LiveServiceActor extends Actor with LiveService {
// the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(myRoute)
}

trait LiveService extends HttpService  with DefaultJsonProtocol  with SprayJsonSupport  {

  implicit val element = jsonFormat3(LiveEvent)
  val myRoute =
      path("") {

        get {
            complete {

              val events = TableQuery[LiveEvents]
              val result = Database.forURL("jdbc:mysql://localhost/ogcnice", user = "root",  driver = "com.mysql.jdbc.Driver") withSession {
                implicit session =>

              val filterQuery: Query[LiveEvents, LiveEvent] = events.filter(_.rencontre_id === 26)

              filterQuery.list
              }
              result.toList
            }

        }
      }

}