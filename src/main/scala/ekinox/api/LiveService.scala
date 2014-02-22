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
//
//              val events = TableQuery[LiveEvents]
//
//              Database.forURL("jdbc:h2:mem:test1", driver = "org.h2.Driver") withSession {
//                implicit session =>
//                // <- write queries here
//                  val q2 = for {
//                    c <- events
//                 } yield (c.contenu)
//
//              }
//
//              val events = TableQuery[LiveEvents]
//            Database.forName("default") withSession {
//              implicit session =>
//              events.length.toString()
//
//              LiveEvents.countDistinct
              val events = TableQuery[LiveEvents]
              val result = Database.forURL("jdbc:mysql://localhost/ogcnice", user = "root",  driver = "com.mysql.jdbc.Driver") withSession {
                implicit session =>

                  events foreach { case (id, rencontre_id, contenu) =>
                        println("  " + id + "\t" + rencontre_id + "\t" + contenu)
                        LiveEvent(id, rencontre_id, contenu)
                      }

//                  events.filter(_.rencontre_id === 26).map(_.contenu).foreach( q => print(q) )
              }

               result
//             List(LiveEvent(1,2,"contenu"))

            }

        }
      }

}