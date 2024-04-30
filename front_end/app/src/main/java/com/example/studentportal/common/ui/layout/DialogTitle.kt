package com.example.studentportal.common.ui.layout

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun DialogTitle(
    modifier: Modifier,
    @StringRes titleRes: Int,
    onCloseClicked: () -> Unit
) {
    ConstraintLayout(modifier = modifier) {
        val (closeButton, title, divider) = createRefs()
        Text(
            modifier = Modifier
                .padding(24.dp)
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            text = stringResource(id = titleRes)
        )
        IconButton(
            modifier = Modifier
                .padding(16.dp)
                .constrainAs(closeButton) {
                    end.linkTo(parent.end)
                    top.linkTo(title.top)
                    bottom.linkTo(title.bottom)
                },
            onClick = onCloseClicked
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = null
            )
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(divider) {
                    top.linkTo(title.bottom, margin = 0.dp)
                    start.linkTo(parent.start, margin = 0.dp)
                }
                .wrapContentSize()
        )
    }
}
