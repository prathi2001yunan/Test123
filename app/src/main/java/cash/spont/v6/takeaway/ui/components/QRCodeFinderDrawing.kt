package cash.spont.v6.takeaway.ui.components

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.drawscope.DrawScope


const val FINDER_PATTERN_ROW_COUNT = 7
private const val INTERIOR_EXTERIOR_SHAPE_RATIO = 3f / FINDER_PATTERN_ROW_COUNT
private const val INTERIOR_EXTERIOR_OFFSET_RATIO = 2f / FINDER_PATTERN_ROW_COUNT
private const val INTERIOR_EXTERIOR_SHAPE_CORNER_RADIUS = 0.12f
private const val INTERIOR_BACKGROUND_EXTERIOR_SHAPE_RATIO = 5f / FINDER_PATTERN_ROW_COUNT
private const val INTERIOR_BACKGROUND_EXTERIOR_OFFSET_RATIO = 1f / FINDER_PATTERN_ROW_COUNT
private const val INTERIOR_BACKGROUND_EXTERIOR_SHAPE_CORNER_RADIUS = 0.5f
internal fun DrawScope.drawQrCodeFinders(
    qrCodeProperties: QrCodeProperties,
    sideLength: Float,
    finderPatternSize: Size,
) {

    setOf(
        Offset(x = QR_MARGIN_PX, y = QR_MARGIN_PX),
        Offset(x = sideLength - (QR_MARGIN_PX + finderPatternSize.width), y = QR_MARGIN_PX),
        Offset(x = QR_MARGIN_PX, y = sideLength - (QR_MARGIN_PX + finderPatternSize.height))
    ).forEach { offset ->
        drawQrCodeFinder(
            qrCodeProperties = qrCodeProperties,
            topLeft = offset,
            finderPatternSize = finderPatternSize,
            cornerRadius = CornerRadius.Zero,
        )
    }
}
private fun DrawScope.drawQrCodeFinder(
    qrCodeProperties: QrCodeProperties,
    topLeft: Offset,
    finderPatternSize: Size,
    cornerRadius: CornerRadius,

) {
    drawPath(
        color = qrCodeProperties.foreground,
        path = newPath {
            addRoundRect(
                roundRect = RoundRect(
                    rect = Rect(
                        offset = topLeft,
                        size = finderPatternSize
                    ),
                    cornerRadius = cornerRadius
                )
            )
            val innerBackgroundOffset = Offset(
                x = finderPatternSize.width * INTERIOR_BACKGROUND_EXTERIOR_OFFSET_RATIO,
                y = finderPatternSize.height * INTERIOR_BACKGROUND_EXTERIOR_OFFSET_RATIO
            )
            addRoundRect(
                roundRect = RoundRect(
                    rect = Rect(
                        offset = topLeft + innerBackgroundOffset,
                        size = finderPatternSize * INTERIOR_BACKGROUND_EXTERIOR_SHAPE_RATIO
                    ),
                    cornerRadius = cornerRadius * INTERIOR_BACKGROUND_EXTERIOR_SHAPE_CORNER_RADIUS
                )
            )
            val innerRectOffset = Offset(
                x = finderPatternSize.width * INTERIOR_EXTERIOR_OFFSET_RATIO,
                y = finderPatternSize.height * INTERIOR_EXTERIOR_OFFSET_RATIO
            )
            addRoundRect(
                roundRect = RoundRect(
                    rect = Rect(
                        offset = topLeft + innerRectOffset,
                        size = finderPatternSize * INTERIOR_EXTERIOR_SHAPE_RATIO
                    ),
                    cornerRadius = cornerRadius * INTERIOR_EXTERIOR_SHAPE_CORNER_RADIUS
                )
            )
        }
    )
}

fun newPath(withPath: Path.() -> Unit) = Path().apply {
    fillType = PathFillType.EvenOdd
    withPath(this)
}