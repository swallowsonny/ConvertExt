package com.swallowsonny.convertext

import com.swallowsonny.convertextlibrary.*

object Test {
    @JvmStatic
    fun main(args: Array<String>) {

        var ba = byteArrayOf(0x00, 0x0a, 0xff.toByte(), 0xf6.toByte(), 0x35, 0x33, 0x38, 0x03)


//        println(ba.readInt8()) // 0
//        println(ba.readUInt8()) // 0
//        println("-----")
//        println(ba.readUInt16BE())
//        println(ba.readUInt16BE(2))
//        println(ba.readInt16BE())
//        println(ba.readInt16BE(2))
//        println("-----")
//        println(ba.readUInt16LE())
//        println(ba.readUInt16LE(2))
//        println(ba.readInt16LE())
//        println(ba.readInt16LE(2))
//        println("-----")
//        println(ba.readUInt32BE())
//        println(ba.readUInt32BE(2))
//        println(ba.readInt32BE())
//        println(ba.readInt32BE(2))
//        println("-----")
//        println(ba.readUInt32LE())
//        println(ba.readUInt32LE(2))
//        println(ba.readInt32LE())
//        println(ba.readInt32LE(2))
//        println("-----")
//        println(ba.readStringBE(0, 4))
//        println(ba.readStringBE(4, 4, "ascii"))
//        println("-----")
//        println(ba.readStringLE(0, 4))
//        println(ba.readStringLE(4, 4, "ascii"))
//        println("-----")
//        println(ba.readFloatBE(0))
//        println(ba.readFloatLE(0))
//        println(ba.readFloatBE(3))
//        println(ba.readFloatLE(3))

//        ba = ba.insertByteArrayBE(byteArrayOf(0x11, 0x22, 0x33))
//        ba = ba.insertByteArrayLE(byteArrayOf(0x11, 0x22, 0x33), 3)

        ba.writeStringBE("11 22 33")
        ba.writeStringLE("3.1", 3, "ascii")
        val str = ba.toHexString()
        str.hex2ByteArray()
        println(ba.toHexString())

    }

}