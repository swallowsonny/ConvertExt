package com.swallowsonny.convertextlibrary

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

/**
 * @author swallowsonny
 *
 */

fun ByteArray.toHexString(hasSpace: Boolean = true) = this.joinToString("") {
    (it.toInt() and 0xFF).toString(16).padStart(2, '0').toUpperCase() + if (hasSpace) " " else ""
}

fun ByteArray.toAsciiString(hasSpace: Boolean = false) =
    this.map { it.toChar() }.joinToString(if (hasSpace) " " else "")

fun ByteArray.readInt8(offset: Int = 0): Int {
    throwOffestError(this, offset, 1)
    return this[offset].toInt()
}

fun ByteArray.readUInt8(offset: Int = 0): Int {
    throwOffestError(this, offset, 1)
    return this[offset].toInt() and 0xFF
}

fun ByteArray.readInt16BE(offset: Int = 0): Int {
    throwOffestError(this, offset, 2)
    return (this[offset].toInt() shl 8) + (this[offset + 1].toInt() and 0xFF)
}

fun ByteArray.readUInt16BE(offset: Int = 0): Int {
    throwOffestError(this, offset, 2)
    // 方式一
    return ((this[offset].toInt() and 0xFF) shl 8) or (this[offset + 1].toInt() and 0xFF)
    // 方式二
//    return readUnsigned(this, 2, offset, false).toInt()
}


fun ByteArray.readInt16LE(offset: Int = 0): Int {
    throwOffestError(this, offset, 2)
    return (this[offset + 1].toInt() shl 8) + (this[offset].toInt() and 0xFF)
}

fun ByteArray.readUInt16LE(offset: Int = 0): Int {
    throwOffestError(this, offset, 2)
    return ((this[offset + 1].toInt() and 0xFF) shl 8) or (this[offset].toInt() and 0xFF)
//    return readUnsigned(this, 2, offset, true).toInt()
}

fun ByteArray.readInt32BE(offset: Int = 0): Int {
    throwOffestError(this, offset, 4)
    return (this[offset].toInt()) shl 24 or
            ((this[offset + 1].toInt() and 0xFF) shl 16) or
            ((this[offset + 2].toInt() and 0xFF) shl 8) or
            (this[offset + 3].toInt() and 0xFF)
//    return (this[offset].toInt() shl 24) + (this[offset + 1].toUByte().toInt() shl 16) + (this[offset + 2].toUByte().toInt() shl 8) + this[offset + 3].toUByte().toInt()
}

fun ByteArray.readUInt32BE(offset: Int = 0): Long {
    throwOffestError(this, offset, 4)
    return (((this[offset].toInt() and 0xFF).toLong() shl 24) or
            ((this[offset + 1].toInt() and 0xFF).toLong() shl 16) or
            ((this[offset + 2].toInt() and 0xFF).toLong() shl 8) or
            (this[offset + 3].toInt() and 0xFF).toLong())
//    return readUnsigned(this, 4, offset, false)
}

fun ByteArray.readInt32LE(offset: Int = 0): Int {
    throwOffestError(this, offset, 4)
    return (this[offset + 3].toInt()) shl 24 or
            ((this[offset + 2].toInt() and 0xFF) shl 16) or
            ((this[offset + 1].toInt() and 0xFF) shl 8) or
            (this[offset].toInt() and 0xFF)
//    return (this[offset + 3].toInt() shl 24) + (this[offset + 2].toUByte().toInt() shl 16) + (this[offset + 1].toUByte().toInt() shl 8) + this[offset].toUByte().toInt()
}

fun ByteArray.readUInt32LE(offset: Int = 0): Long {
    throwOffestError(this, offset, 4)
    return (((this[offset + 3].toInt() and 0xFF).toLong() shl 24) or
            ((this[offset + 2].toInt() and 0xFF).toLong() shl 16) or
            ((this[offset + 1].toInt() and 0xFF).toLong() shl 8) or
            (this[offset].toInt() and 0xFF).toLong())
//    return readUnsigned(this, 4, offset, true)
}

// 四字节 float
fun ByteArray.readFloatBE(offset: Int = 0) = java.lang.Float.intBitsToFloat(this.readInt32BE(offset))
// 四字节 float
fun ByteArray.readFloatLE(offset: Int = 0) = java.lang.Float.intBitsToFloat(this.readInt32LE(offset))
//
//// 四字节 时间
@SuppressLint("SimpleDateFormat")
fun ByteArray.readTimeBE(offset: Int = 0, pattern: String = "yyyy-MM-dd HH:mm:ss") =
    SimpleDateFormat(pattern).format(this.readUInt32BE(offset) * 1000)

@SuppressLint("SimpleDateFormat")
fun ByteArray.readTimeLE(offset: Int = 0, pattern: String = "yyyy-MM-dd HH:mm:ss") =
    SimpleDateFormat(pattern).format(this.readUInt32LE(offset) * 1000)

