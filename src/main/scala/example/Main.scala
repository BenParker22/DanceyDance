package example

import models._

import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom
import org.scalajs.dom.html
import org.scalajs.dom.KeyboardEvent
import org.scalajs.dom.raw.HTMLImageElement


@JSExport
object Main {
  @JSExport
  def main(canvas: html.Canvas): Unit = {
    val ctx = canvas.getContext("2d")
      .asInstanceOf[dom.CanvasRenderingContext2D]

    dom.document.body.appendChild(canvas)

    val tileWidth = 100
    val boardWidth = 8
    val boardHeight = 6
    val boardArea = boardWidth * boardHeight
    val startingPosition = 37
    val dancerImage = new Image("images/dancer.png")
    val creepImage = new Image("images/creep.png")
    val coolDudeImage = new Image("images/coolDude.png")

    var currentBoard = Board((0 until boardArea).toList.map(position => ColourTile(White, position)),
      PlayerTile(startingPosition))

    def drawBoard(board: Board) = {
      val player = board.player
      board.tiles.foreach {
        case tile: ColourTile => {

          ctx.fillStyle = matchColourToRGB(tile.colour)
          ctx.fillRect(
            (tile.position % boardWidth) * tileWidth,
            (tile.position / boardWidth) * tileWidth,
            tileWidth,
            tileWidth
          )
        }}

      if (dancerImage.isReady){
        ctx.drawImage(dancerImage.element, (player.position % boardWidth) * tileWidth, (player.position / boardWidth) * tileWidth)
      }

    }

    def matchColourToRGB(colour: TileColour) = colour match {
      case Blue => "rgb(0, 0, 255)"
      case White => "rgb(255,250,250)"
      case Yellow => "rgb(255,255,0)"
      case Green => "rgb(154,205,50)"
      case Red => "rgb(255, 0, 0)"
      case Black => "rgb(0, 0, 0)"
      case _ => "rgb(0, 0, 0)"
    }

    KeyboardEvent
    dom.window.onkeypress = {e: dom.KeyboardEvent => {
      currentBoard = BoardCalculator.calculateNextBoard(currentBoard, matchKeyCodeToAction(e.keyCode))
      drawBoard(currentBoard)}}

    def matchKeyCodeToAction(keyCode: Int) = keyCode match {
      case 119 => MoveUp
      case 97 => MoveLeft
      case 115 => MoveDown
      case 100 => MoveRight
      case _ => Dance
    }
  }

}
