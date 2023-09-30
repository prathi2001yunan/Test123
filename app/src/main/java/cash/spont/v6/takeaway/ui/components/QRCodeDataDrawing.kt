package cash.spont.v6.takeaway.ui.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.google.zxing.qrcode.encoder.ByteMatrix

private typealias Coordinate = Pair<Int, Int>

fun DrawScope.drawAllQrCodeDataBits(
    qrCodeProperties: QrCodeProperties,
    bytes: ByteMatrix,
    size: Size
) {
    setOf(
        Pair(
            first = Coordinate(first = FINDER_PATTERN_ROW_COUNT, second = 0),
            second = Coordinate(
                first = (bytes.width - FINDER_PATTERN_ROW_COUNT),
                second = FINDER_PATTERN_ROW_COUNT
            )
        ),
        Pair(
            first = Coordinate(first = 0, second = FINDER_PATTERN_ROW_COUNT),
            second = Coordinate(
                first = bytes.width,
                second = bytes.height - FINDER_PATTERN_ROW_COUNT
            )
        ),
        Pair(
            first = Coordinate(
                first = FINDER_PATTERN_ROW_COUNT,
                second = (bytes.height - FINDER_PATTERN_ROW_COUNT)
            ),
            second = Coordinate(
                first = bytes.width,
                second = bytes.height
            )
        )
    ).forEach { section ->
        for (y in section.first.second until section.second.second) {
            for (x in section.first.first until section.second.first) {
                if (bytes[x, y] == 1.toByte()) {
                    drawPath(
                        color = qrCodeProperties.foreground,
                        path = newPath {
                            addRect(
                                rect = Rect(
                                    offset = Offset(
                                        x = QR_MARGIN_PX + x * size.width,
                                        y = QR_MARGIN_PX + y * size.height
                                    ),
                                    size = size
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}