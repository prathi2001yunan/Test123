package cash.spont.v6.takeaway.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.google.zxing.qrcode.encoder.Encoder
import com.google.zxing.qrcode.encoder.QRCode

@Composable
fun QRCode(
    contents: String,
    modifier: Modifier = Modifier,
    qrCodeProperties: QrCodeProperties = QrCodeProperties(
        foreground = if (isSystemInDarkTheme()) Color.White else Color.Black,
        background = if (isSystemInDarkTheme()) Color.Black else Color.White
    )
) {

    require(contents.isNotEmpty()) { "QR Code must have non empty contents" }
    val qrCode = remember(contents) {
        createQrCode(contents = contents)
    }
    Box(
        modifier = modifier
            .defaultMinSize(48.dp, 48.dp)
            .aspectRatio(1f)
            .background(qrCodeProperties.background)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val rowHeight = (size.width - QR_MARGIN_PX * 2f) / qrCode.matrix.height
            val columnWidth = (size.width - QR_MARGIN_PX * 2f) / qrCode.matrix.width
            drawQrCodeFinders(
                qrCodeProperties = qrCodeProperties,
                sideLength = size.width,
                finderPatternSize = Size(
                    width = columnWidth * FINDER_PATTERN_ROW_COUNT,
                    height = rowHeight * FINDER_PATTERN_ROW_COUNT
                )
            )
            drawAllQrCodeDataBits(
                qrCodeProperties = qrCodeProperties,
                bytes = qrCode.matrix,
                size = Size(
                    width = columnWidth,
                    height = rowHeight
                )
            )
        }
    }
}

private fun createQrCode(contents: String): QRCode {
    require(contents.isNotEmpty())

    return Encoder.encode(
        contents,
        QR_ERROR_CORRECTION_LEVEL,
        mapOf(
            EncodeHintType.CHARACTER_SET to "UTF-8",
            EncodeHintType.MARGIN to QR_MARGIN_PX,
            EncodeHintType.ERROR_CORRECTION to QR_ERROR_CORRECTION_LEVEL
        )
    )
}

data class QrCodeProperties(
    val foreground: Color,
    val background: Color
)

const val QR_MARGIN_PX = 16f
val QR_ERROR_CORRECTION_LEVEL = ErrorCorrectionLevel.Q