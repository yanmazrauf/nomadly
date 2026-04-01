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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
fun SavedBoardsScreen(navController: NavController) {
    val boards = MockRepository.boards
    val leftBoards = boards.filterIndexed { i, _ -> i % 2 == 0 }
    val rightBoards = boards.filterIndexed { i, _ -> i % 2 != 0 }

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
                currentRoute = Screen.SavedBoards.route,
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
                modifier = Modifier.size(58.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "New Board",
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    ) { innerPadding ->
        if (boards.isEmpty()) {
            EmptyBoardsState(modifier = Modifier.padding(innerPadding))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Cream),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 0.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                // ── Header ──────────────────────────────────────────────
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, bottom = 16.dp)
                    ) {
                        Text(
                            text = "Saved Boards",
                            fontFamily = NotoSerifFontFamily,
                            fontWeight = FontWeight.Black,
                            fontSize = 34.sp,
                            color = PrimaryText,
                            letterSpacing = (-0.5).sp,
                            lineHeight = 38.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${boards.size} collections curated by you",
                            fontFamily = ManropeFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = SecondaryText.copy(alpha = 0.7f)
                        )
                    }
                }

                // ── Create board CTA ─────────────────────────────────────
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(28.dp))
                            .background(CreamSurface)
                            .clickable { }
                            .padding(horizontal = 18.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(BrandTeal),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                tint = White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "Create a new board",
                                fontFamily = ManropeFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = BrandTeal
                            )
                            Text(
                                text = "Start a new travel collection",
                                fontFamily = ManropeFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp,
                                color = SecondaryText.copy(alpha = 0.65f)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // ── 2-column staggered grid ──────────────────────────────
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        // Left column (even-indexed boards, taller)
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            leftBoards.forEach { board ->
                                BoardCard(
                                    board = board,
                                    onClick = {},
                                    isTall = true,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }

                        // Right column (odd-indexed boards, shorter + offset)
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Spacer(modifier = Modifier.height(44.dp))
                            rightBoards.forEach { board ->
                                BoardCard(
                                    board = board,
                                    onClick = {},
                                    isTall = false,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }

                // ── Curator's Tip card ───────────────────────────────────
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(40.dp))
                            .background(CreamSurface)
                            .padding(28.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .align(Alignment.TopEnd)
                                .blur(40.dp)
                                .background(AccentCyan.copy(alpha = 0.35f), shape = CircleShape)
                        )

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Curator's Tip",
                                fontFamily = NotoSerifFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp,
                                color = RustOrange
                            )
                            Text(
                                text = "\"The best boards are built like stories — with a beginning, a middle, and a longing to return. Layer your destinations with intention, and let each place breathe.\"",
                                fontFamily = NotoSerifFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Italic,
                                fontSize = 14.sp,
                                color = PrimaryText,
                                lineHeight = 23.sp
                            )
                            Text(
                                text = "— Elena Moretti, Nomadly Curator",
                                fontFamily = ManropeFontFamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 11.sp,
                                color = SecondaryText.copy(alpha = 0.6f),
                                letterSpacing = 0.3.sp
                            )
                        }
                    }
                }

                // ── Bottom spacer ────────────────────────────────────────
                item {
                    Spacer(
                        modifier = Modifier
                            .height(100.dp)
                            .navigationBarsPadding()
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyBoardsState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(40.dp)
        ) {
            Text(text = "✦", fontSize = 48.sp, color = BrandTeal.copy(alpha = 0.25f))
            Text(
                text = "No boards yet",
                fontFamily = NotoSerifFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = PrimaryText
            )
            Text(
                text = "Start saving destinations you love and they'll appear here as boards.",
                fontFamily = ManropeFontFamily,
                fontSize = 14.sp,
                color = SecondaryText.copy(alpha = 0.7f),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                lineHeight = 21.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SavedBoardsScreenPreview() {
    NomadlyTheme {
        SavedBoardsScreen(navController = rememberNavController())
    }
}
