package com.swallowsonny.convertextlibrary

fun String.reversalEvery2Charts(hasSpace: Boolean = false): String {
    val hex = this.addSpaceEvery2Charts()
    return hex.split(" ").reversed().joinToString(if (hasSpace) " " else "")
}

fun String.addSpaceEvery2Charts(): String {
    val hex = this.replace(" ", "")
    val sb = StringBuilder()
    for (i in 0 until hex.length / 2) {
        sb.append(hex.substring(i * 2, i * 2 + 2))
        sb.append(" ")
    }
    return sb.toString().trim()
}

fun String.hex2ByteArray(): ByteArray {
    val s = this.replace(" ", "")
    val bs = ByteArray(s.length/2)
    for (i in 0 until s.length/2){
        bs[i] = s.substring(i*2, i*2+2).toInt(16).toByte()
    }
    return bs
}

fun String.ascii2ByteArray(hasSpace: Boolean = false): ByteArray {
    val s = if(hasSpace) this else this.replace(" ", "")
    return s.toByteArray(charset("US-ASCII"))
}

fun String.addFirst(s: String) = "$s$this"

fun String.addLast(s: String) = "$this$s"