package com.example.studentportal.common.ui.layout

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun <E : ExpandableItem> DropdownInput(
    options: List<E>,
    onClick: (E) -> Unit,
    modifier: Modifier = Modifier,
    @StringRes labelStringRes: Int,
    selectedItem: E?,
    enabled: Boolean = true
) {
    val expanded = remember { mutableStateOf(false) }
    ConstraintLayout(
        modifier = modifier
            .border(
                width = 1.dp,
                color = if (enabled) {
                    Color.Gray
                } else {
                    Color.LightGray
                },
                shape = RoundedCornerShape(4.dp)
            )
            .clickable {
                if (enabled) {
                    expanded.value = !expanded.value
                }
            }
    ) {
        val (label, toggleButton) = createRefs()
        // Button to show/hide the dropdown menu
        IconButton(
            enabled = enabled,
            modifier = Modifier
                .constrainAs(toggleButton) {
                    top.linkTo(label.top)
                    bottom.linkTo(label.bottom)
                    end.linkTo(parent.end)
                },
            onClick = {
                expanded.value = !expanded.value
            }
        ) {
            Icon(
                imageVector = if (expanded.value) {
                    Icons.Filled.KeyboardArrowUp
                } else {
                    Icons.Filled.KeyboardArrowDown
                },
                contentDescription = null
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(label) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        ) {
            Text(
                text = "${stringResource(id = labelStringRes)}: ${selectedItem?.text.orEmpty()}",
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                fontSize = 14.sp,
                color = if (enabled) {
                    Color.Black
                } else {
                    Color.Gray
                }
            )
            DropdownMenu(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .background(Color.White),
                expanded = expanded.value,
                onDismissRequest = {
                    expanded.value = false
                }
            ) {
                options.forEach { item ->
                    DropdownMenuItem(
                        colors = MenuItemColors(
                            textColor = Color.Black,
                            leadingIconColor = Color.Black,
                            trailingIconColor = Color.Black,
                            disabledTextColor = Color.Gray,
                            disabledLeadingIconColor = Color.Gray,
                            disabledTrailingIconColor = Color.Black
                        ),
                        text = {
                            Text(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                text = item.text
                            )
                        },
                        onClick = {
                            expanded.value = false
                            onClick.invoke(item)
                        }
                    )
                }
            }
        }
    }
}

interface ExpandableItem {
    val id: String
    val text: String
}
