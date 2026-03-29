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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
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

@Composable
fun SwipeDeck(
    destinations: List<Destination>,
    onSwipedLeft: (Destination) -> Unit,
    onSwipedRight: (Destination) -> Unit,
    onCardClick: (Destination) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentIndex by remember { mutableIntStateOf(0) }
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    val visibleDestinations = remember(currentIndex) {
        destinations.drop(currentIndex).take(3)
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        if (currentIndex >= destinations.size) {
            // All swiped — show end state
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.72f)
                    .clip(RoundedCornerShape(48.dp))
                    .background(CreamSurface),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "You've explored them all!",
                        style = MaterialTheme.typography.headlineSmall,
                        color = BrandTeal
                    )
                    Text(
                        text = "Check your saved boards for your favourites.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            visibleDestinations.asReversed().forEachIndexed { reverseIndex, destination ->
                val stackIndex = visibleDestinations.size - 1 - reverseIndex
                val isTopCard = stackIndex == 0

                val scale = when (stackIndex) {
                    0 -> 1f
                    1 -> 0.95f
                    else -> 0.90f
                }
                val translationYDp = when (stackIndex) {
                    0 -> 0f
                    1 -> -20f
                    else -> -40f
                }
                val alpha = when (stackIndex) {
                    0 -> 1f
                    1 -> 0.85f
                    else -> 0.70f
                }

                key(currentIndex + stackIndex) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(0.72f)
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                                translationY = translationYDp.dp.toPx()
                                this.alpha = alpha
                                if (isTopCard) {
                                    translationX = offsetX.value
                                    translationY += offsetY.value
                                    rotationZ = offsetX.value * 0.04f
                                }
                            }
                            .then(
                                if (isTopCard) {
                                    Modifier.pointerInput(currentIndex) {
                                        detectDragGestures(
                                            onDragEnd = {
                                                scope.launch {
                                                    val threshold = 400f
                                                    when {
                                                        offsetX.value > threshold -> {
                                                            offsetX.animateTo(
                                                                2000f,
                                                                tween(300)
                                                            )
                                                            onSwipedRight(destination)
                                                            currentIndex++
                                                            offsetX.snapTo(0f)
                                                            offsetY.snapTo(0f)
                                                        }

                                                        offsetX.value < -threshold -> {
                                                            offsetX.animateTo(
                                                                -2000f,
                                                                tween(300)
                                                            )
                                                            onSwipedLeft(destination)
                                                            currentIndex++
                                                            offsetX.snapTo(0f)
                                                            offsetY.snapTo(0f)
                                                        }

                                                        else -> {
                                                            launch {
                                                                offsetX.animateTo(
                                                                    0f,
                                                                    spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                                                                )
                                                            }
                                                            launch {
                                                                offsetY.animateTo(
                                                                    0f,
                                                                    spring(dampingRatio = Spring.DampingRatioMediumBouncy)
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
                            onClick = { if (isTopCard) onCardClick(destination) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DestinationCardContent(
    destination: Destination,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(48.dp),
                ambientColor = Color(0x1A1C1C18),
                spotColor = Color(0x1A1C1C18)
            )
            .clip(RoundedCornerShape(48.dp))
            .clickable { onClick() }
    ) {
        // Full-bleed image
        AsyncImage(
            model = destination.imageUrl,
            contentDescription = destination.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Gradient overlay bottom-to-top
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.25f),
                            Color.Black.copy(alpha = 0.70f)
                        )
                    )
                )
        )

        // Content at the bottom
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(horizontal = 28.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Tags row
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                destination.tags.take(2).forEach { tag ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(999.dp))
                            .background(White.copy(alpha = 0.20f))
                            .border(1.dp, White.copy(alpha = 0.35f), RoundedCornerShape(999.dp))
                            .padding(horizontal = 14.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = tag,
                            fontFamily = ManropeFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 11.sp,
                            color = White,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }

            // Destination name
            Text(
                text = destination.name,
                fontFamily = NotoSerifFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = White,
                lineHeight = 36.sp,
                letterSpacing = (-0.5).sp
            )

            // Region, Country
            Text(
                text = "${destination.region}, ${destination.country}",
                fontFamily = ManropeFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
                color = White.copy(alpha = 0.80f),
                letterSpacing = 0.3.sp
            )
        }
    }
}

@Composable
fun SwipeActionButtons(
    onDislike: () -> Unit,
    onLike: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Dislike button
        Box(
            modifier = Modifier
                .size(64.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = CircleShape,
                    ambientColor = Destructive.copy(alpha = 0.2f),
                    spotColor = Destructive.copy(alpha = 0.2f)
                )
                .clip(CircleShape)
                .background(White)
                .border(1.5.dp, Destructive.copy(alpha = 0.3f), CircleShape)
                .clickable { onDislike() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Skip",
                tint = Destructive,
                modifier = Modifier.size(28.dp)
            )
        }

        // Like button
        Box(
            modifier = Modifier
                .size(64.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = CircleShape,
                    ambientColor = BrandTeal.copy(alpha = 0.25f),
                    spotColor = BrandTeal.copy(alpha = 0.25f)
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
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F2)
@Composable
fun SwipeDeckPreview() {
    NomadlyTheme {
        SwipeDeck(
            destinations = MockRepository.destinations,
            onSwipedLeft = {},
            onSwipedRight = {},
            onCardClick = {}
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
