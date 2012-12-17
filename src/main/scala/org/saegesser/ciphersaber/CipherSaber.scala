package org.saegesser.ciphersaber

import org.saegesser.ciphersaber.impl._

object CipherSaber {
  abstract class MyComponents extends CommandLineUserInterface with CipherSaberCodecImpl

  val myComponents = new MyComponents {
    val userInterface = new CLI
    val codec = new Codec
  }

  def main(args: Array[String]) = {
    myComponents.userInterface.run(args)
  }
}
