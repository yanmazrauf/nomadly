package com.nomadly.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.nomadly.app.ui.theme.CreamSurface
import com.nomadly.app.ui.theme.ManropeFontFamily
import com.nomadly.app.ui.theme.NomadlyTheme
import com.nomadly.app.ui.theme.NotoSerifFontFamily
import com.nomadly.app.ui.theme.SecondaryText

@Composable
fun StatCard(
    value: String,
    label: String,
    valueColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(32.dp))
            .background(CreamSurface)
            .padding(horizontal = 16.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = value,
            fontFamily = NotoSerifFontFamily,
            fontWeight = FontWeight.Black,
            fontSize = 28.sp,
            color = valueColor,
            lineHeight = 32.sp
        )
        Text(
            text = label.uppercase(),
            fontFamily = ManropeFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp,
            color = SecondaryText,
            letterSpacing = 1.4.sp
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F2)
@Composable
fun StatCardPreview() {
    NomadlyTheme {
        StatCard(
            value = "124",
            label = "Saved",
            valueColor = BrandTeal
        )
    }
}
