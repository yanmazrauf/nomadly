package com.nomadly.app.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.nomadly.app.data.mock.MockRepository
import com.nomadly.app.model.Destination
import com.nomadly.app.ui.theme.AccentCyan
import com.nomadly.app.ui.theme.BrandTeal
import com.nomadly.app.ui.theme.CreamSurface
import com.nomadly.app.ui.theme.Destructive
import com.nomadly.app.ui.theme.ManropeFontFamily
import com.nomadly.app.ui.theme.NomadlyTheme
import com.nomadly.app.ui.theme.NotoSerifFontFamily
import com.nomadly.app.ui.theme.White
import kotlinx.coroutines.launch

private const val SWIPE_THRESHOLD = 320f
private const val FLY_OFF_TARGET = 1800f

// ─── State ───────────────────────────────────────────────────────────────────

class SwipeDeckState {
    var triggerLeft by mutableIntStateOf(0)
        private set
    var triggerRight by mutableIntStateOf(0)
        private set

    fun swipeLeft() { triggerLeft++ }
    fun swipeRight() { triggerRight++ }
}

@Composable
fun rememberSwipeDeckState(): SwipeDeckState = remember { SwipeDeckState() }

// ─── SwipeDeck ───────────────────────────────────────────────────────────────

@Composable
fun SwipeDeck(
    destinations: List<Destination>,
    onSwipedLeft: (Destination) -> Unit,
    onSwipedRight: (Destination) -> Unit,
    onCardClick: (Destination) -> Unit,
    modifier: Modifier = Modifier,
    state: SwipeDeckState? = null,
    onRemainingChanged: (Int) -> Unit = {}
) {
    var currentIndex by remember { mutableIntStateOf(0) }
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    // Report remaining count whenever index changes
    LaunchedEffect(currentIndex, destinations.size) {
        onRemainingChanged((destinations.size - currentIndex).coerceAtLeast(0))
    }

    // Programmatic swipe left (e.g. from dislike button)
    LaunchedEffect(state?.triggerLeft) {
        val trigger = state?.triggerLeft ?: return@LaunchedEffect
        if (trigger > 0 && currentIndex < destinations.size) {
            val destination = destinations[currentIndex]
            offsetX.animateTo(-FLY_OFF_TARGET, tween(280))
            onSwipedLeft(destination)
            currentIndex++
            offsetX.snapTo(0f)
            offsetY.snapTo(0f)
        }
    }

    // Programmatic swipe right (e.g. from like button)
    LaunchedEffect(state?.triggerRight) {
        val trigger = state?.triggerRight ?: return@LaunchedEffect
        if (trigger > 0 && currentIndex < destinations.size) {
            val destination = destinations[currentIndex]
            offsetX.animateTo(FLY_OFF_TARGET, tween(280))
            onSwipedRight(destination)
            currentIndex++
            offsetX.snapTo(0f)
            offsetY.snapTo(0f)
        }
    }

    val visibleDestinations = remember(currentIndex) {
        destinations.drop(currentIndex).take(3)
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        if (currentIndex >= destinations.size) {
            AllSwipedEmptyState()
        } else {
            val swipeProgress = (offsetX.value / SWIPE_THRESHOLD).coerceIn(-1f, 1f)

            visibleDestinations.asReversed().forEachIndexed { reverseIndex, destination ->
                val stackIndex = visibleDestinations.size - 1 - reverseIndex
                val isTopCard = stackIndex == 0

                val scale = when (stackIndex) {
                    0 -> 1f
                    1 -> 0.95f
                    else -> 0.90f
                }
                val translateYDp = when (stackIndex) {
                    0 -> 0f
                    1 -> 16f
                    else -> 32f
                }
                val alpha = when (stackIndex) {
                    0 -> 1f
                    1 -> 0.88f
                    else -> 0.72f
                }

                key(currentIndex + stackIndex) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(0.70f)
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                                translationY = translateYDp.dp.toPx()
                                this.alpha = alpha
                                if (isTopCard) {
                                    translationX = offsetX.value
                                    translationY += offsetY.value * 0.4f
                                    rotationZ = offsetX.value * 0.035f
                                }
                            }
                            .then(
                                if (isTopCard) {
                                    Modifier.pointerInput(currentIndex) {
                                        detectDragGestures(
                                            onDragEnd = {
                                                scope.launch {
                                                    when {
                                                        offsetX.value > SWIPE_THRESHOLD -> {
                                                            offsetX.animateTo(FLY_OFF_TARGET, tween(280))
                                                            onSwipedRight(destination)
                                                            currentIndex++
                                                            offsetX.snapTo(0f)
                                                            offsetY.snapTo(0f)
                                                        }
                                                        offsetX.value < -SWIPE_THRESHOLD -> {
                                                            offsetX.animateTo(-FLY_OFF_TARGET, tween(280))
                                                            onSwipedLeft(destination)
                                                            currentIndex++
                                                            offsetX.snapTo(0f)
                                                            offsetY.snapTo(0f)
                                                        }
                                                        else -> {
                                                            launch {
                                                                offsetX.animateTo(
                                                                    0f,
                                                                    spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessMediumLow)
                                                                )
                                                            }
                                                            launch {
                                                                offsetY.animateTo(
                                                                    0f,
                                                                    spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessMediumLow)
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            },
                                            onDrag = { change, dragAmount ->
                                                change.consume()
                                                scope.launch {
                                                    offsetX.snapTo(offsetX.value + dragAmount.x)
                                                    offsetY.snapTo(offsetY.value + dragAmount.y)
                                                }
                                            }
                                        )
                                    }
                                } else Modifier
                            )
                    ) {
                        DestinationCardContent(
                            destination = destination,
                            onClick = { if (isTopCard) onCardClick(destination) },
                            swipeProgress = if (isTopCard) swipeProgress else 0f
                        )
                    }
                }
            }
        }
    }
}

