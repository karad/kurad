package jp.greative.kurad

import sbt._
import Keys._
import jp.greative.kurad.app.controller.{AdminController, TemplateController, CrudController}

object KuradPlugin extends Plugin {

  /**
   * opening kurad
   * @return
   */
  def kuradBasic = Command.args(
    "kurad",
    "<all,service,controller,view,route,other> <MODEL_NAME>",
    Help("kurad", ("kurad <all,service,controller,view,route,other> <MODEL_NAME>" , "make controller and views for model"), "make controller and views for model")) { (state:State, args) =>
      println("")
      println("==============================================================")
      println("")
      println(Colors.green("       {{{{ ") + Colors.yellow("kurad - CRUD tool for web application "))
      println("")
      println("==============================================================")
      println("")

      (args, args.headOption) match {
        case (v, mode:Some[String]) if v.size >= 2  => {
          println( doKurad(mode, v.tail.headOption).getOrElse("") )
        }
        case (v, mode:Some[String]) if v.size == 1 && mode.getOrElse("").equals("template") => {
          println( Colors.cyan("[mode]   ") + mode.getOrElse("") )
          println("--------------------------------------------------------------")
          println( TemplateController.apply() )
        }
        case (v, mode:Some[String]) if v.size == 1 && mode.getOrElse("").equals("admin") => {
          println( Colors.cyan("[mode]   ") + mode.getOrElse("") )
          println("--------------------------------------------------------------")
          println( AdminController.apply() )
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
  def doKurad(mode: Option[String], modelName: Option[String]): Option[String] = {
    (mode, modelName) match {
      case (m:Some[_], n:Some[_]) => {
        println(Colors.cyan("[mode]   ") + m.get)
        println(Colors.cyan("[model]  ") + n.get)
        println("--------------------------------------------------------------")
        Option(new CrudController().apply(m.get, n.get))
      }
      case _ => scala.None
    }
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