package com.nomadly.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nomadly.app.data.mock.MockRepository
import com.nomadly.app.navigation.Screen
import com.nomadly.app.ui.components.BoardCard
import com.nomadly.app.ui.components.NomadlyBottomNav
import com.nomadly.app.ui.components.NomadlyTopBar
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
fun SavedBoardsScreen(
    navController: NavController
) {
    val currentRoute = Screen.SavedBoards.route
    val boards = MockRepository.boards

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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                shape = CircleShape,
                containerColor = RustOrange,
                contentColor = White,
                modifier = Modifier.size(64.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "New Board",
                    modifier = Modifier.size(28.dp)
                )
            }
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
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Header
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 8.dp)
                ) {
                    Text(
                        text = "Saved Boards",
                        fontFamily = NotoSerifFontFamily,
                        fontWeight = FontWeight.Black,
                        fontSize = 36.sp,
                        color = PrimaryText,
                        letterSpacing = (-0.5).sp,
                        lineHeight = 40.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${boards.size} collections curated by you",
                        fontFamily = ManropeFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                        color = SecondaryText
                    )
                }
            }

            // Create new board CTA row
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(32.dp))
                        .background(CreamSurface)
                        .clickable { }
                        .padding(horizontal = 20.dp, vertical = 18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(BrandTeal),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = White,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "Create a new board",
                            fontFamily = ManropeFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = BrandTeal
                        )
                        Text(
                            text = "Start a new travel collection",
                            fontFamily = ManropeFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 13.sp,
                            color = SecondaryText
                        )
                    }
                }
            }

            // Board cards with alternating tall/short
            itemsIndexed(boards) { index, board ->
                BoardCard(
                    board = board,
                    onClick = {},
                    isTall = index % 2 == 0
                )
            }

            // Curator's Tip card
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(48.dp))
                        .background(CreamSurface)
                        .padding(28.dp)
                ) {
                    // Decorative teal blur orb (top-right)
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.TopEnd)
                            .blur(40.dp)
                            .background(
                                AccentCyan.copy(alpha = 0.4f),
                                shape = CircleShape
                            )
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Curator's Tip",
                            fontFamily = NotoSerifFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = RustOrange
                        )
                        Text(
                            text = "\"The best boards are built like stories — with a beginning, a middle, and a longing to return. Layer your destinations with intention, and let each place breathe.\"",
                            fontFamily = NotoSerifFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Italic,
                            fontSize = 15.sp,
                            color = PrimaryText,
                            lineHeight = 24.sp
                        )
                        Text(
                            text = "— Elena Moretti, Nomadly Curator",
                            fontFamily = ManropeFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                            color = SecondaryText,
                            letterSpacing = 0.3.sp
                        )
                    }
                }
            }

            // Bottom padding for FAB + nav
            item {
                Spacer(
                    modifier = Modifier
                        .height(96.dp)
                        .navigationBarsPadding()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SavedBoardsScreenPreview() {
    NomadlyTheme {
        SavedBoardsScreen(
            navController = rememberNavController()
        )
    }
}
