package it.walmann.adhdcompanion.components

import android.content.Context
import android.view.MotionEvent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import it.walmann.adhdcompanion.R


import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.ExperimentalComposeUiApi

import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import kotlin.math.PI
import kotlin.math.atan2


internal fun Float.normalizeAngle(): Float = this % 360f
private val CardHorizontalPadding = 40.dp
private const val BankCardAspectRatio = 1.5819f


@Composable
fun RotaryDialDialog(modifier: Modifier = Modifier, context: Context) {
    Dialog(
        onDismissRequest = { /*TODO*/ },
    ) {

        RotaryDialWidget()
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun RotaryDialWidget(modifier: Modifier = Modifier) {
//    THIS WIDGET IS ABANDONED! I may want to see into this later. But now its stopping me from continuing.
    var rotaryValue by remember {
        mutableFloatStateOf(0f)
    }
    Card(
//        modifier = Modifier // TODO NEXT This is the way to go. https://developer.android.com/develop/ui/compose/touch-input/pointer-input/drag-swipe-fling
//            .offset { IntOffset(offsetX.roundToInt(), 0) }
//            .draggable(
//                orientation = Orientation.Horizontal,
//                state = rememberDraggableState { delta ->
//                    offsetX += delta
//                }
//            ),

    ) {
        Text(text = rotaryValue.toString())
        MusicKnob(
//            limitingAngle = 350f
        ) {
            rotaryValue = it


        }

    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MusicKnob(
    modifier: Modifier = Modifier,
    limitingAngle: Float = 60f,
    onValueChange: (Float) -> Unit
) {
    var rotation by remember {
        mutableStateOf(limitingAngle)
    }
    var touchX by remember {
        mutableStateOf(0f)
    }
    var touchY by remember {
        mutableStateOf(0f)
    }
    var centerX by remember {
        mutableStateOf(0f)
    }
    var centerY by remember {
        mutableStateOf(0f)
    }

    Image(
        painter = painterResource(id = R.drawable.music_knob),
        contentDescription = "Music knob",
        modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned {
                val windowBounds = it.boundsInWindow()
                centerX = windowBounds.size.width / 2f
                centerY = windowBounds.size.height / 2f
            }
            .pointerInteropFilter { event ->
                touchX = event.x
                touchY = event.y
                val angle = -atan2(centerX - touchX, centerY - touchY) * (180f / PI).toFloat()

                when (event.action) {
                    MotionEvent.ACTION_DOWN,
                    MotionEvent.ACTION_MOVE -> {
                        if (angle !in -limitingAngle..limitingAngle) {
                            val fixedAngle =
//                                if (angle in -180f..-limitingAngle) {
                                if (angle >= -180f) {
                                    360f + angle
                                } else {
                                    angle
                                }
                            rotation = fixedAngle

//                            val percent = (fixedAngle - limitingAngle) / (360f - 2 * limitingAngle)
                            val percent = (fixedAngle)
//                            val percent = rotation
                            onValueChange(angle)
                            true
                        } else false
                    }

                    else -> false
                }
            }
            .rotate(rotation)
    )
}