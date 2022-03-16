package com.dummyproject

import java.io.FileInputStream
import java.security.KeyStore

class Abc {

    companion object {
        fun readKeyStore(): KeyStore? {
            val ks: KeyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            // get user password and file input stream
            var password = CharArray(26) { '1' }
            var fis: FileInputStream? = null
            try {
                fis = FileInputStream("storename")
                ks.load(fis, password)
            } finally {
                fis?.close()
            }
            return ks
        }
    }
}