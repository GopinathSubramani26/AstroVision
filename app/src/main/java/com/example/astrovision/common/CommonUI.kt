package com.example.astrovision.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.astrovision.ui.theme.dimen0
import com.example.astrovision.ui.theme.primaryTextColor
import com.example.astrovision.ui.theme.mediumTextStyleSize24

@Composable
fun CommonText(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Start,
    textDecoration: TextDecoration = TextDecoration.None,
    textColor: Color = primaryTextColor,
    style: TextStyle = MaterialTheme.typography.displaySmall,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(dimen0),
            verticalAlignment = Alignment.CenterVertically
        ) {

         Text(
                modifier = modifier,
                textAlign = textAlign,
                text = text,
                textDecoration = textDecoration,
                color = textColor,
                style = style,
                overflow = overflow,
                maxLines = maxLines
            )
        }
    }

@Composable
fun CommonHeader(
    text: String,
    image: Painter,
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
    ) {
        Image(
            painter = image,
            contentDescription = "Back",
            modifier = Modifier
                .clickable(onClick = onBackClick)
                .padding(end = 16.dp)
                .size(34.dp)
        )
        CommonText(
            modifier = Modifier.weight(1f),
            text = text,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.mediumTextStyleSize24
        )
    }
}


