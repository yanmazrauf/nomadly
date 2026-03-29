package com.nomadly.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.nomadly.app.data.mock.MockRepository
import com.nomadly.app.model.Board
import com.nomadly.app.ui.theme.BrandTeal
import com.nomadly.app.ui.theme.ManropeFontFamily
import com.nomadly.app.ui.theme.NomadlyTheme
import com.nomadly.app.ui.theme.NotoSerifFontFamily
import com.nomadly.app.ui.theme.PrimaryText
import com.nomadly.app.ui.theme.White

@Composable
fun BoardCard(
    board: Board,
    onClick: () -> Unit,
    isTall: Boolean = true
) {
    val cardHeight = if (isTall) 420.dp else 300.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight)
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(48.dp),
                ambientColor = Color(0x0F1C1C18),
                spotColor = Color(0x0F1C1C18)
            )
            .clip(RoundedCornerShape(48.dp))
            .clickable { onClick() }
    ) {
        // Full-bleed image
        AsyncImage(
            model = board.imageUrl,
            contentDescription = board.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Gradient overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.0f),
                            Color.Black.copy(alpha = 0.55f)
                        ),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )

        // Info panel at bottom
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(White.copy(alpha = 0.88f))
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = board.title,
                            fontFamily = NotoSerifFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = PrimaryText,
                            lineHeight = 26.sp
                        )
                        Text(
                            text = "${board.destinationCount} DESTINATIONS",
                            fontFamily = ManropeFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 11.sp,
                            color = BrandTeal,
                            letterSpacing = 1.4.sp
                        )
                    }

                    // Overlapping avatars
                    if (board.collaboratorAvatars.isNotEmpty()) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy((-10).dp)
                        ) {
                            board.collaboratorAvatars.take(3).forEachIndexed { index, avatarUrl ->
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .border(2.dp, BrandTeal, CircleShape)
                                        .clip(CircleShape)
                                ) {
                                    AsyncImage(
                                        model = avatarUrl,
                                        contentDescription = "Collaborator",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }
                            if (board.extraCollaborators > 0) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(BrandTeal),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "+${board.extraCollaborators}",
                                        fontFamily = ManropeFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 9.sp,
                                        color = White
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F2)
@Composable
fun BoardCardPreview() {
    NomadlyTheme {
        BoardCard(
            board = MockRepository.boards.first(),
            onClick = {},
            isTall = true
        )
    }
}