// ─── Card Content ─────────────────────────────────────────────────────────────

@Composable
fun DestinationCardContent(
    destination: Destination,
    onClick: () -> Unit,
    swipeProgress: Float = 0f
) {
    val saveAlpha = (swipeProgress * 2.5f).coerceIn(0f, 1f)
    val skipAlpha = (-swipeProgress * 2.5f).coerceIn(0f, 1f)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .shadow(
                elevation = 20.dp,
                shape = RoundedCornerShape(44.dp),
                ambientColor = Color(0x141C1C18),
                spotColor = Color(0x1A1C1C18)
            )
            .clip(RoundedCornerShape(44.dp))
            .clickable { onClick() }
    ) {
        // Full-bleed photo
        AsyncImage(
            model = destination.imageUrl,
            contentDescription = destination.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Bottom gradient scrim
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colorStops = arrayOf(
                            0.0f to Color.Transparent,
                            0.45f to Color.Transparent,
                            0.70f to Color.Black.copy(alpha = 0.20f),
                            0.85f to Color.Black.copy(alpha = 0.55f),
                            1.0f to Color.Black.copy(alpha = 0.80f)
                        )
                    )
                )
        )

        // SAVE overlay (swiping right)
        if (saveAlpha > 0.01f) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(28.dp)
                    .rotate(-18f)
                    .alpha(saveAlpha)
                    .border(3.dp, BrandTeal, RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "SAVE",
                    fontFamily = ManropeFontFamily,
                    fontWeight = FontWeight.Black,
                    fontSize = 26.sp,
                    color = BrandTeal,
                    letterSpacing = 2.sp
                )
            }
        }

        // SKIP overlay (swiping left)
        if (skipAlpha > 0.01f) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(28.dp)
                    .rotate(18f)
                    .alpha(skipAlpha)
                    .border(3.dp, Destructive, RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "SKIP",
                    fontFamily = ManropeFontFamily,
                    fontWeight = FontWeight.Black,
                    fontSize = 26.sp,
                    color = Destructive,
                    letterSpacing = 2.sp
                )
            }
        }

        // Card info (bottom)
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(horizontal = 28.dp, vertical = 30.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Tags
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                destination.tags.take(2).forEach { tag ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(999.dp))
                            .background(White.copy(alpha = 0.18f))
                            .border(1.dp, White.copy(alpha = 0.30f), RoundedCornerShape(999.dp))
                            .padding(horizontal = 12.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = tag,
                            fontFamily = ManropeFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 11.sp,
                            color = White,
                            letterSpacing = 0.4.sp
                        )
                    }
                }
            }

            // Destination name
            Text(
                text = destination.name,
                fontFamily = NotoSerifFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = White,
                lineHeight = 34.sp,
                letterSpacing = (-0.5).sp
            )

            // Region, Country
            Text(
                text = "${destination.region}, ${destination.country}",
                fontFamily = ManropeFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = White.copy(alpha = 0.75f),
                letterSpacing = 0.2.sp
            )
        }
    }
}