// 读取ByteArray
fun ByteArray.readByteArrayBE(offset: Int, byteLength: Int): ByteArray {
    throwOffestError(this, offset)
    return this.copyOfRange(offset, if ((offset + byteLength) > this.size) this.size else offset + byteLength)
}

fun ByteArray.readByteArrayLE(offset: Int, byteLength: Int): ByteArray {
    throwOffestError(this, offset)
    return this.readByteArrayBE(offset, byteLength).reversedArray()
}

// 读取指定范围输出字符串
fun ByteArray.readStringBE(
    offset: Int,
    byteLength: Int,
    encoding: String = "hex",
    hasSpace: Boolean = encoding.toLowerCase() == "hex"
): String {
    return when (encoding.toLowerCase()) {
        "hex" -> this.readByteArrayBE(offset, byteLength).toHexString(hasSpace)
        "ascii" -> this.readByteArrayBE(offset, byteLength).map { it.toChar() }.joinToString(if (hasSpace) " " else "")
        else -> ""
    }
}

fun ByteArray.readStringLE(
    offset: Int,
    byteLength: Int,
    encoding: String = "hex",
    hasSpace: Boolean = encoding.toLowerCase() == "hex"
): String {
    return when (encoding.toLowerCase()) {
        "hex" -> this.readByteArrayLE(offset, byteLength).toHexString(hasSpace)
        "ascii" -> this.readByteArrayLE(offset, byteLength).map { it.toChar() }.joinToString(if (hasSpace) " " else "")
        else -> ""
    }
}

// ********************************************** 写 **********************************************
fun ByteArray.writeInt8(value: Int, offset: Int = 0): ByteArray {
    throwOffestError(this, offset)
    // 无符号Int 执行写入
    this[offset] = value.toByte()
    return this
}

fun ByteArray.writeInt16BE(value: Int, offset: Int = 0): ByteArray {
    throwOffestError(this, offset, 2)
    // 执行写入
    this[offset] = (value and 0xff00 ushr 8).toByte()
    this[offset + 1] = (value and 0xff).toByte()
    return this
}

fun ByteArray.writeInt16LE(value: Int, offset: Int = 0): ByteArray {
    throwOffestError(this, offset, 2)
    // 无符号Int 执行写入
    this[offset] = (value and 0xff).toByte()
    this[offset + 1] = (value and 0xff00 ushr 8).toByte()
    return this
}

fun ByteArray.writeInt32BE(value: Long, offset: Int = 0): ByteArray {
    throwOffestError(this, offset, 4)
    // 无符号Int 执行写入
    this[offset + 3] = (value and 0xff).toByte()
    this[offset + 2] = (value and 0xff00 ushr 8).toByte()
    this[offset + 1] = (value and 0xff0000 ushr 16).toByte()
    this[offset] = (value and 0xff000000 ushr 24).toByte()
    return this
}

fun ByteArray.writeInt32LE(value: Long, offset: Int = 0): ByteArray {
    throwOffestError(this, offset, 4)
    // 无符号Int 执行写入
    this[offset] = (value and 0xff).toByte()
    this[offset + 1] = (value and 0xff00 ushr 8).toByte()
    this[offset + 2] = (value and 0xff0000 ushr 16).toByte()
    this[offset + 3] = (value and 0xff000000 ushr 24).toByte()
    return this
}

// 写入Float类型
fun ByteArray.writeFloatBE(value: Float, offset: Int = 0): ByteArray {
    throwOffestError(this, offset, 4)
    this.writeInt32BE(java.lang.Float.floatToIntBits(value).toLong(), offset)
    return this
}

fun ByteArray.writeFloatLE(value: Float, offset: Int = 0): ByteArray {
    throwOffestError(this, offset, 4)
    this.writeInt32LE(java.lang.Float.floatToIntBits(value).toLong(), offset)
    return this
}

// 写入时间
@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@SuppressLint("SimpleDateFormat")
fun ByteArray.writeTimeBE(time: String, offset: Int = 0, pattern: String = "yyyy-MM-dd HH:mm:ss"): ByteArray {
    this.writeInt32BE(SimpleDateFormat(pattern).parse(time).time / 1000, offset)
    return this
}

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@SuppressLint("SimpleDateFormat")
fun ByteArray.writeTimeLE(time: String, offset: Int = 0, pattern: String = "yyyy-MM-dd HH:mm:ss"): ByteArray {
    this.writeInt32LE(SimpleDateFormat(pattern).parse(time).time / 1000, offset)
    return this
}

// 指定位置写入ByteArray
fun ByteArray.writeByteArrayBE(byteArray: ByteArray, offset: Int = 0, length: Int = byteArray.size): ByteArray {
    this.writeStringBE(byteArray.toHexString(), offset, length)
    return this
}

fun ByteArray.writeByteArrayLE(byteArray: ByteArray, offset: Int = 0, length: Int = byteArray.size): ByteArray {
    this.writeStringLE(byteArray.toHexString(), offset, length)
    return this
}

