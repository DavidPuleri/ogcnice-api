   package ekinox.api

import akka.actor.Actor
import spray.routing._
import scala.slick.driver.MySQLDriver.simple._
import spray.httpx.SprayJsonSupport
import spray.json.DefaultJsonProtocol
   import spray.http.HttpHeaders.RawHeader


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

  implicit val element = jsonFormat4(LiveEvent)
  implicit val elementEquipe = jsonFormat3(Equipe)
  val origin = "*"
  def fromObjectCross(origin: String) = respondWithHeader(RawHeader("Access-Control-Allow-Origin", origin))



  val myRoute =
      path("") {

        get {
          fromObjectCross(origin){
            complete {

              val result = Database.forURL("jdbc:mysql://localhost/ogcnice", user = "root",  driver = "com.mysql.jdbc.Driver") withSession {
                implicit session =>

                val events = TableQuery[LiveEvents]
                val filterQuery: Query[LiveEvents, LiveEvent] = events.filter(_.rencontre_id === 26)




                      val q3 = for {
                        c <- events if c.rencontre_id === 26
                        s <- c.equipe
                      } yield ((c.contenu, s.nom))

                  q3.list()
//
              }
            result.toList
          }

          }
        }

      }

}