// ─── Empty State ─────────────────────────────────────────────────────────────

@Composable
private fun AllSwipedEmptyState() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.70f)
            .clip(RoundedCornerShape(44.dp))
            .background(CreamSurface),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                text = "✦",
                fontSize = 40.sp,
                color = BrandTeal.copy(alpha = 0.3f)
            )
            Text(
                text = "You've explored them all",
                style = MaterialTheme.typography.headlineSmall,
                color = BrandTeal
            )
            Text(
                text = "Check your Saved boards\nfor your favourites.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

// ─── Action Buttons ──────────────────────────────────────────────────────────

@Composable
fun SwipeActionButtons(
    onDislike: () -> Unit,
    onLike: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Dislike
        Box(
            modifier = Modifier
                .size(60.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = CircleShape,
                    ambientColor = Destructive.copy(alpha = 0.15f),
                    spotColor = Destructive.copy(alpha = 0.20f)
                )
                .clip(CircleShape)
                .background(White)
                .border(1.5.dp, Destructive.copy(alpha = 0.25f), CircleShape)
                .clickable { onDislike() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Skip",
                tint = Destructive,
                modifier = Modifier.size(26.dp)
            )
        }

        // Like
        Box(
            modifier = Modifier
                .size(72.dp)
                .shadow(
                    elevation = 12.dp,
                    shape = CircleShape,
                    ambientColor = BrandTeal.copy(alpha = 0.22f),
                    spotColor = BrandTeal.copy(alpha = 0.28f)
                )
                .clip(CircleShape)
                .background(BrandTeal)
                .clickable { onLike() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Save",
                tint = AccentCyan,
                modifier = Modifier.size(30.dp)
            )
        }

        // Dislike mirror (spacing balance — invisible placeholder or a "super like" / info icon)
        Box(
            modifier = Modifier
                .size(60.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = CircleShape,
                    ambientColor = Color(0x10000000),
                    spotColor = Color(0x10000000)
                )
                .clip(CircleShape)
                .background(White)
                .border(1.5.dp, BrandTeal.copy(alpha = 0.20f), CircleShape)
                .clickable { /* info / super-like */ },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "✦",
                fontSize = 20.sp,
                color = BrandTeal.copy(alpha = 0.7f)
            )
        }
    }
}

// ─── Previews ─────────────────────────────────────────────────────────────────

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F2)
@Composable
fun SwipeDeckPreview() {
    NomadlyTheme {
        SwipeDeck(
            destinations = MockRepository.destinations,
            onSwipedLeft = {},
            onSwipedRight = {},
            onCardClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F2)
@Composable
fun SwipeActionButtonsPreview() {
    NomadlyTheme {
        SwipeActionButtons(
            onDislike = {},
            onLike = {}
        )
    }
}
