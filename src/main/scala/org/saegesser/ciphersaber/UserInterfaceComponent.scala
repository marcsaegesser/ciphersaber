package org.saegesser.ciphersaber

trait UserInterfaceComponent {
  val userInterface: UserInterface

  trait UserInterface {
    def run(args: Array[String])
  }
}
