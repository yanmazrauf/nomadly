package com.nomadly.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nomadly.app.navigation.Screen
import com.nomadly.app.ui.theme.BrandTeal
import com.nomadly.app.ui.theme.ManropeFontFamily
import com.nomadly.app.ui.theme.NomadlyTheme
import com.nomadly.app.ui.theme.SecondaryText
import com.nomadly.app.ui.theme.White

data class BottomNavItem(
    val label: String,
    val route: String,
    val activeIcon: ImageVector,
    val inactiveIcon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(
        label = "Explore",
        route = Screen.Home.route,
        activeIcon = Icons.Filled.Explore,
        inactiveIcon = Icons.Outlined.Explore
    ),
    BottomNavItem(
        label = "Saved",
        route = Screen.SavedBoards.route,
        activeIcon = Icons.Filled.Bookmark,
        inactiveIcon = Icons.Outlined.BookmarkBorder
    ),
    BottomNavItem(
        label = "Profile",
        route = Screen.Profile.route,
        activeIcon = Icons.Filled.Person,
        inactiveIcon = Icons.Outlined.PersonOutline
    )
)

@Composable
fun NomadlyBottomNav(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 24.dp,
                shape = RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp),
                clip = false
            )
            .clip(RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp))
            .background(White.copy(alpha = 0.92f))
            .navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            bottomNavItems.forEach { item ->
                val isActive = currentRoute == item.route
                BottomNavItemView(
                    item = item,
                    isActive = isActive,
                    onClick = { onNavigate(item.route) }
                )
            }
        }
    }
}

@Composable
private fun BottomNavItemView(
    item: BottomNavItem,
    isActive: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .then(
                if (isActive) Modifier.background(BrandTeal)
                else Modifier
            )
            .clickable { onClick() }
            .padding(horizontal = if (isActive) 20.dp else 12.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isActive) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = item.activeIcon,
                    contentDescription = item.label,
                    tint = White,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = item.label,
                    fontFamily = ManropeFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = White,
                    letterSpacing = 0.sp
                )
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = item.inactiveIcon,
                    contentDescription = item.label,
                    tint = SecondaryText.copy(alpha = 0.5f),
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = item.label,
                    fontFamily = ManropeFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 10.sp,
                    color = SecondaryText.copy(alpha = 0.5f),
                    letterSpacing = 0.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NomadlyBottomNavPreview() {
    NomadlyTheme {
        NomadlyBottomNav(
            currentRoute = Screen.Home.route,
            onNavigate = {}
        )
    }
}
