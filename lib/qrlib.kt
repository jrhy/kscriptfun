//DEPS com.google.zxing:core:3.3.2
//DEPS com.google.zxing:javase:3.3.2

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

fun createQrImage(qrFile: File, text: String, size: Int, fileType: String) {
    val byteMatrix = text.toQrBitMatrix()
    // Make the BufferedImage that are to hold the QRCode
    val matrixWidth = byteMatrix.getWidth()
    val image = BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB)
    image.createGraphics()

    val graphics = image.graphics as Graphics2D
    graphics.color = Color.WHITE
    graphics.fillRect(0, 0, matrixWidth, matrixWidth)
    // Paint and save the image using the ByteMatrix
    graphics.color = Color.BLACK

    for (i in 0 until matrixWidth) {
        for (j in 0 until matrixWidth) {
            if (byteMatrix.get(i, j)) {
                graphics.fillRect(i, j, 1, 1)
            }
        }
    }
    ImageIO.write(image, fileType, qrFile)
}

fun createUnicodeQr(
        text: String,
        level: ErrorCorrectionLevel = ErrorCorrectionLevel.Q
): String {
    val twoByTwo = arrayOf(
            ' ',
            '▗',
            '▖',
            '▄',
            '▝',
            '▐',
            '▞',
            '▟',
            '▘',
            '▚',
            '▌',
            '▙',
            '▀',
            '▜',
            '▛',
            '█'
    )
    val matrix = text.toQrBitMatrix(level)
    val matrixWidth = matrix.width
    return Array((matrixWidth) / 2) { i ->
        var line = ""
        for (j in 0 until matrixWidth step 2) {
            var index = 0
            if (matrix.getOrFalse(i * 2, j))
                index += 8
            if (matrix.getOrFalse(i * 2, j + 1))
                index += 4
            if (matrix.getOrFalse(i * 2 + 1, j))
                index += 2
            if (matrix.getOrFalse(i * 2 + 1, j + 1))
                index += 1
            val char = twoByTwo[index]
            line += char
        }
        line
    }.joinToString("\n")
}

fun createBrailleQr(
        text: String,
        level: ErrorCorrectionLevel = ErrorCorrectionLevel.Q
): String {
    val base: Char = '⠀'
    val matrix = text.toQrBitMatrix(level)
    val matrixWidth = matrix.width
    return Array((matrixWidth) / 4) { i ->
        var line = ""
        for (j in 0 until matrixWidth step 2) {
            var index = 0
            if (matrix.getOrFalse(i * 4, j))
                index += 1
            if (matrix.getOrFalse(i * 4 + 1, j))
                index += 2
            if (matrix.getOrFalse(i * 4 + 2, j))
                index += 4
            if (matrix.getOrFalse(i * 4, j + 1))
                index += 8
            if (matrix.getOrFalse(i * 4 + 1, j + 1))
                index += 0x10
            if (matrix.getOrFalse(i * 4 + 2, j + 1))
                index += 0x20
            if (matrix.getOrFalse(i * 4 + 3, j))
                index += 0x40
            if (matrix.getOrFalse(i * 4 + 3, j + 1))
                index += 0x80
            line += base + index
        }
        line
    }.joinToString("\n")
}

fun BitMatrix.getOrFalse(i: Int, j: Int): Boolean =
    if (i < width && j < width)
        get(i, j)
    else
        false

fun String.toQrBitMatrix(
        errorCorrectionLevel: ErrorCorrectionLevel = ErrorCorrectionLevel.L
): BitMatrix =
        QRCodeWriter().encode(
                this,
                BarcodeFormat.QR_CODE,
                1,
                1,
                mapOf(
                        EncodeHintType.ERROR_CORRECTION to errorCorrectionLevel
                ))!!

fun createQrAnsiString(text: String): String {
    val byteMatrix = text.toQrBitMatrix()
    val matrixWidth = byteMatrix.width
    val ESC = 27.toChar()
    return Array(matrixWidth) { i ->
        var line = ""
        for (j in 0 until matrixWidth) {
            line += when (byteMatrix.get(i, j)) {
                true -> "$ESC[7m "
                false -> "$ESC[0m "
            }
        }
        line
    }.joinToString("\n")
}
