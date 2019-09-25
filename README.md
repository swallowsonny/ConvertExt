> 依据node.js中[Buffer](http://nodejs.cn/api/buffer.html)的API编写的ByteArray扩展工具类，解决ByteArray与基本类型转换之间的符号及大小端问题
###### ByteArray 转换 hex字符串
```
fun ByteArray.toHexString(hasSpace: Boolean = true): String
fun ByteArray.toAsciiString(hasSpace: Boolean = false)：String
```
###### ByteArray 与 Int 相互转换 
```
// 读
fun ByteArray.readInt8(offset: Int = 0): Int
fun ByteArray.readUInt8(offset: Int = 0): Int

fun ByteArray.readInt16BE(offset: Int = 0): Int
fun ByteArray.readUInt16BE(offset: Int = 0): Int
fun ByteArray.readInt16LE(offset: Int = 0): Int
fun ByteArray.readUInt16LE(offset: Int = 0): Int

fun ByteArray.readInt32BE(offset: Int = 0): Int
fun ByteArray.readUInt32BE(offset: Int = 0): Long
fun ByteArray.readInt32LE(offset: Int = 0): Int
fun ByteArray.readUInt32LE(offset: Int = 0): Long

// 写
fun ByteArray.writeInt8(value: Int, offset: Int = 0): ByteArray

fun ByteArray.writeInt16BE(value: Int, offset: Int = 0): ByteArray
fun ByteArray.writeInt16LE(value: Int, offset: Int = 0): ByteArray

fun ByteArray.writeInt32BE(value: Int, offset: Int = 0): ByteArray
fun ByteArray.writeInt32LE(value: Int, offset: Int = 0): ByteArray
```
###### ByteArray 与 Float 相互转换 
```
// 读
fun ByteArray.readFloatBE(offset: Int = 0): Float
fun ByteArray.readFloatLE(offset: Int = 0): Float
// 写
fun ByteArray.writeFloatBE(value: Float, offset: Int = 0): ByteArray
fun ByteArray.writeFloatLE(value: Float, offset: Int = 0): ByteArray
```
###### ByteArray 与 时间(4个字节) 相互转换 
```
// 读 
fun ByteArray.readTimeBE(offset: Int = 0, pattern: String = "yyyy-MM-dd HH:mm:ss"): String
fun ByteArray.readTimeLE(offset: Int = 0, pattern: String = "yyyy-MM-dd HH:mm:ss"): String
// 写
fun ByteArray.writeTimeBE(time: String, offset: Int = 0, pattern: String = "yyyy-MM-dd HH:mm:ss"): ByteArray
fun ByteArray.writeTimeLE(time: String, offset: Int = 0, pattern: String = "yyyy-MM-dd HH:mm:ss"): ByteArray
```
###### ByteArray 与 Hex / ASCII 相互转换 
```
// 读 encoding = hex / ascii
fun ByteArray.readStringBE(
    offset: Int,
    byteLength: Int,
    encoding: String = "hex",
    hasSpace: Boolean = encoding.toLowerCase() == "hex"
): String
fun ByteArray.readStringLE(
    offset: Int,
    byteLength: Int,
    encoding: String = "hex",
    hasSpace: Boolean = encoding.toLowerCase() == "hex"
): String

// 写
fun ByteArray.writeStringBE(str: String, offset: Int = 0, encoding: String = "hex"): ByteArray
fun ByteArray.writeStringLE(str: String, offset: Int = 0, encoding: String = "hex"): ByteArray
fun ByteArray.writeStringBE(str: String, offset: Int, length: Int, encoding: String = "hex"): ByteArray
fun ByteArray.writeStringLE(str: String, offset: Int, length: Int, encoding: String = "hex"): ByteArray
```
###### ByteArray 读取、替换 与 插入
```
// 读取
fun ByteArray.readByteArrayBE(offset: Int, byteLength: Int): ByteArray
fun ByteArray.readByteArrayLE(offset: Int, byteLength: Int): ByteArray
// 替换指定位置
fun ByteArray.writeByteArrayBE(byteArray: ByteArray, offset: Int = 0, length: Int = byteArray.size): ByteArray
fun ByteArray.writeByteArrayLE(byteArray: ByteArray, offset: Int = 0, length: Int = byteArray.size): ByteArray
// 插入
fun ByteArray.insertByteArrayBE(
    insertArray: ByteArray,
    origrinalIndex: Int = 0,
    insertArrayOffset: Int = 0,
    insertArrayLength: Int = insertArray.size - insertArrayOffset
): ByteArray
fun ByteArray.insertByteArrayLE(
    insertArray: ByteArray,
    origrinalIndex: Int = 0,
    insertArrayOffset: Int = 0,
    insertArrayLength: Int = insertArray.size - insertArrayOffset
): ByteArray
```