//DEPS com.google.zxing:core:3.3.2
//DEPS com.google.zxing:javase:3.3.2

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

//const val fullBlockChar = '█'

fun main(args: Array<String>) =
        args
                .or("hello there")
                .joinToString(" ")
                .let {
                    println(it)
                    println(createQrAnsiString(it))
                }

fun createQrImage(qrFile: File, qrCodeText: String, size: Int, fileType: String) {
    // Create the ByteMatrix for the QR-Code that encodes the given String
    val hintMap = mapOf(
            EncodeHintType.ERROR_CORRECTION to ErrorCorrectionLevel.L
    )
    val qrCodeWriter = QRCodeWriter()
    val byteMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size, hintMap)
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

fun createQrAnsiString(qrCodeText: String): String {
    val hintMap = mapOf(
            EncodeHintType.ERROR_CORRECTION to ErrorCorrectionLevel.L
    )
    val qrCodeWriter = QRCodeWriter()
    val byteMatrix =
            qrCodeWriter.encode(
                    qrCodeText,
                    BarcodeFormat.QR_CODE,
                    1,
                    1,
                    hintMap)
    val matrixWidth = byteMatrix.width

    val ESC = 27.toChar()
    return Array(matrixWidth) { i ->
        var line = ""
        for (j in 0 until matrixWidth) {
            line += when (byteMatrix.get(i, j)) {
                true -> "$ESC[7m   "
                false -> "$ESC[0m   "
            }
        }
        line
    }.joinToString("\n")
}

private fun Array<String>.or(string: String) =
        if (isEmpty())
            arrayOf(string)
        else
            this