// 指定位置插入ByteArray
fun ByteArray.insertByteArrayBE(
    insertArray: ByteArray,
    origrinalIndex: Int = 0,
    insertArrayOffset: Int = 0,
    insertArrayLength: Int = insertArray.size - insertArrayOffset
): ByteArray {
    val byteArrayPre = this.copyOfRange(0, origrinalIndex)
    val byteArrayLast = this.copyOfRange(origrinalIndex, this.size)
    val insertFinalArray = insertArray.copyOfRange(insertArrayOffset, insertArrayOffset + insertArrayLength)
    return byteArrayPre.plus(insertFinalArray).plus(byteArrayLast)
}
fun ByteArray.insertByteArrayLE(
    insertArray: ByteArray,
    origrinalIndex: Int = 0,
    insertArrayOffset: Int = 0,
    insertArrayLength: Int = insertArray.size - insertArrayOffset
): ByteArray {
    insertArray.reverse()
    val byteArrayPre = this.copyOfRange(0, origrinalIndex)
    val byteArrayLast = this.copyOfRange(origrinalIndex, this.size)
    val insertFinalArray = insertArray.copyOfRange(insertArrayOffset, insertArrayOffset + insertArrayLength)
    return byteArrayPre.plus(insertFinalArray).plus(byteArrayLast)
}

// 指定位置写入String
/**
 * @str 写入的字符串
 * @encoding  Hex & ASCII
 */
fun ByteArray.writeStringBE(str: String, offset: Int = 0, encoding: String = "hex"): ByteArray {
    throwOffestError(this, offset)
    when (encoding.toLowerCase()) {
        "hex" -> {
            val hex = str.replace(" ", "")
            throwHexError(hex)
            for (i in 0 until hex.length / 2) {
                if (i + offset < this.size) {
                    this[i + offset] = hex.substring(i * 2, i * 2 + 2).toInt(16).toByte()
                }
            }
        }
        "ascii" -> {
            val hex = str.toCharArray().map { it.toInt() }.map { it.toString(16) }.joinToString("")
            this.writeStringBE(hex, offset, "hex")
        }
    }
    return this
}

// length: 写入长度
fun ByteArray.writeStringLE(str: String, offset: Int = 0, encoding: String = "hex"): ByteArray {
    when (encoding.toLowerCase()) {
        "hex" -> {
            val hex = str.reversalEvery2Charts()
            this.writeStringBE(hex, offset, encoding)
        }
        "ascii" -> {
            val hex = str.toCharArray().map { it.toInt() }.map { it.toString(16) }.joinToString("")
            this.writeStringLE(hex, offset, "hex")
        }
    }
    return this
}

fun ByteArray.writeStringBE(str: String, offset: Int, length: Int, encoding: String = "hex"): ByteArray {
    throwOffestError(this, offset, length)
    when (encoding.toLowerCase()) {
        "hex" -> {
            val hex = str.replace(" ", "").padStart(length * 2, '0').substring(0, length * 2)
            throwHexError(hex)
            this.writeStringBE(hex, offset)
        }
        "ascii" -> {
            val hex = str.toCharArray().map { it.toInt() }.map { it.toString(16) }.joinToString("")
            this.writeStringBE(hex, offset, length, "hex")
        }
    }
    return this
}

fun ByteArray.writeStringLE(str: String, offset: Int, length: Int, encoding: String = "hex"): ByteArray {
    when (encoding.toLowerCase()) {
        "hex" -> {
            val hex = str.reversalEvery2Charts().padEnd(length * 2, '0').substring(0, length * 2)
            this.writeStringBE(hex, offset, length, encoding)
        }
        "ascii" -> {
            val hex = str.toCharArray().map { it.toInt() }.map { it.toString(16) }.joinToString("")
            this.writeStringLE(hex, offset, length, "hex")
        }
    }
    return this
}

// 无符号int
private fun readUnsigned(byteArray: ByteArray, len: Int, offset: Int, littleEndian: Boolean): Long {
    var value = 0L
    for (count in 0 until len) {
        val shift = (if (littleEndian) count else (len - 1 - count)) shl 3
        value = value or (0xff.toLong() shl shift and (byteArray[offset + count].toLong() shl shift))
    }
    return value
}

/****  异常  ****/
private fun throwLenError(byteArray: ByteArray, byteLength: Int) {
    if (byteLength <= 0 || byteLength > 4) throw IllegalArgumentException("The value of \"byteLength\" is out of range. It must be >= 1 and <= 4. Received ${byteLength}")
    if (byteLength > byteArray.size) throw IllegalArgumentException("Attempt to write outside ByteArray bounds.")
}

private fun throwHexError(hex: String) {
    if (hex.length % 2 != 0) throw IllegalArgumentException("The value of \"hex\".length is out of range. It must be an even number")
}

private fun throwOffestError(byteArray: ByteArray, offset: Int, length: Int = 1, byteLength: Int = 0) {
    if (offset > byteArray.size - length - byteLength) throw IllegalArgumentException("The value of \"offset\" is out of range. It must be >= 0 and <= ${byteArray.size - length - byteLength}. Received ${offset}")
}