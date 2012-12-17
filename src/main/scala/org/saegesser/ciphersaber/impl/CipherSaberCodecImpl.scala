package org.saegesser.ciphersaber.impl

import java.io.{InputStream, OutputStream}
import org.saegesser.ciphersaber.CipherSaberCodecComponent

trait CipherSaberCodecImpl extends CipherSaberCodecComponent {
  class Codec extends CipherSaberCodec {

    def encode(in: InputStream, out: OutputStream, key: Array[Byte], n: Int) = {
      val r = new java.security.SecureRandom

      // 0.  Initialization Vector
      val iv: Array[Byte] = Array.fill(10){0}
      r.nextBytes(iv)
      val kiv = key ++ iv

      // 1.  Key setup
      val s = setupKey(kiv, n)

      // 2. Stream Generation
      out.write(iv)  // Write initialization vector to start of stream
      transform(in, out, 0, 0 , s)
    }

    def decode(in: InputStream, out: OutputStream, key: Array[Byte], n: Int) = {
      // 0.  Initialization Vector
      val iv: Array[Byte] = Array.fill(10){0}
      in.read(iv)
//      val kiv = key ++ iv

      // 1.  Key Setup
      val s = setupKey(key ++ iv, n)

      // 2.  Stream Generation
      transform(in, out, 0, 0, s)
    }

    private def setupKey(key: Array[Byte], n: Int): Array[Byte] = {
      val s = (for(i <- 0 to 255) yield i.toByte).toArray
      val s2 = (for{i <- 0 to 255
                   val k = key(i % key.length)
                 }yield k).toArray
      var j = 0
      for(rep <- 1 to n; i <- 0 to 255) {
        j = toUnsigned((j + s(i) + s2(i)) % 256)
        swap(s, i, j)
      }

      s
    }

    private def transform(in: InputStream, out: OutputStream, i: Int, j: Int, s: Array[Byte]): Unit = {
      in.read match {
        case -1 =>
        case b  =>
          var iNew = toUnsigned((i+1) % 256)
          val jNew = toUnsigned((j + s(iNew)) % 256)
          swap(s, iNew, jNew)
          val t = toUnsigned((s(iNew) + s(jNew)) % 256)
          val K = s(t)
          out.write(b ^ K)
          transform(in, out, iNew, jNew, s)
      }
    }

    def swap(a: Array[Byte], i: Int, j: Int) = {
      val tmp = a(i)
      a(i) = a(j)
      a(j) = tmp
    }

    private def toUnsigned(b: Int): Int = if(b < 0) b + 256 else b
  }
}
