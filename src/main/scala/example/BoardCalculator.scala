package example

import models._

import scala.util.Random

case class Board(tiles: List[Tile], player: PlayerTile) {
  def map(f: (Board, GameAction) => Board) = {gameAction: GameAction => f(this, gameAction)}
}

sealed trait GameAction

case object MoveUp extends GameAction
case object MoveDown extends GameAction
case object MoveLeft extends GameAction
case object MoveRight extends GameAction
case object Dance extends GameAction

object BoardCalculator {
  val tileWidth = 100
  val boardWidth = 8
  val boardHeight = 8
  val boardArea = boardWidth * boardHeight
  val startingPosition = 37

  def movePlayer(board: Board, action: GameAction) = {
    def changePlayerPosition(board: Board, positionChange: Int) = {
      val oldPlayerPosition = board.player.position
      val newPlayerPositon = board.player.position + positionChange

      if (newPlayerPositon < 0 || newPlayerPositon >= boardArea) board
      else Board(
        board.tiles.filterNot(tile => tile.position == newPlayerPositon) :+ ColourTile(generateRandomColour, oldPlayerPosition),
        PlayerTile(newPlayerPositon)
      )
    }

    if (action == MoveUp) changePlayerPosition(board, -boardWidth)
    else if (action == MoveDown) changePlayerPosition(board, boardWidth)
    else if (action == MoveLeft) changePlayerPosition(board, -1)
    else if (action == MoveRight) changePlayerPosition(board, 1)
    else board
  }

  def calculateNextBoard(board: Board, buttonPressed: GameAction): Board = {
    board.map(movePlayer)(buttonPressed).map(recolourBoard)(buttonPressed)
  }

  def recolourBoard(board: Board, none: GameAction) = {
    Board(board.tiles.map{
      case tile: ColourTile => ColourTile(generateRandomColour, tile.position)
    }, board.player)
  }

  def generateRandomColour: TileColour = {
    Random.nextInt(5) match {
      case 0 => Blue
      case 1 => Red
      case 2 => Green
      case 3 => Yellow
      case 4 => White
    }
  }
}
