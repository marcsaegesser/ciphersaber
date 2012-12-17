package org.saegesser.ciphersaber

import java.io.{InputStream, OutputStream}

trait CipherSaberCodecComponent {
  val codec: CipherSaberCodec

  trait CipherSaberCodec {
    def encode(in: InputStream, out: OutputStream, key: Array[Byte], n: Int)
    def decode(in: InputStream, out: OutputStream, key: Array[Byte], n: Int)
  }
}
