package ekinox.api

import scala.slick.driver.MySQLDriver.simple._


case class LiveEvent (id: Int,
                       rencontre_id: Int,
                       contenu: String,
                         equipe_id: Int
                       //                       composition_id: Option[Long],
//                       composition_id_associe: Option[Long],
//                       date: Option[java.util.Date],
//                       minute: Option[Long],
//                       couleur_id: Option[Long],
//                       etat_publication: String,
//                       discr: String,
//                       type_id: Option[Long],
//                       pied_id: Option[Long]
                       )

//class LiveEvents(tag: Tag) extends Table[(Long, Int, Long, Long, Long, String , Long, Long, String, String, Long, Long)](tag, "LIVEEVENT") {
 class LiveEvents(tag: Tag) extends Table[LiveEvent](tag, "LiveEvent") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def rencontre_id = column[Int]("rencontre_id")
  def contenu = column[String]("contenu")
  def equipe_id = column[Int]("equipe_id")
//  def composition_id = column[Long]("composition_id")
//  def composition_id_associe = column[Long]("composition_id")
//    def date = column[java.util.Date]("date", O.Nullable)
//    def minute = column[Long]("minute")
//    def couleur_id = column[Long]("couleur_id")
//    def etat_publication = column[String]("etat_publication")
//    def discr = column[String]("discr")
//    def type_id = column[Long]("type_id")
//    def pied_id = column[Long]("pied_id")
   def * = (id, rencontre_id, contenu, equipe_id) <> (LiveEvent.tupled, LiveEvent.unapply)
   def equipe = foreignKey("SUP_FK", equipe_id, TableQuery[Equipes])(_.id)

}
