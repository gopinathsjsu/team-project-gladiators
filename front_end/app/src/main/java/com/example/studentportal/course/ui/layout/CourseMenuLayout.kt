package com.example.studentportal.course.ui.layout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.studentportal.course.ui.model.Command
import com.example.studentportal.course.ui.model.CourseMenuItem
import com.example.studentportal.course.ui.model.UserType

@Composable
fun CourseMenuLayout(
    userType: UserType,
    modifier: Modifier = Modifier,
    onClicked: (command: Command) -> Unit
) {
    LazyColumn(modifier) {
        items(userType.menuItems) {
            MenuItem(
                menuItem = it,
                onClicked = onClicked
            )
        }
    }
}

@Composable
fun MenuItem(
    menuItem: CourseMenuItem,
    onClicked: (command: Command) -> Unit,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = Modifier
            .height(64.dp)
            .fillMaxWidth()
            .clickable {
                onClicked.invoke(menuItem.command)
            }
    ) {
        val (icon, title, arrow, divider) = createRefs()
        Icon(
            modifier = modifier
                .constrainAs(icon) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start, margin = 16.dp)
                },
            imageVector = menuItem.icon,
            contentDescription = null
        )
        Text(
            modifier = modifier
                .wrapContentHeight()
                .padding(start = 16.dp, end = 16.dp)
                .constrainAs(title) {
                    top.linkTo(icon.top)
                    bottom.linkTo(icon.bottom)
                    start.linkTo(icon.end)
                },
            text = stringResource(id = menuItem.titleRes),
            textAlign = TextAlign.Start,
            style = TextStyle(fontSize = 22.sp)
        )
        Icon(
            modifier = modifier
                .constrainAs(arrow) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end, margin = 16.dp)
                },
            imageVector = Icons.Outlined.KeyboardArrowRight,
            contentDescription = null
        )
        Divider(
            modifier = modifier
                .constrainAs(divider) {
                    top.linkTo(parent.bottom, margin = 0.dp)
                    start.linkTo(parent.start, margin = 0.dp)
                    end.linkTo(parent.end, margin = 0.dp)
                }
                .wrapContentSize()
        )
    }
}
