package xyz.dnieln7.canvascourse.common.composable.custom.likeselector

import androidx.compose.animation.core.EaseOutCirc
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import xyz.dnieln7.canvascourse.R
import kotlin.math.max

@Composable
fun ReactionSelector(
    modifier: Modifier = Modifier,
    likeColors: List<Color> = listOf(
        Color.Blue.copy(alpha = 0.40F),
        Color.Blue.copy(alpha = 0.60F),
        Color.Blue.copy(alpha = 0.80F),
    ),
    dislikeColors: List<Color> = listOf(
        Color.Red.copy(alpha = 0.40F),
        Color.Red.copy(alpha = 0.60F),
        Color.Red.copy(alpha = 0.80F),
    ),
    reactionSize: Dp = 48.dp,
    gap: Dp = 28.dp,
    onReactionChanged: (Reaction) -> Unit = {},
) {
    var currentReaction: Reaction by remember { mutableStateOf(Reaction.LIKE) }

    val scaleFactor = reactionSize.value / 24F

    val likePathString = stringResource(R.string.like)

    val like = remember { PathParser().parsePathString(likePathString).toPath() }
    val likeBounds = remember { like.getBounds() }
    var scaledLikeTranslation by remember { mutableStateOf(Offset.Zero) }

    val likeGradientEndY by animateFloatAsState(
        targetValue = if (currentReaction == Reaction.LIKE) likeBounds.topLeft.y else likeBounds.bottomRight.y,
        animationSpec = tween(
            durationMillis = 500,
            easing = EaseOutCirc,
        ),
        label = "likeSelectionRadius",
    )

    val dislikePathString = stringResource(R.string.dislike)

    val dislike = remember { PathParser().parsePathString(dislikePathString).toPath() }
    val dislikeBounds = remember { dislike.getBounds() }
    var scaledDislikeTranslation by remember { mutableStateOf(Offset.Zero) }

    val dislikeGradientEndY by animateFloatAsState(
        targetValue = if (currentReaction == Reaction.DISLIKE) dislikeBounds.topLeft.y else dislikeBounds.bottomRight.y,
        animationSpec = tween(
            durationMillis = 500,
            easing = EaseOutCirc,
        ),
        label = "dislikeSelectionRadius",
    )

    val width = (likeBounds.width * scaleFactor).dp + (dislikeBounds.width * scaleFactor).dp
    val height = max(likeBounds.height * scaleFactor, dislikeBounds.height * scaleFactor).dp

    Canvas(
        modifier = modifier
            .width(width)
            .height(height)
            .pointerInput(true) {
                detectTapGestures {
                    val scaledLikeBounds = Rect(
                        offset = scaledLikeTranslation,
                        size = likeBounds.size * scaleFactor,
                    )

                    if (currentReaction != Reaction.LIKE && scaledLikeBounds.contains(it)) {
                        currentReaction = Reaction.LIKE
                        onReactionChanged(currentReaction)

                        return@detectTapGestures
                    }

                    val scaledDislikeBounds = Rect(
                        offset = scaledDislikeTranslation,
                        size = dislikeBounds.size * scaleFactor,
                    )

                    if (currentReaction != Reaction.DISLIKE && scaledDislikeBounds.contains(it)) {
                        currentReaction = Reaction.DISLIKE
                        onReactionChanged(currentReaction)

                        return@detectTapGestures
                    }
                }
            }
    ) {
        scaledLikeTranslation = Offset(
            x = center.x - (likeBounds.width * scaleFactor) - (gap.toPx() / 2F),
            y = center.y - (likeBounds.height * scaleFactor) / 2F,
        )

        scaledDislikeTranslation = Offset(
            x = center.x + (gap.toPx() / 2F),
            y = center.y - (dislikeBounds.height * scaleFactor) / 2F,
        )

        translate(
            left = scaledLikeTranslation.x,
            top = scaledLikeTranslation.y,
        ) {
            scale(
                scale = scaleFactor,
                pivot = likeBounds.topLeft,
            ) {
                drawPath(
                    path = like,
                    color = Color.LightGray,
                )
                clipPath(
                    path = like,
                ) {
                    drawRect(
                        brush = Brush.verticalGradient(
                            tileMode = TileMode.Decal,
                            colors = likeColors,
                            startY = likeBounds.bottomRight.y,
                            endY = likeGradientEndY,
                        ),
                        size = likeBounds.size,
                        topLeft = likeBounds.topLeft,
                    )
                }
            }
        }

        translate(
            left = scaledDislikeTranslation.x,
            top = scaledDislikeTranslation.y,
        ) {
            scale(
                scale = scaleFactor,
                pivot = dislikeBounds.topLeft,
            ) {
                drawPath(
                    path = dislike,
                    color = Color.LightGray,
                )
                clipPath(
                    path = dislike,
                ) {
                    drawRect(
                        brush = Brush.verticalGradient(
                            tileMode = TileMode.Decal,
                            colors = dislikeColors,
                            startY = dislikeBounds.bottomRight.y,
                            endY = dislikeGradientEndY,
                        ),
                        size = dislikeBounds.size,
                        topLeft = dislikeBounds.topLeft,
                    )
                }
            }
        }
    }
}