package com.velvet.daynight

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.velvet.darknight.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DayNightSwitch(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    checked: Boolean,
    onCheckedChange: () -> Unit
) {
    val durationMillis = 500
    val skyColorNight = Color(0xFF000066)
    val skyColor = Color(0xFF00B5E2)

    val color = animateColorAsState(
        targetValue = if (checked) skyColorNight else skyColor,
        animationSpec = tween(durationMillis)
    )
    Box(
        modifier = modifier
            .clip(CircleShape)
            .size(width = 52.dp, height = 32.dp)
            .background(color.value)
            .clickable(
                enabled = enabled, onClick = onCheckedChange
            )
    ) {
        AnimatedVisibility(
            modifier = Modifier.zIndex(1f),
            visible = checked,
            enter = slideInHorizontally(
                animationSpec = tween(durationMillis),
                initialOffsetX = { -it }),
            exit = slideOutHorizontally(
                animationSpec = tween(durationMillis),
                targetOffsetX = { -it })
        ) {
            Image(painter = painterResource(R.drawable.stars), contentDescription = null)
        }
        AnimatedVisibility(
            modifier = Modifier.zIndex(3f),
            visible = !checked,
            enter = slideInHorizontally(
                animationSpec = tween(durationMillis),
                initialOffsetX = { it }),
            exit = slideOutHorizontally(
                animationSpec = tween(durationMillis),
                targetOffsetX = { it })
        ) {
            Image(painter = painterResource(R.drawable.clouds), contentDescription = null)
        }
        AnimatedVisibility(
            modifier = Modifier
                .zIndex(1f)
                .offset(x = (-7).dp),
            visible = !checked,
            enter = scaleIn(animationSpec = tween(durationMillis / 2, durationMillis)),
            exit = scaleOut(animationSpec = tween(durationMillis / 2))
        ) {
            Image(
                painter = painterResource(R.drawable.sun_radiance),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
        AnimatedVisibility(
            modifier = Modifier
                .zIndex(1f)
                .offset(x = 13.dp),
            visible = checked,
            enter = scaleIn(animationSpec = tween(durationMillis / 2, durationMillis)),
            exit = scaleOut(animationSpec = tween(durationMillis / 2))
        ) {
            Image(
                painter = painterResource(R.drawable.moon_radiance),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
        val offsetState = animateDpAsState(
            targetValue = if (checked) 22.dp else 2.dp,
            animationSpec = tween(durationMillis)
        )
        Crossfade(
            modifier = Modifier
                .zIndex(2f)
                .offset(x = offsetState.value, y = 2.dp),
            targetState = checked, animationSpec = tween(durationMillis)
        ) { isChecked ->
            if (isChecked) {
                Image(
                    painter = painterResource(id = R.drawable.moon),
                    contentDescription = null
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.sun),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun DayNightSwitchPreview() {
    var checked by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.LightGray),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DayNightSwitch(
                checked = checked,
                onCheckedChange = { checked = !checked }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.DarkGray),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DayNightSwitch(
                checked = checked,
                onCheckedChange = { checked = !checked }
            )
        }
    }
}