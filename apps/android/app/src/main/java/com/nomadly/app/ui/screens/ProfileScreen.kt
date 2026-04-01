package com.nomadly.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.nomadly.app.model.UserProfile
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
import com.nomadly.app.ui.theme.SurfaceAlt
import com.nomadly.app.ui.theme.TealAlt
import com.nomadly.app.ui.theme.White

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = viewModel(factory = ProfileViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = Cream,
        topBar = {
            NomadlyTopBar(title = "Nomadly", onMenuClick = {}, onNotificationClick = {})
        },
        bottomBar = {
            NomadlyBottomNav(
                currentRoute = Screen.Profile.route,
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
        when (val state = uiState) {
            is ProfileUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator(color = BrandTeal) }
            }

            is ProfileUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Couldn't load profile",
                            fontFamily = NotoSerifFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = PrimaryText
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(999.dp))
                                .background(BrandTeal)
                                .clickable { viewModel.retry() }
                                .padding(horizontal = 24.dp, vertical = 10.dp)
                        ) {
                            Text("Retry", fontFamily = ManropeFontFamily, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = White)
                        }
                    }
                }
            }

            is ProfileUiState.Success -> {
                ProfileContent(
                    profile = state.profile,
                    modifier = Modifier.padding(innerPadding),
                    onUpdateStyles = viewModel::updateTravelStyles
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ProfileContent(
    profile: UserProfile,
    modifier: Modifier = Modifier,
    onUpdateStyles: (List<String>) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize().background(Cream),
        contentPadding = PaddingValues(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Header
        item {
            Column(
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.size(120.dp)) {
                    Box(modifier = Modifier.size(120.dp).clip(CircleShape).background(Brush.linearGradient(listOf(BrandTeal, AccentCyan))))
                    Box(modifier = Modifier.size(112.dp).clip(CircleShape).background(Cream))
                    AsyncImage(
                        model = profile.avatarUrl,
                        contentDescription = profile.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(104.dp).clip(CircleShape)
                    )
                }
                Text(
                    text = profile.name,
                    fontFamily = NotoSerifFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    color = PrimaryText,
                    letterSpacing = (-0.3).sp
                )
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    androidx.compose.material3.Icon(Icons.Default.LocationOn, null, tint = BrandTeal, modifier = Modifier.size(15.dp))
                    Text(profile.location, fontFamily = ManropeFontFamily, fontWeight = FontWeight.Medium, fontSize = 14.sp, color = SecondaryText.copy(alpha = 0.8f))
                }
                Text(
                    text = "Slow traveller · Gastronome · Art lover",
                    fontFamily = ManropeFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp,
                    color = SecondaryText.copy(alpha = 0.55f)
                )
            }
        }

        // Stats
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                StatCard(value = profile.savedCount.toString(), label = "Saved", valueColor = BrandTeal, modifier = Modifier.weight(1f))
                StatCard(value = profile.boardCount.toString(), label = "Boards", valueColor = RustOrange, modifier = Modifier.weight(1f))
                StatCard(value = profile.visitedCount.toString(), label = "Visited", valueColor = BrandTeal, modifier = Modifier.weight(1f))
            }
        }

        // Nomadly Club card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp))
                    .background(Brush.linearGradient(colors = listOf(BrandTeal, TealAlt, Color(0xFF004B4C))))
                    .padding(24.dp)
            ) {
                Box(modifier = Modifier.size(110.dp).align(Alignment.TopEnd).blur(45.dp).background(AccentCyan.copy(alpha = 0.45f), CircleShape))
                androidx.compose.material3.Icon(Icons.Outlined.AutoAwesome, null, tint = AccentCyan, modifier = Modifier.size(22.dp).align(Alignment.TopEnd))
                Column(modifier = Modifier.fillMaxWidth(0.78f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Nomadly Club", fontFamily = NotoSerifFontFamily, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = White, letterSpacing = (-0.3).sp)
                    Text(
                        text = "Unlock exclusive destinations, curated itineraries, and early access to hidden gems worldwide.",
                        fontFamily = ManropeFontFamily,
                        fontSize = 13.sp,
                        color = White.copy(alpha = 0.78f),
                        lineHeight = 20.sp
                    )
                    Spacer(Modifier.height(4.dp))
                    Box(modifier = Modifier.clip(RoundedCornerShape(999.dp)).background(White).clickable {}.padding(horizontal = 22.dp, vertical = 11.dp)) {
                        Text("Upgrade Now", fontFamily = ManropeFontFamily, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = BrandTeal)
                    }
                }
            }
        }

        // Travel Style
        item {
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    ProfileSectionLabel("Travel Style")
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(999.dp))
                            .background(CreamSurface)
                            .clickable {}
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text("MODIFY", fontFamily = ManropeFontFamily, fontWeight = FontWeight.Bold, fontSize = 10.sp, color = BrandTeal, letterSpacing = 1.2.sp)
                    }
                }
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    profile.travelStyles.forEach { style -> StyleChip(label = style, isActive = true) }
                }
            }
        }

        // My Account
        item {
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                ProfileSectionLabel("My Account")
                Spacer(Modifier.height(8.dp))
                ProfileGroup {
                    ProfileListItem(Icons.Default.Favorite, "My Favourites", onClick = {})
                    ProfileDivider()
                    ProfileListItem(Icons.Default.History, "Travel History", onClick = {})
                    ProfileDivider()
                    ProfileListItem(Icons.Default.CreditCard, "Payment Methods", onClick = {})
                }
            }
        }

        // Settings
        item {
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                ProfileSectionLabel("Settings")
                Spacer(Modifier.height(8.dp))
                ProfileGroup {
                    ProfileListItem(Icons.Default.Settings, "General Settings", onClick = {})
                    ProfileDivider()
                    ProfileListItem(Icons.Default.Lock, "Privacy & Security", onClick = {})
                    ProfileDivider()
                    ProfileListItem(Icons.Default.Logout, "Log Out", titleColor = Destructive, onClick = {}, showChevron = false)
                }
            }
        }

        item { Spacer(modifier = Modifier.height(120.dp).navigationBarsPadding()) }
    }
}

@Composable
private fun ProfileSectionLabel(text: String) {
    Text(
        text = text.uppercase(),
        fontFamily = ManropeFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 10.sp,
        color = SecondaryText.copy(alpha = 0.45f),
        letterSpacing = 1.8.sp
    )
}

@Composable
private fun ProfileGroup(content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).background(White)) {
        content()
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
        modifier = Modifier.fillMaxWidth().clickable { onClick() }.padding(horizontal = 20.dp, vertical = 17.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (titleColor == PrimaryText) BrandTeal.copy(alpha = 0.08f) else titleColor.copy(alpha = 0.08f)),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.material3.Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (titleColor == PrimaryText) BrandTeal else titleColor,
                    modifier = Modifier.size(18.dp)
                )
            }
            Text(title, fontFamily = ManropeFontFamily, fontWeight = FontWeight.Medium, fontSize = 15.sp, color = titleColor)
        }
        if (showChevron) {
            androidx.compose.material3.Icon(Icons.Default.ChevronRight, null, tint = SecondaryText.copy(alpha = 0.30f), modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
private fun ProfileDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 68.dp, end = 20.dp)
            .height(1.dp)
            .background(SurfaceAlt.copy(alpha = 0.7f))
    )
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    NomadlyTheme {
        ProfileScreen(navController = rememberNavController())
    }
}
