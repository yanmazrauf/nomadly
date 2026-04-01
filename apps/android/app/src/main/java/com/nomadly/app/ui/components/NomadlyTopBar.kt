package com.nomadly.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nomadly.app.ui.theme.BrandTeal
import com.nomadly.app.ui.theme.Cream
import com.nomadly.app.ui.theme.ManropeFontFamily
import com.nomadly.app.ui.theme.NomadlyTheme
import com.nomadly.app.ui.theme.PrimaryText
import com.nomadly.app.ui.theme.SurfaceAlt
import com.nomadly.app.ui.theme.White

@Composable
fun NomadlyTopBar(
    title: String = "Nomadly",
    onMenuClick: () -> Unit = {},
    onNotificationClick: (() -> Unit)? = null,
    isTransparent: Boolean = false,
    contentColor: Color = if (isTransparent) White else PrimaryText
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (isTransparent) Color.Transparent
                else Cream.copy(alpha = 0.96f)
            )
            .statusBarsPadding()
            .height(60.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Menu button
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(
                        if (isTransparent) White.copy(alpha = 0.15f)
                        else Color.Transparent
                    )
                    .clickable { onMenuClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = contentColor,
                    modifier = Modifier.size(22.dp)
                )
            }

            // Title / logo
            if (title.isNotEmpty()) {
                Text(
                    text = title,
                    fontFamily = ManropeFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 17.sp,
                    color = if (isTransparent) White else BrandTeal,
                    letterSpacing = (-0.5).sp
                )
            }

            // Notification button
            if (onNotificationClick != null) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(
                            if (isTransparent) White.copy(alpha = 0.15f)
                            else SurfaceAlt.copy(alpha = 0.6f)
                        )
                        .clickable { onNotificationClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notifications",
                        tint = contentColor,
                        modifier = Modifier.size(22.dp)
                    )
                }
            } else {
                Spacer(modifier = Modifier.size(40.dp))
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F2)
@Composable
fun NomadlyTopBarPreview() {
    NomadlyTheme {
        NomadlyTopBar(
            title = "Nomadly",
            onNotificationClick = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF006768)
@Composable
fun NomadlyTopBarTransparentPreview() {
    NomadlyTheme {
        NomadlyTopBar(
            title = "Nomadly",
            isTransparent = true,
            onNotificationClick = {}
        )
    }
}
