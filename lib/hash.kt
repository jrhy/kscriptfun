import java.security.MessageDigest
import java.util.Base64

val String.sha256
    get() = hash("SHA-256").base64()
val String.sha1
    get() = hash("SHA1").hexString()
val String.md5
    get() = hash("MD5")
fun String.hash(alg: String) = toByteArray().hash(alg)
val ByteArray.sha256
    get() = hash("SHA-256").base64()
val ByteArray.sha1
    get() = hash("SHA1").hexString()
val ByteArray.md5
    get() = hash("MD5").hexString()
fun ByteArray.hash(alg: String) =
        MessageDigest.getInstance(alg)
                .digest(this)
fun ByteArray.hexString() = this
                .map { "%02x".format(it) }
                .joinToString("")
fun ByteArray.base64() = Base64.getEncoder().encodeToString(this)


