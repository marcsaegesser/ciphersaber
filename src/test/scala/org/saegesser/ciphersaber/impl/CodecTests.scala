package org.saegesser.ciphersaber.impl

import java.io._
import org.scalatest.fixture.WordSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.saegesser.ciphersaber._

class CodecTests extends WordSpec with ShouldMatchers {

  type FixtureParam = CipherSaberCodecComponent

  def withFixture(test: OneArgTest) = {
    class Fixture extends CipherSaberCodecImpl {
      val codec = new Codec
    }

    val fixture = new Fixture
    test(fixture)
  }

  "a codec should" should {
    "return the same value after encode->decode" in 
    { fixture =>
      val key = "Password".getBytes("UTF-8")
      val inputData = "Testing 1, 2, 3".getBytes("UTF-8")
      val in1 = new ByteArrayInputStream(inputData)
      val out1 = new ByteArrayOutputStream()

      fixture.codec.encode(in1, out1, key, 1)
      val cipherText = out1.toByteArray
      
      val in2 = new ByteArrayInputStream(cipherText)
      val out2 = new ByteArrayOutputStream()

      fixture.codec.decode(in2, out2, key, 1)
      val plainText = out2.toByteArray

      cipherText.length should equal (inputData.length + 10)
      plainText should equal (inputData)
    }

    "correctly decrypt cstest1.cs1" in
    { fixture =>
      val expectedString = "This is a test of CipherSaber."
      val cl = getClass.getClassLoader
      val in = new BufferedInputStream(cl.getResourceAsStream("cstest1.cs1"))
      val out = new ByteArrayOutputStream()
      val key = "asdfg".getBytes("UTF-8")

      fixture.codec.decode(in, out, key, 1)

      val outputString = new String(out.toByteArray, "UTF-8")
      
      outputString should equal (expectedString)

     in.close
    }  

    "correctly decrypt cs2test1.cs2" in
    { fixture =>
      val expectedString = "This is a test of CipherSaber-2."
      val cl = getClass.getClassLoader
      val in = new BufferedInputStream(cl.getResourceAsStream("cs2test1.cs2"))
      val out = new ByteArrayOutputStream()
      val key = "asdfg".getBytes("UTF-8")

      fixture.codec.decode(in, out, key, 10)

      val outputString = new String(out.toByteArray, "UTF-8")
      
      outputString should equal (expectedString)

     in.close
    }  
  }
}
