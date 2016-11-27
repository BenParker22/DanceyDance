package models

import org.scalajs.dom
import org.scalajs.dom.raw.HTMLImageElement


class Image(src: String) {
  private var ready: Boolean = false

  val element = dom.document.createElement("img").asInstanceOf[HTMLImageElement]
  element.onload = (e: dom.Event) => ready = true
  element.src = src

  def isReady: Boolean = ready
}
