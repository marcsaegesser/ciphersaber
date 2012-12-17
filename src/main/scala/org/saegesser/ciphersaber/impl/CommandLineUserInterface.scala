package org.saegesser.ciphersaber.impl

import java.io.{FileInputStream, BufferedInputStream, FileOutputStream, BufferedOutputStream, BufferedReader, FileReader}
import scopt.immutable._
import org.saegesser.ciphersaber.{UserInterfaceComponent, CipherSaberCodecComponent}

trait CommandLineUserInterface extends UserInterfaceComponent {
  this: CipherSaberCodecComponent =>

  class CLI extends UserInterface {
    trait Operation
    case object Encode extends Operation
    case object Decode extends Operation

    case class Config(inFile: Option[String] = None, outFile: Option[String] = None, keyString: Option[String] = None, keyFile: Option[String] = None, n: Int = 1, op: Operation = Encode)

    def run(args: Array[String]) = {
      val parser = new OptionParser[Config]("CipherSaber", "0.1.0") {
        def options = Seq(
          opt("i", "infile", "input file (default stdin)") { (v: String, c: Config) => c.copy(inFile = Some(v))},
          opt("o", "outfile", "output file (default stdout)") { (v: String, c: Config) => c.copy(outFile = Some(v))},
          opt("k", "key", "A key string.  The key value will be the string converted to bytes using UTF-8 encoding") { (v: String, c: Config) => c.copy(keyString = Some(v))},
          opt("f", "keyFile", "A file containing the binary key.  NOTE:  Either -k or -f must be supplied.  If both are included the value in -k is used.") { (v: String, c: Config) => c.copy(keyFile = Some(v))},
          intOpt("n", "n", "N value (default 1)") { (v: Int, c: Config) => c.copy(n = v)},
          flag("d", "decrypt", "Decrypts the input.  If not specified the default operation is encryption") { c: Config => c.copy(op = Decode) }
        )
      }

      parser.parse(args, Config()) map { config =>
        val in = config.inFile map { f => new BufferedInputStream(new FileInputStream(f)) } getOrElse {new BufferedInputStream(System.in)}
        val out = config.outFile map { f => new BufferedOutputStream(new FileOutputStream(f)) } getOrElse {new BufferedOutputStream(System.out)}
        val key = config.keyString map { _.getBytes("UTF-8")} getOrElse { config.keyFile map {keyFromFile(_)} getOrElse {parser.showUsage; sys.exit} }
      
        config.op match {
          case Encode => codec.encode(in, out, key, config.n)
          case Decode => codec.decode(in, out, key, config.n)
        }

        out.close
        in.close
      }
    }

    private def keyFromFile(fileName: String): Array[Byte] = {
      val reader = new BufferedReader(new FileReader(fileName))
      val key = reader.readLine
      reader.close

      key.getBytes("UTF-8")
    }

  }
}
