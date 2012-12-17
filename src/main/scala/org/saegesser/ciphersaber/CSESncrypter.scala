package org.saegesser.ciphersaber

import java.io.{InputStream, OutputStream}

trait CSEncryptor {
  def encrypt(in: InputStream, out: OutputStream, key: Array[Byte], n: Int): Unit
  def decrypt(in: InputStream, out: OutputStream, key: Array[Byte], n: Int): Unit
}
