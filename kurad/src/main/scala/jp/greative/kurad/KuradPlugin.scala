package jp.greative.kurad

import sbt._
import Keys._
import jp.greative.kurad.app.controller.{DeleteAllController, AdminController, TemplateController, CrudController}
import jp.greative.kurad.app.model.mode.Mode
import sbt.complete.Parser

object KuradPlugin extends Plugin {

  private[this] val modeList = Mode.values.filterNot(_ == Mode.NONE).map(_.name.toLowerCase)

  private[this] val kuradParser: Parser[Either[(Mode, String), String]] = {
    import sbt.complete.DefaultParsers._
    val modes = modeList.map(m => token(m).map(Mode.is)).reduce(_ | _)
    val modeParser = (Space ~> modes) ~ (Space ~> token(NotSpace, "<model-name>"))
    val other = Space ~> (token("template") | token("admin") | token("deleteAll"))
    modeParser || other
  }

  /**
   * opening kurad
   * @return
   */
  def kuradBasic = Command(
    "kurad", ("kurad", modeList.mkString("<",",","> <MODEL_NAME>")),
    "make controller and views for model"
  )(_ => kuradParser){ (state, args) =>
      println("")
      println("==============================================================")
      println("")
      println(Colors.green("       {{{{ ") + Colors.yellow("kurad - CRUD tool for web application "))
      println("")
      println("==============================================================")
      println("")

      args match {
        case Left((mode, model)) => {
          println( doKurad(mode, model) )
        }
        case Right(mode @ "template")=> {
          println( Colors.cyan("[mode]   ") + mode )
          println("--------------------------------------------------------------")
          println( TemplateController.apply() )
        }
        case Right(mode @ "admin") => {
          println( Colors.cyan("[mode]   ") + mode )
          println("--------------------------------------------------------------")
          println( AdminController.apply() )
        }
        case Right(mode @ "deleteAll") => {
          println( Colors.cyan("[mode]   ") + mode )
          println("--------------------------------------------------------------")
          println( DeleteAllController.apply() )
        }
        case _ => {
          println("Kurad tool invalid args.")
        }
      }

      state
  }

  /**
   * do kurad program
   */
  def doKurad(mode: Mode, modelName: String): String = {
    println(Colors.cyan("[mode]   ") + mode)
    println(Colors.cyan("[model]  ") + modelName)
    println("--------------------------------------------------------------")
    new CrudController().apply(mode, modelName)
  }

  /**
   * add setting
   * @return
   */
  override def settings: scala.Seq[Setting[_]] = super.settings ++ Seq(
    commands ++= Seq(kuradBasic)
  )

}

/**
 * Terminal color
 */
object Colors {

  import scala.Console._

  lazy val isANSISupported = {
    Option(System.getProperty("sbt.log.noformat")).map(_ != "true").orElse {
      Option(System.getProperty("os.name"))
        .map(_.toLowerCase)
        .filter(_.contains("windows"))
        .map(_ => false)
    }.getOrElse(true)
  }

  def red(str: String): String = if (isANSISupported) (RED + str + RESET) else str
  def blue(str: String): String = if (isANSISupported) (BLUE + str + RESET) else str
  def cyan(str: String): String = if (isANSISupported) (CYAN + str + RESET) else str
  def green(str: String): String = if (isANSISupported) (GREEN + str + RESET) else str
  def magenta(str: String): String = if (isANSISupported) (MAGENTA + str + RESET) else str
  def white(str: String): String = if (isANSISupported) (WHITE + str + RESET) else str
  def black(str: String): String = if (isANSISupported) (BLACK + str + RESET) else str
  def yellow(str: String): String = if (isANSISupported) (YELLOW + str + RESET) else str

}
