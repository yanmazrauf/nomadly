package com.nomadly.app.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Person
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
import com.nomadly.app.ui.theme.Cream
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
        inactiveIcon = Icons.Outlined.Bookmark
    ),
    BottomNavItem(
        label = "Profile",
        route = Screen.Profile.route,
        activeIcon = Icons.Filled.Person,
        inactiveIcon = Icons.Outlined.Person
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
                elevation = 32.dp,
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                ambientColor = Color(0x14000000),
                spotColor = Color(0x1A000000),
                clip = false
            )
            .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
            .background(Cream.copy(alpha = 0.97f))
            .navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            bottomNavItems.forEach { item ->
                BottomNavItemView(
                    item = item,
                    isActive = currentRoute == item.route,
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
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .clickable { onClick() }
            .padding(horizontal = 4.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Icon pill
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(999.dp))
                .then(
                    if (isActive) Modifier.background(BrandTeal)
                    else Modifier
                )
                .padding(
                    horizontal = if (isActive) 20.dp else 12.dp,
                    vertical = 9.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = if (isActive) item.activeIcon else item.inactiveIcon,
                contentDescription = item.label,
                tint = if (isActive) White else SecondaryText.copy(alpha = 0.45f),
                modifier = Modifier.size(20.dp)
            )
            AnimatedVisibility(
                visible = isActive,
                enter = expandHorizontally(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMediumLow
                    )
                ) + fadeIn(),
                exit = shrinkHorizontally() + fadeOut()
            ) {
                Text(
                    text = item.label,
                    fontFamily = ManropeFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = White
                )
            }
        }

        // Inactive label below icon pill
        AnimatedVisibility(
            visible = !isActive,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Text(
                text = item.label,
                fontFamily = ManropeFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 10.sp,
                color = SecondaryText.copy(alpha = 0.45f)
            )
        }

        // Active indicator dot (hidden when active to avoid extra height)
        if (isActive) {
            Box(modifier = Modifier.height(4.dp).width(1.dp))
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F2)
@Composable
fun NomadlyBottomNavPreview() {
    NomadlyTheme {
        Column {
            NomadlyBottomNav(
                currentRoute = Screen.Home.route,
                onNavigate = {}
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F2)
@Composable
fun NomadlyBottomNavSavedPreview() {
    NomadlyTheme {
        NomadlyBottomNav(
            currentRoute = Screen.SavedBoards.route,
            onNavigate = {}
        )
    }
}
