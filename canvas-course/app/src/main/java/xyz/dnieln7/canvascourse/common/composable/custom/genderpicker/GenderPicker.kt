package xyz.dnieln7.canvascourse.common.composable.custom.genderpicker

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import xyz.dnieln7.canvascourse.R

@Composable
fun GenderPicker(
    modifier: Modifier = Modifier,
    maleGradient: List<Color> = listOf(
        Color.Blue,
        Color.Blue.copy(alpha = 0.40F),
        Color.Blue.copy(alpha = 0.20F),
    ),
    femaleGradient: List<Color> = listOf(
        Color.Magenta,
        Color.Magenta.copy(alpha = 0.40F),
        Color.Magenta.copy(alpha = 0.20F),
    ),
    gap: Dp = 50.dp,
    scaleFactor: Float = 8F, // How many times bigger it will get
    onGenderSelected: (Gender) -> Unit = {},
) {
    var selectedGender: Gender by remember { mutableStateOf(Gender.FEMALE) }
    var center by remember { mutableStateOf(Offset.Unspecified) }

    val malePathString = stringResource(R.string.male_path)
    val femalePathString = stringResource(R.string.female_path)

    val malePath = remember { PathParser().parsePathString(malePathString).toPath() }
    val malePathBounds = remember { malePath.getBounds() } //  Rectangle of the path
    var maleTranslationOffset by remember { mutableStateOf(Offset.Zero) }

    val femalePath = remember { PathParser().parsePathString(femalePathString).toPath() }
    val femalePathBounds = remember { femalePath.getBounds() }
    var femaleTranslationOffset by remember { mutableStateOf(Offset.Zero) }

    var currentClickOffset by remember { mutableStateOf(Offset.Zero) }

    val maleSelectionRadius by animateFloatAsState(
        targetValue = if (selectedGender == Gender.MALE) 80F else 0F,
        animationSpec = tween(
            durationMillis = 500,
        ),
        label = "maleSelectionRadius",
    )

    val femaleSelectionRadius by animateFloatAsState(
        targetValue = if (selectedGender == Gender.FEMALE) 80F else 0F,
        animationSpec = tween(
            durationMillis = 500,
        ),
        label = "femaleSelectionRadius",
    )

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(true) {
                detectTapGestures {
                    val transformedMaleBounds = Rect(
                        offset = maleTranslationOffset,
                        size = malePathBounds.size * scaleFactor,
                    )

                    val transformedFemaleBounds = Rect(
                        offset = femaleTranslationOffset,
                        size = femalePathBounds.size * scaleFactor,
                    )

                    if (selectedGender != Gender.MALE && transformedMaleBounds.contains(it)) {
                        currentClickOffset = it
                        selectedGender = Gender.MALE
                        onGenderSelected(selectedGender)

                        return@detectTapGestures
                    }


                    if (selectedGender != Gender.FEMALE && transformedFemaleBounds.contains(it)) {
                        currentClickOffset = it
                        selectedGender = Gender.FEMALE
                        onGenderSelected(selectedGender)

                        return@detectTapGestures
                    }
                }
            }
            .then(modifier)
    ) {
        center = this.center

        maleTranslationOffset = Offset(
            x = center.x - (malePathBounds.width * scaleFactor) - (gap.toPx() / 2F),
            y = center.y - (malePathBounds.height * scaleFactor) / 2F,
        )

        val untransformedMaleClickOffset = if (currentClickOffset == Offset.Zero) {
            malePathBounds.center
        } else {
            (currentClickOffset - maleTranslationOffset) / scaleFactor
        }

        femaleTranslationOffset = Offset(
            x = center.x + gap.toPx() / 2F, // Path bounds' top left is already at the edge of center so, no need to use femalePathBounds.width
            y = center.y - (femalePathBounds.height * scaleFactor) / 2F,
        )

        val untransformedFemaleClickOffset = if (currentClickOffset == Offset.Zero) {
            femalePathBounds.center
        } else {
            (currentClickOffset - femaleTranslationOffset) / scaleFactor
        }

        translate(
            left = maleTranslationOffset.x,
            top = maleTranslationOffset.y,
        ) {
            scale(
                scale = scaleFactor,
                pivot = malePathBounds.topLeft,
            ) {
                drawPath(
                    path = malePath,
                    color = Color.LightGray,
                )
                clipPath(
                    path = malePath,
                ) {
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = maleGradient,
                            center = untransformedMaleClickOffset,
                            radius = maleSelectionRadius + 1F,
                        ),
                        center = untransformedMaleClickOffset,
                        radius = maleSelectionRadius,
                    )
                }
            }
        }

        translate(
            top = femaleTranslationOffset.y,
            left = femaleTranslationOffset.x,
        ) {
            scale(
                scale = scaleFactor,
                pivot = femalePathBounds.topLeft,
            ) {
                drawPath(
                    path = femalePath,
                    color = Color.LightGray,
                )
                clipPath(
                    path = femalePath,
                ) {
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = femaleGradient,
                            center = untransformedFemaleClickOffset,
                            radius = femaleSelectionRadius + 1F,
                        ),
                        center = untransformedFemaleClickOffset,
                        radius = femaleSelectionRadius,
                    )
                }
            }
        }
    }
}