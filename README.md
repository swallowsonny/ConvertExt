> 安卓与硬件通讯过程中(例如：蓝牙，串口等)，经常会遇到ByteArray的解析，故而依据node.js中[Buffer](http://nodejs.cn/api/buffer.html)的API编写了[ByteArray扩展工具类](https://github.com/swallowsonny/ConvertExt/blob/master/convertextlibrary/src/main/java/com/swallowsonny/convertextlibrary/ByteArrayExt.kt)，解决ByteArray与基本类型转换之间的符号及大小端问题，简单高效

### 项目引入

`implementation 'com.swallowsonny:convert-ext:1.0.4'`

### 基本使用
与Node.js中[Buffer](http://nodejs.cn/api/buffer.html)的读写API几乎完全一致

#### `byteArray.toHexString([hasSpace])`

- `hasSpace` **Boolean** 字节间是否需要空格隔开。**默认值:** `true`。
- 返回: **String**

将字节数组转换为16进制字符串。

```kotlin
val ba = byteArrayOf(1, 2, 3, 4)
println(ba.toHexString())         // 01 02 03 04
println(ba.toHexString(false))    // 01020304
```

#### `byteArray.toAsciiString([hasSpace])`

- `hasSpace` **Boolean** 字节间是否需要空格隔开。**默认值:** `false`。
- 返回: **String**

将字节数组转换为ASCII字符串。

```kotlin
val ba = byteArrayOf(0x49 ,0x20 ,0x4C ,0x6F ,0x76 ,0x65 ,0x20 ,0x51 ,0x4D ,0x54)
println(ba.toAsciiString()) // I Love QMT
```

#### `hexString.hex2ByteArray()`

- 返回: **ByteArray**

将16进制字符串转换为字节数组，自动去除空格

```kotlin
val str = "01 02 03 04"
val str1 = "01020304"
println(str.hex2ByteArray())	// byteArrayOf(1, 2, 3, 4)
println(str1.hex2ByteArray())	// byteArrayOf(1, 2, 3, 4)
```

#### `asciiString.ascii2ByteArray([hasSpace])`

- `hasSpace` **Boolean** 是否需要移除空格。**默认值:** `false`。
- 返回: **String**

将ASCII字符串转换为**ByteArray**

```kotlin
val str = "I Love QMT"
println(str.ascii2ByteArray().toHexString())	// 49 4C 6F 76 65 51 4D 54
println(str.ascii2ByteArray().toAsciiString())	// ILoveQMT
println(str.ascii2ByteArray(true).toHexString()) // 49 20 4C 6F 76 65 20 51 4D 54
println(str.ascii2ByteArray(true).toAsciiString())	// I Love QMT
```

#### `byteArray.readFloatBE([offset])`

- `offset` **int** 开始读取之前要跳过的字节数。 必须满足 `0 <= offset <= byteArray.size - 4`。 **默认值:** `0`。
- 返回: **float**

从指定 `offset` 处的 `byteArray` 读取 32 位大端序浮点数。

```kotlin
val ba = byteArrayOf(1, 2, 3, 4)
println(ba.readFloatBE(0)) // 2.3879393E-38
```

#### `byteArray.readFloatLE([offset])`

- `offset` **int** 开始读取之前要跳过的字节数。 必须满足 `0 <= offset <= byteArray.size - 4`。 **默认值:** `0`。
- 返回: **float**

从指定 `offset` 处的 `buf` 读取 32 位小端序浮点数。

```kotlin
val ba = byteArrayOf(1, 2, 3, 4)
println(ba.readFloatLE(0)) // 1.5399896E-36
```

#### `byteArray.readInt8([offset])`

- `offset` **int** 开始读取之前要跳过的字节数。 必须满足 `0 <= offset <= byteArray.size - 1`。 **默认值:** `0`。
- 返回: 有符号的 **int** 

从指定 `offset` 处的`byteArray`读取有符号的 8 位整数。

```kotlin
val ba = byteArrayOf(-1, 5)
println(ba.readInt8(0)) // 打印-1
println(ba.readInt8(1)) // 打印5
println(ba.readInt8(2)) // 抛异常
```

#### `byteArray.readUInt8([offset])`

- `offset` **int** 开始读取之前要跳过的字节数。 必须满足 `0 <= offset <= byteArray.size - 1`。 **默认值:** `0`。
- 返回: 无符号的 **int** 

从指定 `offset` 处的 `byteArray` 读取无符号 8 位整数。

```kotlin
val ba = byteArrayOf(1, -2)
println(ba.readUInt8(0)) // 打印1
println(ba.readUInt8(1)) // 打印254
println(ba.readInt8(2)) // 抛异常
```

#### `byteArray.readInt16BE([offset])`

- `offset` **int** 开始读取之前要跳过的字节数。 必须满足 `0 <= offset <= byteArray.size - 2`。 **默认值:** `0`。
- 返回: **int**

从指定的 `offset` 处的 `byteArray` 读取有符号的大端序 16 位整数。

```kotlin
val ba = byteArrayOf(0, 5)
println(ba.readInt16BE()) // 打印5
val ba1 = byteArrayOf(1, 0, 5)
println(ba1.readInt16BE(1)) // 打印5
```

#### `byteArray.readUInt16BE([offset])`

- `offset` **int** 开始读取之前要跳过的字节数。 必须满足 `0 <= offset <= byteArray.size - 2`。 **默认值:** `0`。
- 返回: **int**

从指定的 `offset` 处的 `byteArray` 读取无符号的大端序 16 位整数。

```kotlin
val ba = byteArrayOf(0x12, 0x34, 0x56)
println(ba.readUInt16BE(0)) // 打印4660  ==> 0x1234
println(ba.readUInt16BE(1)) // 打印13398 ==> 0x3456
```

#### `byteArray.readInt16LE([offset])`

- `offset` **int** 开始读取之前要跳过的字节数。 必须满足 `0 <= offset <= byteArray.size - 2`。 **默认值:** `0`。
- 返回: **int**

从指定的 `offset` 处的 `byteArray` 读取有符号的小端序 16 位整数。

```kotlin
val ba = byteArrayOf(0x12, 0x34, 0x56)
println(ba.readInt16LE(0)) // 打印13330 ==> 0x3412
println(ba.readInt16LE(0).toString(16)) // 打印3412
println(ba.readInt16LE(1)) // 打印22068 ==> 0x5634
println(ba.readInt16LE(1).toString(16)) // 打印5634
```

#### `byteArray.readUInt16LE([offset])`

- `offset` **int** 开始读取之前要跳过的字节数。 必须满足 `0 <= offset <= byteArray.size - 2`。 **默认值:** `0`。
- 返回: **int**

从指定的 `offset` 处的 `byteArray` 读取无符号的小端序 16 位整数。

```kotlin
val ba = byteArrayOf(0x12, 0x34, 0x56)
println(ba.readUInt16LE(0)) //  打印13330 ==> 0x3412
println(ba.readUInt16LE(0).toString(16)) // 打印3412
println(ba.readUInt16LE(1)) // 打印22068 ==> 0x5634
println(ba.readUInt16LE(1).toString(16)) // 打印5634
```

#### `byteArray.readInt32BE([offset])`

- `offset` **int** 开始读取之前要跳过的字节数。 必须满足 `0 <= offset <= byteArray.size - 4`。 **默认值:** `0`。
- 返回: **int**

从指定的 `offset` 处的 `byteArray` 读取有符号的大端序 32 位整数。

```kotlin
val ba = byteArrayOf(0x12, 0x34, 0x56, 0x78)
println(ba.readInt32BE(0)) // 打印305419896 ==> 0x12345678
println(ba.readInt32BE(0).toString(16)) // 打印12345678
```

#### `byteArray.readUInt32BE([offset])`

- `offset` **int** 开始读取之前要跳过的字节数。 必须满足 `0 <= offset <= byteArray.size - 4`。 **默认值:** `0`。
- 返回: **int**

从指定的 `offset` 处的 `byteArray` 读取无符号的大端序 32 位整数。

```kotlin
val ba = byteArrayOf(0x12, 0x34, 0x56, 0x78)
println(ba.readUInt32BE(0)) // 打印305419896 ==> 0x12345678
println(ba.readUInt32BE(0).toString(16)) // 打印12345678
```

#### `byteArray.readInt32LE([offset])`

- `offset` **int** 开始读取之前要跳过的字节数。 必须满足 `0 <= offset <= byteArray.size - 4`。 **默认值:** `0`。
- 返回: **int**

从指定的 `offset` 处的 `byteArray` 读取有符号的小端序 32 位整数。

```kotlin
val ba = byteArrayOf(0x12, 0x34, 0x56, 0x78)
println(ba.readInt32LE(0)) // 打印2018915346 ==> 0x78563412
println(ba.readInt32LE(0).toString(16)) // 打印78563412
```

#### `byteArray.readUInt32LE([offset])`

- `offset` **int** 开始读取之前要跳过的字节数。 必须满足 `0 <= offset <= byteArray.size - 4`。 **默认值:** `0`。
- 返回: **int**

从指定的 `offset` 处的 `byteArray` 读取无符号的小端序 32 位整数。

```kotlin
val ba = byteArrayOf(0x12, 0x34, 0x56, 0x78)
println(ba.readUInt32LE(0)) // 打印2018915346 ==> 0x78563412
println(ba.readUInt32LE(0).toString(16)) // 打印78563412
```

#### `byteArray.readTimeBE([offset[, pattern]])`

- `offset` **int** 开始读取之前要跳过的字节数。 必须满足 `0 <= offset <= byteArray.size - 4`。 **默认值:** `0`。
- `pattern` **String** 转换为时间的格式。**默认值：**`yyyy-MM-dd HH:mm:ss`
- 返回: **String**

从指定的 `offset` 处的 `byteArray` 读取4字节的大端序时间戳根据规则转换为时间字符串。

```kotlin
val ba = byteArrayOf(0x61, 0x9B.toByte(), 0xBE.toByte(), 0x80.toByte())
println(ba.readTimeBE()) // 打印2021-11-23 00:00:00
println(ba.readTimeBE(0, "yyyy-MM-dd")) // 打印2021-11-23
```

#### `byteArray.readTimeLE([offset[, pattern]])`

- `offset` **int** 开始读取之前要跳过的字节数。 必须满足 `0 <= offset <= byteArray.size - 4`。 **默认值:** `0`。
- `pattern` **String** 转换为时间的格式。**默认值：**`yyyy-MM-dd HH:mm:ss`
- 返回: **String**

从指定的 `offset` 处的 `byteArray` 读取4字节的小端序时间戳根据规则转换为时间字符串。

```kotlin
val ba = byteArrayOf(0x80.toByte(), 0xBE.toByte(), 0x9B.toByte(), 0x61)
println(ba.readTimeLE()) // 打印2021-11-23 00:00:00
println(ba.readTimeLE(0, "yyyy-MM-dd")) // 打印2021-11-23
```

#### `byteArray.readStringBE(offset, byteLength[, encoding[, hasSpace]])`

- `offset` **int** 开始读取之前要跳过的字节数。 必须满足 `0 <= offset <= byteArray.size`。 **默认值:** `0`。
- `byteLength` **int** 字符串字节长度。
- `encoding` **String** hex / ascii。**默认值：**`hex`。
- `hasSpace` **Boolean** 是否有空格。**默认值：**`false`。
- 返回: **String**

从指定的 `offset` 处的 `byteArray` 读取byteLength长度的字节根据编码转化为大端序的字符串。

```kotlin
val ba = ByteArray(10)
ba.writeStringBE("I Love QMT", 0, "ascii")
println(ba.toHexString()) // 49 20 4C 6F 76 65 20 51 4D 54
println(ba.readStringBE(0, 10, "ascii")) // I Love QMT
println(ba.readStringBE(0, 10, "hex")) // 49 20 4C 6F 76 65 20 51 4D 54
```

#### `byteArray.readStringLE(offset, byteLength[, encoding[, hasSpace]])`

- `offset` **int** 开始读取之前要跳过的字节数。 必须满足 `0 <= offset <= byteArray.size`。 **默认值:** `0`。
- `byteLength` **int** 字符串字节长度。
- `encoding` **String** hex / ascii。**默认值：**`hex`。
- `hasSpace` **Boolean** 是否有空格。**默认值：**`false`。
- 返回: **String**

从指定的 `offset` 处的 `byteArray` 读取byteLength长度的字节根据编码转化为小端序的字符串。

```kotlin
val ba = ByteArray(10)
ba.writeStringLE("I Love QMT", 0, "ascii")
println(ba.toHexString()) // 54 4D 51 20 65 76 6F 4C 20 49
println(ba.readStringLE(1, 10, "ascii")) // I Love QMT
println(ba.readStringLE(1, 10, "hex")) // 54 4D 51 20 65 76 6F 4C 20 49
```

#### `byteArray.readByteArrayBE(offset, byteLength)`

- `offset` **int** 开始读取之前要跳过的字节数。 必须满足 `0 <= offset <= byteArray.size`。 **默认值:** `0`。
- `byteLength` **int** 需要读取的字节长度
- 返回: **ByteArray**

从指定的 `offset` 处的 `byteArray` 读取byteLength长度的大端序字节。

```kotlin
val ba = byteArrayOf(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09)
val ba1 = ba.readByteArrayBE(0, 2)
val ba2 = ba.readByteArrayBE(0, 9)
val ba3 = ba.readByteArrayBE(2, 10)
println(ba1.toHexString()) // 01 02
println(ba2.toHexString()) // 01 02 03 04 05 06 07 08 09
println(ba3.toHexString()) // 03 04 05 06 07 08 09
```

#### `byteArray.readByteArrayLE(offset, byteLength)`

- `offset` **int** 开始读取之前要跳过的字节数。 必须满足 `0 <= offset <= byteArray.size`。 **默认值:** `0`。
- `byteLength` **int** 需要读取的字节长度
- 返回: **ByteArray**

从指定的 `offset` 处的 `byteArray` 读取byteLength长度的小端序字节。

```kotlin
val ba = byteArrayOf(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09)
val ba1 = ba.readByteArrayLE(0, 2)
val ba2 = ba.readByteArrayLE(0, 9)
val ba3 = ba.readByteArrayLE(2, 10)
println(ba1.toHexString()) // 02 01
println(ba2.toHexString()) // 09 08 07 06 05 04 03 02 01
println(ba3.toHexString()) // 09 08 07 06 05 04 03
```





#### `byteArray.writeStringBE(str[, offset[, encoding]])`

- `str` **String** 需要写入的字符串

- `offset` **int** 开始读取之前要跳过的字节数。 **默认值:** `0`。
- `encoding` **String**  str的编码类型 hex / ascii。**默认值：**`hex`。
- 返回: **ByteArray**

将字符串str根据编码`encoding`大端序写入`byteArray`指定的`offset`。

```kotlin
val ba = ByteArray(15)
ba.writeStringBE("01 02 03 04", 0, "hex")
ba.writeStringBE("abcde", 5, "ascii")
println(ba.toHexString()) // 01 02 03 04 00 61 62 63 64 65 00 00 00 00 00
```

#### `byteArray.writeStringLE(str[, offset[, encoding]])`

- `str` **String** 需要写入的字符串

- `offset` **int** 开始读取之前要跳过的字节数。 **默认值:** `0`。
- `encoding` **String**  str的编码类型 hex / ascii。**默认值：**`hex`。
- 返回: **ByteArray**

将字符串str根据编码`encoding`小端序写入`byteArray`指定的`offset`。

```kotlin
val ba = ByteArray(15)
ba.writeStringLE("01 02 03 04", 0, "hex")
ba.writeStringLE("abcde", 5, "ascii")
println(ba.toHexString()) // 04 03 02 01 00 65 64 63 62 61 00 00 00 00 00
```

#### `byteArray.writeStringBE(str, offset, length[, encoding])`

- `str` **String** 需要写入的字符串

- `offset` **int** 开始读取之前要跳过的字节数。 **默认值:** `0`。
- `length` **int**  要写入字符串的长度。
- `encoding` **String**  str的编码类型 hex / ascii。**默认值：**`hex`。
- 返回: **ByteArray**

- `offset` **int** 开始读取之前要跳过的字节数。 **默认值:** `0`。

将字符串str根据长度`length`与编码`encoding`大端序写入`byteArray`指定的`offset`。

```kotlin
val ba = ByteArray(15)
ba.writeStringBE("01 02 03 04", 0, 2)
ba.writeStringBE("abcde", 5, 3, "ascii")
println(ba.toHexString()) // 01 02 00 00 00 61 62 63 00 00 00 00 00 00 00
```

#### `byteArray.writeStringLE(str, offset, length[, encoding])`

- `str` **String** 需要写入的字符串

- `offset` **int** 开始读取之前要跳过的字节数。 **默认值:** `0`。
- `length` **int**  要写入字符串的长度。
- `encoding` **String**  str的编码类型 hex / ascii。**默认值：**`hex`。
- 返回: **ByteArray**

- `offset` **int** 开始读取之前要跳过的字节数。 **默认值:** `0`。

将字符串str根据长度`length`与编码`encoding`小端序写入`byteArray`指定的`offset`。

```kotlin
val ba = ByteArray(15)
ba.writeStringLE("01 02 03 04", 0, 2)
ba.writeStringLE("abcde", 5, 3, "ascii")
println(ba.toHexString()) // 04 03 00 00 00 65 64 63 00 00 00 00 00 00 00
```

#### `byteArray.writeFloatBE(value[, offset])`

- `value` **int** 要写入 `byteArray` 的浮点数。
- `offset` **int**开始写入之前要跳过的字节数。 必须满足 `0 <= offset <= byteArray.size - 4`。 **默认值:** `0`。
- 返回:  **ByteArray**

将 `value`按大端序写入 `byteArray` 中指定的 `offset`。

```kotlin
val ba = ByteArray(15)
ba.writeFloatBE(3.1415926f)
println(ba.toHexString()) // 40 49 0F DA 00 00 00 00 00 00 00 00 00 00 00
println(ba.readFloatBE()) // 3.1415925
```

#### `byteArray.writeFloatLE(value[, offset])`

- `value` **int** 要写入 `byteArray` 的浮点数。
- `offset` **int**开始写入之前要跳过的字节数。 必须满足 `0 <= offset <= byteArray.size - 4`。 **默认值:** `0`。
- 返回:  **ByteArray**

将 `value`按小端序写入 `byteArray` 中指定的 `offset`。

```kotlin
val ba = ByteArray(15)
ba.writeFloatLE(3.1415926f)
println(ba.toHexString()) // DA 0F 49 40 00 00 00 00 00 00 00 00 00 00 00
println(ba.readFloatLE()) // 3.1415925
```

#### `byteArray.writeInt8(value[, offset])`

- `value` **int** 要写入 `byteArray` 的整数。
- `offset` **int**开始写入之前要跳过的字节数。 必须满足 `0 <= offset <= byteArray.size - 1`。 **默认值:** `0`。
- 返回:  **ByteArray**

将 `value` 写入 `byteArray` 中指定的 `offset`。 `value` 必须是有效的有符号 8 位整数。

```kotlin
val ba = byteArrayOf(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09)
println(ba.writeInt8(10).toHexString()) 	// 0A 02 03 04 05 06 07 08 09
println(ba.writeInt8(11, 1).toHexString())	// 0A 0B 03 04 05 06 07 08 09
println(ba.writeInt8(11, 11).toHexString())	// The value of "offset" is out of range. It must be >= 0 and <= 8. Received 11
```

#### `byteArray.writeInt16BE(value[, offset])`

- `value` **int** 要写入 `byteArray` 的整数。
- `offset` **int**开始写入之前要跳过的字节数。 必须满足 `0 <= offset <= byteArray.size - 2`。 **默认值:** `0`。
- 返回:  **ByteArray**

将 `value` 作为大端序写入 `byteArray` 中指定的 `offset`。

```kotlin
val ba = byteArrayOf(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09)
println(ba.writeInt16BE(10).toHexString())		// 00 0A 03 04 05 06 07 08 09
println(ba.writeInt16BE(11, 3).toHexString())	// 00 0A 03 00 0B 06 07 08 09
```

#### `byteArray.writeInt16LE(value[, offset])`

- `value` **int** 要写入 `byteArray` 的整数。
- `offset` **int**开始写入之前要跳过的字节数。 必须满足 `0 <= offset <= byteArray.size - 2`。 **默认值:** `0`。
- 返回:  **ByteArray**

将 `value` 作为小端序写入 `byteArray` 中指定的 `offset`。

```kotlin
val ba = byteArrayOf(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09)
println(ba.writeInt16LE(10).toHexString())		// 0A 00 03 04 05 06 07 08 09
println(ba.writeInt16LE(11, 3).toHexString())	// 0A 00 03 0B 00 06 07 08 09
```

#### `byteArray.writeInt32BE(value[, offset])`

- `value` **int** 要写入 `byteArray` 的整数。
- `offset` **int**开始写入之前要跳过的字节数。 必须满足 `0 <= offset <= byteArray.size - 4`。 **默认值:** `0`。
- 返回:  **ByteArray**

将 `value` 作为大端序写入 `byteArray` 中指定的 `offset`。

```kotlin
val ba = byteArrayOf(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09)
println(ba.writeInt32BE(10).toHexString())		// 00 00 00 0A 05 06 07 08 09
println(ba.writeInt32BE(11, 4).toHexString())	// 00 00 00 0A 00 00 00 0B 09
```

#### `byteArray.writeInt32LE(value[, offset])`

- `value` **int** 要写入 `byteArray` 的整数。
- `offset` **int**开始写入之前要跳过的字节数。 必须满足 `0 <= offset <= byteArray.size - 4`。 **默认值:** `0`。
- 返回:  **ByteArray**

将 `value` 作为小端序写入 `byteArray` 中指定的 `offset`。

```kotlin
val ba = byteArrayOf(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09)
println(ba.writeInt32LE(10).toHexString())		// 0A 00 00 00 05 06 07 08 09
println(ba.writeInt32LE(11, 4).toHexString())	// 0A 00 00 00 0B 00 00 00 09
```

#### `byteArray.writeTimeBE(time[, offset[, pattern]])`

- `time`  **String** 要写入 `byteArray` 的时间字符串。
- `offset` **int**开始写入之前要跳过的字节数。 必须满足 `0 <= offset <= byteArray.size - 4`。 **默认值:** `0`。
- `pattern` **String** 时间格式。**默认值：**`yyyy-MM-dd HH:mm:ss`
- 返回:  **ByteArray**

将 `time` 作为大端序写入 `byteArray` 中指定的 `offset`，占用四个字节

```kotlin
val ba = ByteArray(15)
ba.writeTimeBE("2021-11-23 14:20:52")
println(ba.toHexString())	// 61 9C 88 44 00 00 00 00 00 00 00 00 00 00 00
println(ba.readTimeBE())	// 2021-11-23 14:20:52
```

#### `byteArray.writeTimeLE(time[, offset[, pattern]])`

- `time`  **String** 要写入 `byteArray` 的时间字符串。
- `offset` **int**开始写入之前要跳过的字节数。 必须满足 `0 <= offset <= byteArray.size - 4`。 **默认值:** `0`。
- `pattern` **String** 时间格式。**默认值：**`yyyy-MM-dd HH:mm:ss`
- 返回:  **ByteArray**

将 `time` 作为小端序写入 `byteArray` 中指定的 `offset`，占用四个字节

```kotlin
val ba = ByteArray(15)
ba.writeTimeLE("2021-11-23 14:20:52")
println(ba.toHexString())	// 44 88 9C 61 00 00 00 00 00 00 00 00 00 00 00
println(ba.readTimeLE())	// 2021-11-23 14:20:52
```

#### `byteArray.writeByteArrayBE(byteArray[, offset[, length]])`

- `byteArray`  **ByteArray** 要写入 `byteArray` 的字节数组。
- `offset` **int**开始写入之前要跳过的字节数。 必须满足 `0 <= offset <= byteArray.size - length`。 **默认值:** `0`。
- `length` **int** 要写入字节数组的长度。**默认值：**`byteArray.size`。
- 返回:  **ByteArray**

将 `byteArray` 根据指定的长度截取作为大端序写入 `byteArray` 中指定的 `offset`

```kotlin
val ba = ByteArray(15)
ba.writeByteArrayBE(byteArrayOf(0x01, 0x02, 0x03, 0x04))
println(ba.toHexString()) // 01 02 03 04 00 00 00 00 00 00 00 00 00 00 00

ba.writeByteArrayBE(byteArrayOf(0x05, 0x06, 0x07, 0x08), 4, 2)
println(ba.toHexString()) // 01 02 03 04 05 06 00 00 00 00 00 00 00 00 00
```

#### `byteArray.writeByteArrayLE(byteArray[, offset[, length]])`

- `byteArray`  **ByteArray** 要写入 `byteArray` 的字节数组。
- `offset` **int**开始写入之前要跳过的字节数。 必须满足 `0 <= offset <= byteArray.size - length`。 **默认值:** `0`。
- `length` **int** 要写入字节数组的长度。**默认值：**`byteArray.size`。
- 返回:  **ByteArray**

将 `byteArray` 根据指定的长度截取作为小端序写入 `byteArray` 中指定的 `offset`

```kotlin
val ba = ByteArray(15)
ba.writeByteArrayLE(byteArrayOf(0x01, 0x02, 0x03, 0x04))
println(ba.toHexString()) // 04 03 02 01 00 00 00 00 00 00 00 00 00 00 00

ba.writeByteArrayLE(byteArrayOf(0x05, 0x06, 0x07, 0x08), 4, 2)
println(ba.toHexString()) // 01 02 03 04 05 06 00 00 00 00 00 00 00 00 00
```

#### `byteArray.insertByteArrayBE(insertArray[, origrinalIndex[, insertArrayOffset[, insertArrayLength]]])`

- `insertArray`  **ByteArray** 要插入 `byteArray` 的字节数组。
- `origrinalIndex` **int**开始插入之前要跳过的字节数。 必须满足 `0 <= offset <= byteArray.size - length`。 **默认值:** `0`。
- `insertArrayOffset` **int** 要插入字节数组跳过的字节数。
- `insertArrayLength` **int** 要插入字节数组的长度。**默认值：**`insertArray.size - insertArrayOffset`。
- 返回:  **ByteArray**

将 `insertArray` 根据指定的索引`insertArrayOffset`与长度`insertArrayLength`截取作为大端序插入 `byteArray` 中指定的 `origrinalIndex`。返回插入后的字节数组。

```kotlin
val ba = ByteArray(15)
val insertArray = byteArrayOf(0x01, 0x02, 0x03, 0x04, 0x05, 0x06)
val result = ba.insertByteArrayBE(insertArray, 1, 1)
println(result.toHexString()) // 00 02 03 04 05 06 00 00 00 00 00 00 00 00 00 00 00 00 00 00
```

#### `byteArray.insertByteArrayLE(insertArray[, origrinalIndex[, insertArrayOffset[, insertArrayLength]]])`

- `insertArray`  **ByteArray** 要插入 `byteArray` 的字节数组。
- `origrinalIndex` **int**开始插入之前要跳过的字节数。 必须满足 `0 <= offset <= byteArray.size - length`。 **默认值:** `0`。
- `insertArrayOffset` **int** 要插入字节数组跳过的字节数。
- `insertArrayLength` **int** 要插入字节数组的长度。**默认值：**`insertArray.size - insertArrayOffset`。
- 返回:  **ByteArray**

将 `insertArray` 根据指定的索引`insertArrayOffset`与长度`insertArrayLength`截取作为小端序插入 `byteArray` 中指定的 `origrinalIndex`。返回插入后的字节数组。

```kotlin
val ba = ByteArray(15)
val insertArray = byteArrayOf(0x01, 0x02, 0x03, 0x04, 0x05, 0x06)
val result = ba.insertByteArrayLE(insertArray, 1, 1)
println(result.toHexString()) // 00 05 04 03 02 01 00 00 00 00 00 00 00 00 00 00 00 00 00 00
```