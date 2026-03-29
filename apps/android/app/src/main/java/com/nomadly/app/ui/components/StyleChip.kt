package com.nomadly.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nomadly.app.ui.theme.AccentCyan
import com.nomadly.app.ui.theme.BrandTeal
import com.nomadly.app.ui.theme.ManropeFontFamily
import com.nomadly.app.ui.theme.NomadlyTheme
import com.nomadly.app.ui.theme.SecondaryText
import com.nomadly.app.ui.theme.SurfaceAlt

@Composable
fun StyleChip(
    label: String,
    isActive: Boolean,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(if (isActive) AccentCyan else SurfaceAlt)
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontFamily = ManropeFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = if (isActive) BrandTeal else SecondaryText
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F2)
@Composable
fun StyleChipPreview() {
    NomadlyTheme {
        StyleChip(label = "Slow Travel", isActive = true)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F2)
@Composable
fun StyleChipInactivePreview() {
    NomadlyTheme {
        StyleChip(label = "Adventure", isActive = false)
    }
}
