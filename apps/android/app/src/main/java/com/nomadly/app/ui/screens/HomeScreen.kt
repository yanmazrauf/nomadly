package com.nomadly.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nomadly.app.model.Destination
import com.nomadly.app.navigation.Screen
import com.nomadly.app.ui.components.NomadlyBottomNav
import com.nomadly.app.ui.components.SwipeActionButtons
import com.nomadly.app.ui.components.SwipeDeck
import com.nomadly.app.ui.components.rememberSwipeDeckState
import com.nomadly.app.ui.theme.BrandTeal
import com.nomadly.app.ui.theme.Cream
import com.nomadly.app.ui.theme.CreamSurface
import com.nomadly.app.ui.theme.Destructive
import com.nomadly.app.ui.theme.ManropeFontFamily
import com.nomadly.app.ui.theme.NomadlyTheme
import com.nomadly.app.ui.theme.NotoSerifFontFamily
import com.nomadly.app.ui.theme.PrimaryText
import com.nomadly.app.ui.theme.SecondaryText
import com.nomadly.app.ui.theme.White

private val CATEGORIES = listOf(
    "All", "Coastal", "Romantic", "Culture",
    "Adventure", "Luxury", "Spiritual", "Nature"
)

@Composable
fun HomeScreen(
    navController: NavController,
    onDestinationClick: (Destination) -> Unit,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
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
            when (val state = uiState) {
                is HomeUiState.Loading -> HomeLoadingContent()

                is HomeUiState.Error -> HomeErrorContent(
                    message = state.message,
                    onRetry = viewModel::retry
                )

                is HomeUiState.Success -> HomeSuccessContent(
                    uiState        = state,
                    swipeDeckState = swipeDeckState,
                    onCategorySelected = viewModel::selectCategory,
                    onRemainingChanged = viewModel::onRemainingChanged,
                    onSwipedLeft   = viewModel::onSwipedLeft,
                    onSwipedRight  = { dest ->
                        viewModel.onSwipedRight(dest)
                        onDestinationClick(dest)
                    },
                    onCardClick    = onDestinationClick,
                    onDislike      = { swipeDeckState.swipeLeft() },
                    onLike         = { swipeDeckState.swipeRight() }
                )
            }
        }
    }
}

// ── Sub-composables ──────────────────────────────────────────────────────────

@Composable
private fun HomeLoadingContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = BrandTeal)
    }
}

@Composable
private fun HomeErrorContent(message: String, onRetry: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize().padding(32.dp), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Something went wrong",
                fontFamily = NotoSerifFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = PrimaryText
            )
            Text(
                text = message,
                fontFamily = ManropeFontFamily,
                fontSize = 14.sp,
                color = SecondaryText
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(BrandTeal)
                    .clickable { onRetry() }
                    .padding(horizontal = 28.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "Try again",
                    fontFamily = ManropeFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = White
                )
            }
        }
    }
}

@Composable
private fun ColumnScope.HomeSuccessContent(
    uiState: HomeUiState.Success,
    swipeDeckState: com.nomadly.app.ui.components.SwipeDeckState,
    onCategorySelected: (String) -> Unit,
    onRemainingChanged: (Int) -> Unit,
    onSwipedLeft: (Destination) -> Unit,
    onSwipedRight: (Destination) -> Unit,
    onCardClick: (Destination) -> Unit,
    onDislike: () -> Unit,
    onLike: () -> Unit
) {
    // ── Header ────────────────────────────────────────────────────────────────
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

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(999.dp))
                .background(BrandTeal.copy(alpha = 0.10f))
                .padding(horizontal = 14.dp, vertical = 7.dp)
        ) {
            Text(
                text = "${uiState.remainingCount} left",
                fontFamily = ManropeFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = BrandTeal
            )
        }
    }

    // ── Category chips ────────────────────────────────────────────────────────
    LazyRow(
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(CATEGORIES) { category ->
            val isSelected = category == uiState.selectedCategory
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(if (isSelected) BrandTeal else CreamSurface)
                    .clickable { onCategorySelected(category) }
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

    // ── Swipe deck ────────────────────────────────────────────────────────────
    key(uiState.selectedCategory) {
        SwipeDeck(
            destinations       = uiState.filteredDestinations,
            state              = swipeDeckState,
            onSwipedLeft       = onSwipedLeft,
            onSwipedRight      = onSwipedRight,
            onCardClick        = onCardClick,
            onRemainingChanged = onRemainingChanged,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 20.dp)
        )
    }

    Spacer(modifier = Modifier.height(20.dp))

    // ── Action buttons ────────────────────────────────────────────────────────
    SwipeActionButtons(
        onDislike = onDislike,
        onLike    = onLike
    )

    Spacer(modifier = Modifier.height(24.dp))
}

// ── Preview ──────────────────────────────────────────────────────────────────

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
