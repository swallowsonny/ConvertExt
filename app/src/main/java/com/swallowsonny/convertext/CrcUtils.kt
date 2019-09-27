package com.swallowsonny.convertext

object CrcUtils {
    fun getCrc16Str(arr_buff: ByteArray, little_endian: Boolean = true): ByteArray {
        val len = arr_buff.size
        // 预置 1 个 16 位的寄存器为十六进制FFFF, 称此寄存器为 CRC寄存器。
        var crc = 0xFFFF
        for (i in 0 until len) {
            crc = ((crc and 0xFF00) or (crc and 0x00FF) xor (arr_buff[i].toInt() and 0xFF))
            for (j in 0 until 8) {
                // 把 CRC 寄存器的内容右移一位( 朝低位)用 0 填补最高位, 并检查右移后的移出位
                if ((crc and 0x0001) > 0) {
                    // 如果移出位为 1, CRC寄存器与多项式A001进行异或
                    crc = crc.shr(1)
                    crc = crc xor 0xA001
                } else {
                    // 如果移出位为 0,再次右移一位
                    crc = crc.shr(1)
                }
            }
        }
        val result = ByteArray(2)
        result[if(little_endian) 0 else 1] = (crc.shr(8) and 0xFF).toByte()
        result[if(little_endian) 1 else 0] = (crc and 0xFF).toByte()
        return result
    }
}