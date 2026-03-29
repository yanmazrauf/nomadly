package com.nomadly.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.nomadly.app.ui.components.NomadlyTopBar
import com.nomadly.app.ui.components.SwipeActionButtons
import com.nomadly.app.ui.components.SwipeDeck
import com.nomadly.app.ui.theme.BrandTeal
import com.nomadly.app.ui.theme.Cream
import com.nomadly.app.ui.theme.ManropeFontFamily
import com.nomadly.app.ui.theme.NomadlyTheme
import com.nomadly.app.ui.theme.NotoSerifFontFamily
import com.nomadly.app.ui.theme.PrimaryText
import com.nomadly.app.ui.theme.SecondaryText

@Composable
fun HomeScreen(
    navController: NavController,
    onDestinationClick: (Destination) -> Unit
) {
    val currentRoute = Screen.Home.route
    var currentIndex by remember { mutableIntStateOf(0) }
    val destinations = remember { MockRepository.destinations }
    var triggerSwipeLeft by remember { mutableStateOf(false) }
    var triggerSwipeRight by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Cream,
        topBar = {
            NomadlyTopBar(
                title = "Nomadly",
                onMenuClick = {},
                onNotificationClick = {}
            )
        },
        bottomBar = {
            NomadlyBottomNav(
                currentRoute = currentRoute,
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
                .padding(innerPadding)
                .background(Cream)
                .padding(horizontal = 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Discover",
                        fontFamily = NotoSerifFontFamily,
                        fontWeight = FontWeight.Black,
                        fontSize = 28.sp,
                        color = PrimaryText,
                        letterSpacing = (-0.5).sp
                    )
                    Text(
                        text = "Swipe to explore destinations",
                        fontFamily = ManropeFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = SecondaryText
                    )
                }
                val remaining = (destinations.size - currentIndex).coerceAtLeast(0)
                Text(
                    text = "$remaining left",
                    fontFamily = ManropeFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    color = BrandTeal
                )
            }

            // SwipeDeck
            SwipeDeck(
                destinations = destinations,
                onSwipedLeft = { _ ->
                    currentIndex++
                },
                onSwipedRight = { destination ->
                    currentIndex++
                    onDestinationClick(destination)
                },
                onCardClick = { destination ->
                    onDestinationClick(destination)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Swipe action buttons
            SwipeActionButtons(
                onDislike = {
                    // Trigger left swipe programmatically (index advances via SwipeDeck)
                    currentIndex = (currentIndex + 1).coerceAtMost(destinations.size)
                },
                onLike = {
                    if (currentIndex < destinations.size) {
                        onDestinationClick(destinations[currentIndex])
                        currentIndex = (currentIndex + 1).coerceAtMost(destinations.size)
                    }
                }
            )

            Spacer(modifier = Modifier.height(20.dp))
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
