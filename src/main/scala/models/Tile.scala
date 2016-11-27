package models

sealed trait Tile {
  val position: Int
}

case class ColourTile(colour: TileColour, position: Int) extends Tile
case class PlayerTile(position: Int) extends Tile

sealed trait TileColour

case object Blue extends TileColour
case object Red extends TileColour
case object Green extends TileColour
case object Yellow extends TileColour
case object White extends TileColour
case object Black extends TileColour