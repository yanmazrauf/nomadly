package com.nomadly.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.nomadly.app.data.mock.MockRepository
import com.nomadly.app.model.Destination
import com.nomadly.app.ui.components.PrimaryButton
import com.nomadly.app.ui.components.StyleChip
import com.nomadly.app.ui.theme.AccentCyan
import com.nomadly.app.ui.theme.BrandTeal
import com.nomadly.app.ui.theme.Cream
import com.nomadly.app.ui.theme.CreamSurface
import com.nomadly.app.ui.theme.ManropeFontFamily
import com.nomadly.app.ui.theme.NomadlyTheme
import com.nomadly.app.ui.theme.NotoSerifFontFamily
import com.nomadly.app.ui.theme.PrimaryText
import com.nomadly.app.ui.theme.RustOrange
import com.nomadly.app.ui.theme.SecondaryText
import com.nomadly.app.ui.theme.White

@Composable
fun DestinationDetailScreen(
    destination: Destination,
    onBack: () -> Unit,
    viewModel: DestinationDetailViewModel = viewModel(
        key     = destination.id,
        factory = DestinationDetailViewModel.provideFactory(destination)
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Cast to Success — VM is initialised with Success so this is always valid here
    val state = uiState as? DestinationDetailUiState.Success
        ?: DestinationDetailUiState.Success(destination, destination.isSaved)

    DestinationDetailContent(
        destination = state.destination,
        isSaved     = state.isSaved,
        onBack      = onBack,
        onToggleSaved = viewModel::toggleSaved
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DestinationDetailContent(
    destination: Destination,
    isSaved: Boolean,
    onBack: () -> Unit,
    onToggleSaved: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().background(Cream)) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {

            // ── Hero ─────────────────────────────────────────────────────────
            item {
                Box(modifier = Modifier.fillMaxWidth().height(400.dp)) {
                    AsyncImage(
                        model = destination.imageUrl,
                        contentDescription = destination.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Gradient scrim
                    Box(
                        modifier = Modifier.fillMaxSize().background(
                            Brush.verticalGradient(
                                colorStops = arrayOf(
                                    0.0f to Color.Black.copy(alpha = 0.25f),
                                    0.25f to Color.Transparent,
                                    0.55f to Color.Transparent,
                                    0.78f to Color.Black.copy(alpha = 0.40f),
                                    1.0f  to Color.Black.copy(alpha = 0.70f)
                                )
                            )
                        )
                    )

                    // Back button
                    Box(
                        modifier = Modifier
                            .statusBarsPadding()
                            .padding(16.dp)
                            .size(44.dp)
                            .shadow(4.dp, CircleShape, ambientColor = Color(0x20000000))
                            .clip(CircleShape)
                            .background(White.copy(alpha = 0.85f))
                            .clickable { onBack() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = PrimaryText, modifier = Modifier.size(20.dp))
                    }

                    // Bookmark button
                    Box(
                        modifier = Modifier
                            .statusBarsPadding()
                            .align(Alignment.TopEnd)
                            .padding(16.dp)
                            .size(44.dp)
                            .shadow(4.dp, CircleShape, ambientColor = Color(0x20000000))
                            .clip(CircleShape)
                            .background(if (isSaved) BrandTeal else White.copy(alpha = 0.85f))
                            .clickable { onToggleSaved() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isSaved) Icons.Filled.Bookmark else Icons.Outlined.Bookmark,
                            contentDescription = if (isSaved) "Saved" else "Save",
                            tint = if (isSaved) AccentCyan else PrimaryText,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Hero labels
                    Column(
                        modifier = Modifier.align(Alignment.BottomStart).padding(24.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(999.dp))
                                .background(AccentCyan)
                                .padding(horizontal = 14.dp, vertical = 5.dp)
                        ) {
                            Text(
                                text = "${destination.region}, ${destination.country}".uppercase(),
                                fontFamily = ManropeFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                color = BrandTeal,
                                letterSpacing = 1.4.sp
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = destination.name,
                            fontFamily = NotoSerifFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 36.sp,
                            color = White,
                            lineHeight = 40.sp,
                            letterSpacing = (-0.5).sp
                        )
                    }
                }
            }

            // ── Body ─────────────────────────────────────────────────────────
            item {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(top = 24.dp)
                ) {
                    // Rating
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(Icons.Default.Star, "Rating", tint = Color(0xFFFFB547), modifier = Modifier.size(18.dp))
                        Text(destination.rating.toString(), fontFamily = ManropeFontFamily, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = PrimaryText)
                        Text("·", fontSize = 15.sp, color = SecondaryText.copy(alpha = 0.4f))
                        Text("${destination.reviewCount} reviews", fontFamily = ManropeFontFamily, fontSize = 13.sp, color = SecondaryText)
                    }

                    Spacer(Modifier.height(16.dp))

                    // Tags
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        destination.tags.forEach { tag -> StyleChip(label = tag, isActive = false) }
                    }

                    Spacer(Modifier.height(24.dp))

                    // Tagline
                    Text(destination.tagline, fontFamily = NotoSerifFontFamily, fontWeight = FontWeight.Bold, fontSize = 19.sp, color = BrandTeal, lineHeight = 26.sp)
                    Spacer(Modifier.height(12.dp))

                    SectionLabel("Why Visit")
                    Spacer(Modifier.height(8.dp))
                    Text(destination.description, fontFamily = ManropeFontFamily, fontSize = 15.sp, color = SecondaryText, lineHeight = 24.sp)

                    Spacer(Modifier.height(28.dp))

                    SectionLabel("Highlights")
                    Spacer(Modifier.height(12.dp))
                    destination.highlights.forEach { highlight ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Box(modifier = Modifier.padding(top = 7.dp).size(6.dp).clip(CircleShape).background(BrandTeal))
                            Text(highlight, fontFamily = ManropeFontFamily, fontSize = 15.sp, color = PrimaryText, lineHeight = 22.sp)
                        }
                    }

                    Spacer(Modifier.height(28.dp))

                    // Info cards
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        InfoCard(
                            icon = { Icon(Icons.Default.CalendarMonth, null, tint = BrandTeal, modifier = Modifier.size(15.dp)) },
                            label = "BEST TIME",
                            value = destination.bestTimeToVisit,
                            modifier = Modifier.weight(1f)
                        )
                        InfoCard(
                            icon = { Icon(Icons.Default.Wallet, null, tint = RustOrange, modifier = Modifier.size(15.dp)) },
                            label = "AVG BUDGET",
                            value = destination.avgBudget,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(Modifier.height(100.dp))
                }
            }
        }

        // Sticky bottom CTA
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Brush.verticalGradient(colors = listOf(Color.Transparent, Cream, Cream)))
                .navigationBarsPadding()
                .padding(horizontal = 24.dp, vertical = 20.dp)
        ) {
            PrimaryButton(
                text = if (isSaved) "SAVED ✓" else "SAVE TO BOARD",
                onClick = onToggleSaved,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text.uppercase(),
        fontFamily = ManropeFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 10.sp,
        color = SecondaryText.copy(alpha = 0.5f),
        letterSpacing = 2.sp
    )
}

@Composable
private fun InfoCard(
    icon: @Composable () -> Unit,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.clip(RoundedCornerShape(20.dp)).background(CreamSurface).padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            icon()
            Text(label, fontFamily = ManropeFontFamily, fontWeight = FontWeight.Bold, fontSize = 9.sp, color = SecondaryText, letterSpacing = 1.2.sp)
        }
        Spacer(Modifier.height(8.dp))
        Text(value, fontFamily = ManropeFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = PrimaryText, lineHeight = 18.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun DestinationDetailScreenPreview() {
    NomadlyTheme {
        DestinationDetailScreen(
            destination = MockRepository.destinations.first(),
            onBack = {}
        )
    }
}
