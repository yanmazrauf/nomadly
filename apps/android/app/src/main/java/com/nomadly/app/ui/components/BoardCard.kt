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
    isTall: Boolean = true,
    modifier: Modifier = Modifier
) {
    val cardHeight = if (isTall) 300.dp else 220.dp

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(cardHeight)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(36.dp),
                ambientColor = Color(0x0C1C1C18),
                spotColor = Color(0x10000000)
            )
            .clip(RoundedCornerShape(36.dp))
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
                        colorStops = arrayOf(
                            0.0f to Color.Transparent,
                            0.4f to Color.Black.copy(alpha = 0.05f),
                            1.0f to Color.Black.copy(alpha = 0.62f)
                        )
                    )
                )
        )

        // Info panel
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(12.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(White.copy(alpha = 0.90f))
                .padding(horizontal = 16.dp, vertical = 14.dp)
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
                            fontSize = 16.sp,
                            color = PrimaryText,
                            lineHeight = 22.sp
                        )
                        Text(
                            text = "${board.destinationCount} DESTINATIONS",
                            fontFamily = ManropeFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 10.sp,
                            color = BrandTeal,
                            letterSpacing = 1.2.sp
                        )
                    }

                    // Overlapping collaborator avatars
                    if (board.collaboratorAvatars.isNotEmpty()) {
                        Row(horizontalArrangement = Arrangement.spacedBy((-9).dp)) {
                            board.collaboratorAvatars.take(3).forEach { avatarUrl ->
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .border(1.5.dp, White, CircleShape)
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
                                        .size(28.dp)
                                        .clip(CircleShape)
                                        .background(BrandTeal),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "+${board.extraCollaborators}",
                                        fontFamily = ManropeFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 8.sp,
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
fun BoardCardTallPreview() {
    NomadlyTheme {
        BoardCard(
            board = MockRepository.boards.first(),
            onClick = {},
            isTall = true
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F2)
@Composable
fun BoardCardShortPreview() {
    NomadlyTheme {
        BoardCard(
            board = MockRepository.boards[1],
            onClick = {},
            isTall = false
        )
    }
}
