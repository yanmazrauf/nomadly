package com.nomadly.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.nomadly.app.data.mock.MockRepository
import com.nomadly.app.navigation.Screen
import com.nomadly.app.ui.components.NomadlyBottomNav
import com.nomadly.app.ui.components.NomadlyTopBar
import com.nomadly.app.ui.components.StatCard
import com.nomadly.app.ui.components.StyleChip
import com.nomadly.app.ui.theme.AccentCyan
import com.nomadly.app.ui.theme.BrandTeal
import com.nomadly.app.ui.theme.Cream
import com.nomadly.app.ui.theme.CreamSurface
import com.nomadly.app.ui.theme.Destructive
import com.nomadly.app.ui.theme.ManropeFontFamily
import com.nomadly.app.ui.theme.NomadlyTheme
import com.nomadly.app.ui.theme.NotoSerifFontFamily
import com.nomadly.app.ui.theme.PrimaryText
import com.nomadly.app.ui.theme.RustOrange
import com.nomadly.app.ui.theme.SecondaryText
import com.nomadly.app.ui.theme.TealAlt
import com.nomadly.app.ui.theme.White

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileScreen(
    navController: NavController
) {
    val currentRoute = Screen.Profile.route
    val profile = MockRepository.userProfile

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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Cream),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                horizontal = 24.dp,
                vertical = 0.dp
            ),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Profile header
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Avatar with gradient ring
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(136.dp)
                    ) {
                        // Gradient ring
                        Box(
                            modifier = Modifier
                                .size(136.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(BrandTeal, AccentCyan)
                                    )
                                )
                        )
                        // White border
                        Box(
                            modifier = Modifier
                                .size(128.dp)
                                .clip(CircleShape)
                                .background(White)
                        )
                        // Avatar image
                        AsyncImage(
                            model = profile.avatarUrl,
                            contentDescription = profile.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                        )
                    }

                    Text(
                        text = profile.name,
                        fontFamily = NotoSerifFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        color = PrimaryText,
                        letterSpacing = (-0.3).sp
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = BrandTeal,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = profile.location,
                            fontFamily = ManropeFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            color = SecondaryText
                        )
                    }
                }
            }

            // Stats grid
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        value = profile.savedCount.toString(),
                        label = "Saved",
                        valueColor = BrandTeal,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        value = profile.boardCount.toString(),
                        label = "Boards",
                        valueColor = RustOrange,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        value = profile.visitedCount.toString(),
                        label = "Visited",
                        valueColor = BrandTeal,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Nomadly Club premium CTA card
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(32.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(BrandTeal, TealAlt, Color(0xFF004B4C))
                            )
                        )
                        .padding(24.dp)
                ) {
                    // Decorative cyan blur orb (top-right)
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .align(Alignment.TopEnd)
                            .blur(50.dp)
                            .background(
                                AccentCyan.copy(alpha = 0.5f),
                                shape = CircleShape
                            )
                    )

                    // Sparkle icon (top-right corner)
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Outlined.AutoAwesome,
                        contentDescription = null,
                        tint = AccentCyan,
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.TopEnd)
                            .padding(0.dp)
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth(0.75f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Nomadly Club",
                            fontFamily = NotoSerifFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = White,
                            letterSpacing = (-0.3).sp
                        )
                        Text(
                            text = "Unlock exclusive destinations, curated itineraries, and early access to hidden gems worldwide.",
                            fontFamily = ManropeFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 13.sp,
                            color = White.copy(alpha = 0.80f),
                            lineHeight = 20.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(999.dp))
                                .background(White)
                                .clickable {}
                                .padding(horizontal = 24.dp, vertical = 12.dp)
                        ) {
                            Text(
                                text = "Upgrade Now",
                                fontFamily = ManropeFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = BrandTeal
                            )
                        }
                    }
                }
            }

            // Travel Style section
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Travel Style",
                            fontFamily = ManropeFontFamily,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 11.sp,
                            color = SecondaryText,
                            letterSpacing = 1.8.sp
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(999.dp))
                                .background(CreamSurface)
                                .clickable {}
                                .padding(horizontal = 16.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "MODIFY",
                                fontFamily = ManropeFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                color = BrandTeal,
                                letterSpacing = 1.4.sp
                            )
                        }
                    }

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        profile.travelStyles.forEach { style ->
                            StyleChip(
                                label = style,
                                isActive = true
                            )
                        }
                    }
                }
            }

            // Preferences section
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "My Account",
                        fontFamily = ManropeFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 11.sp,
                        color = SecondaryText,
                        letterSpacing = 1.8.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(White)
                    ) {
                        ProfileListItem(
                            icon = Icons.Default.Favorite,
                            title = "My Favourites",
                            onClick = {}
                        )
                        ProfileDivider()
                        ProfileListItem(
                            icon = Icons.Default.History,
                            title = "Travel History",
                            onClick = {}
                        )
                        ProfileDivider()
                        ProfileListItem(
                            icon = Icons.Default.CreditCard,
                            title = "Payment Methods",
                            onClick = {}
                        )
                    }
                }
            }

            // App Settings section
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Settings",
                        fontFamily = ManropeFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 11.sp,
                        color = SecondaryText,
                        letterSpacing = 1.8.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(White)
                    ) {
                        ProfileListItem(
                            icon = Icons.Default.Settings,
                            title = "General Settings",
                            onClick = {}
                        )
                        ProfileDivider()
                        ProfileListItem(
                            icon = Icons.Default.Lock,
                            title = "Privacy & Security",
                            onClick = {}
                        )
                        ProfileDivider()
                        ProfileListItem(
                            icon = Icons.Default.Logout,
                            title = "Log Out",
                            titleColor = Destructive,
                            onClick = {},
                            showChevron = false
                        )
                    }
                }
            }

            // Bottom spacer
            item {
                Spacer(
                    modifier = Modifier
                        .height(128.dp)
                        .navigationBarsPadding()
                )
            }
        }
    }
}

@Composable
private fun ProfileListItem(
    icon: ImageVector,
    title: String,
    titleColor: Color = PrimaryText,
    onClick: () -> Unit,
    showChevron: Boolean = true
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            androidx.compose.material3.Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (titleColor == PrimaryText) BrandTeal else titleColor,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = title,
                fontFamily = ManropeFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
                color = titleColor
            )
        }
        if (showChevron) {
            androidx.compose.material3.Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = SecondaryText.copy(alpha = 0.4f),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun ProfileDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(1.dp)
            .background(Color(0xFFEBE8E1))
    )
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    NomadlyTheme {
        ProfileScreen(
            navController = rememberNavController()
        )
    }
}
