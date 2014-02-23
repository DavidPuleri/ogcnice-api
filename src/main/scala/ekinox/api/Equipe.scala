package ekinox.api

import scala.slick.driver.MySQLDriver.simple._

case class Equipe (
                    id: Int,
                    nom: String,
                    nom_court: String
                    )

class Equipes(tag: Tag) extends Table[Equipe](tag, "Equipe") {

  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def nom = column[String]("nom")
  def nom_court = column[String]("nom_court")

  def * = (id, nom, nom_court) <> (Equipe.tupled, Equipe.unapply)
}
