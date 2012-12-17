# CipherSaber — A Scala implementation of CipherSaber-2 #

[CipherSaber](http://ciphersaber.gurus.org/) is a simple encryption mechanism based the
[RC4](http://www.mozilla.org/projects/security/pki/nss/draft-kaukonen-cipher-arcfour-03.txt) algorithm.  CipherSaber
was created by Arnold Reinhold shortly after 9/11 as a political statement about the need for open access to cryptograhy
as a protection from tyrrany.

The ChiperSaber algorithm is easy enough to implement with a small amount of code but provides reasonable amount of 
security.  It is not a replacement for serious cryptography, but it would be sufficient for limited usage in a time
of need.

Arnold urged developers to forge their own CipherSaber implementations and become CipherKnights by decrypting a GIF file
containing a certificate of accomplishment.  

## Build & run ##

```sh
$ cd ciphersaber
$ ./sbt
> run
```

## Usage ##

```
CipherSaber 0.1.0
Usage: CipherSaber [options] 

  -i <value> | --infile <value>
        input file (default stdin)
  -o <value> | --outfile <value>
        output file (default stdout)
  -k <value> | --key <value>
        A key string.  The key value will be the string converted to bytes using UTF-8 encoding
  -f <value> | --keyFile <value>
        A file containing the binary key.  NOTE:  Either -k or -f must be supplied.  If both are included the value in -k is used.
  -n <value> | --n <value>
        N value (default 1)
  -d | --decrypt
        Decrypts the input.  If not specified the default operation is encryption
```


## License ##

Released under the [Apache License](http://www.apache.org/licenses/LICENSE-2.0.html).
