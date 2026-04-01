package com.nomadly.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nomadly.app.data.mock.MockRepository
import com.nomadly.app.model.Destination
import com.nomadly.app.navigation.Screen
import com.nomadly.app.ui.components.NomadlyBottomNav
import com.nomadly.app.ui.components.SwipeActionButtons
import com.nomadly.app.ui.components.SwipeDeck
import com.nomadly.app.ui.components.rememberSwipeDeckState
import com.nomadly.app.ui.theme.BrandTeal
import com.nomadly.app.ui.theme.Cream
import com.nomadly.app.ui.theme.CreamSurface
import com.nomadly.app.ui.theme.ManropeFontFamily
import com.nomadly.app.ui.theme.NomadlyTheme
import com.nomadly.app.ui.theme.NotoSerifFontFamily
import com.nomadly.app.ui.theme.PrimaryText
import com.nomadly.app.ui.theme.SecondaryText
import com.nomadly.app.ui.theme.White

private val CATEGORIES = listOf("All", "Coastal", "Romantic", "Culture", "Adventure", "Luxury", "Spiritual", "Nature")

@Composable
fun HomeScreen(
    navController: NavController,
    onDestinationClick: (Destination) -> Unit
) {
    val allDestinations = remember { MockRepository.destinations }
    var selectedCategory by remember { mutableStateOf("All") }
    var remaining by remember { mutableIntStateOf(allDestinations.size) }

    val filteredDestinations = remember(selectedCategory) {
        if (selectedCategory == "All") allDestinations
        else allDestinations.filter { dest ->
            dest.tags.any { it.contains(selectedCategory, ignoreCase = true) }
        }
    }

    LaunchedEffect(selectedCategory) {
        remaining = filteredDestinations.size
    }

    val swipeDeckState = rememberSwipeDeckState()

    Scaffold(
        containerColor = Cream,
        topBar = {},
        bottomBar = {
            NomadlyBottomNav(
                currentRoute = Screen.Home.route,
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(Screen.Home.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
                .background(Cream)
                .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ── Header ──────────────────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(top = 20.dp, bottom = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = "Discover",
                        fontFamily = NotoSerifFontFamily,
                        fontWeight = FontWeight.Black,
                        fontSize = 30.sp,
                        color = PrimaryText,
                        letterSpacing = (-0.8).sp
                    )
                    Text(
                        text = "Swipe to explore destinations",
                        fontFamily = ManropeFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 13.sp,
                        color = SecondaryText.copy(alpha = 0.7f)
                    )
                }

                // Remaining count badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(BrandTeal.copy(alpha = 0.10f))
                        .padding(horizontal = 14.dp, vertical = 7.dp)
                ) {
                    Text(
                        text = "$remaining left",
                        fontFamily = ManropeFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = BrandTeal
                    )
                }
            }

            // ── Category chips ───────────────────────────────────────────────
            LazyRow(
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(CATEGORIES) { category ->
                    val isSelected = category == selectedCategory
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(999.dp))
                            .background(if (isSelected) BrandTeal else CreamSurface)
                            .clickable { selectedCategory = category }
                            .padding(horizontal = 18.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = category,
                            fontFamily = ManropeFontFamily,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 13.sp,
                            color = if (isSelected) White else SecondaryText
                        )
                    }
                }
            }

            // ── Swipe Deck ───────────────────────────────────────────────────
            key(selectedCategory) {
                SwipeDeck(
                    destinations = filteredDestinations,
                    state = swipeDeckState,
                    onSwipedLeft = { },
                    onSwipedRight = { destination -> onDestinationClick(destination) },
                    onCardClick = { destination -> onDestinationClick(destination) },
                    onRemainingChanged = { remaining = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 20.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ── Action Buttons ───────────────────────────────────────────────
            SwipeActionButtons(
                onDislike = { swipeDeckState.swipeLeft() },
                onLike = { swipeDeckState.swipeRight() }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    NomadlyTheme {
        HomeScreen(
            navController = rememberNavController(),
            onDestinationClick = {}
        )
    }
}
