package com.chinalwb.compose.rolleranimator

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import java.lang.Float.max

enum class RollerState {
    Started,
    Ended
}

/**
 * Roller animation from startValue to endValue.
 *
 * - By default, startValue should be less than endValue; if startValue is greater than endValue,
 * the startValue will be reset as 0 and start animation.
 *
 * - If set decrease as true, startValue expects to be greater than endValue, otherwise, it'll be
 * ignored.
 *
 */
@Composable
fun RollerNumberAnimator(
    modifier: Modifier = Modifier,
    startValue: Int,
    endValue: Int,
    animateOneByOne: Boolean = false,
    ltr: Boolean = false,
    decrease: Boolean = false,
    duration: Int? = null,
    animateToSameNumber: Boolean = false,
    useFading: Boolean = true,
    vPadding: Dp = 4f.dp,
    textSize: TextUnit = 36.sp,
    textColor: Color = Color(0xFFFEFEFE),
    backgroundColor: Color = Color(0xFF0CCD8C),
    onLayoutFinished: (paddingBottomDp: Dp) -> Unit = {},
    onRollingFinished: (endValue: Int) -> Unit = {}
) {
    if (decrease) {
        if (startValue < endValue)
            throw IllegalArgumentException("startValue must be greater than endValue when using decrease")
    }

    var rollerStartNumber = startValue
    var rollerEndNumber = endValue
    if (rollerEndNumber < 0) {
       rollerEndNumber = 0
    }

    if (rollerStartNumber > rollerEndNumber) {
        if (decrease) {
            // Do nothing
        } else {
            rollerStartNumber = 0
        }
    }


    // Add n Text accordingly

    val maxNumber = maxOf(rollerStartNumber, rollerEndNumber)
    val bitCount = maxNumber.toString().length


    val listOfNumberList: List<List<Int>> = calculateListOfNumberList(
        rollerStartNumber,
        rollerEndNumber,
        decrease,
        animateToSameNumber
    )

    var rollingNth by remember(endValue) {
        mutableStateOf(if (ltr) 1 else bitCount)
    }
    val finishedRollingList = remember(endValue) {
        mutableStateListOf<Int>()
    }

    Row(modifier = modifier) {
        for (i in 1 .. bitCount) {
            val useAnimation = if (animateOneByOne) {
                rollingNth == i
            } else {
                true
            }

            val numberList = if (decrease) {
                listOfNumberList[i - 1].reversed()
            } else {
                listOfNumberList[i - 1]
            }

            RollerNumberComponent(
                numberList,
                backgroundColor,
                vPadding,
                textSize,
                textColor,
                i,
                useAnimation,
                useFading,
                decrease,
                duration,
                endValue,
                onLayoutFinished,
            ) {
                rollingNth = if (ltr) {
                    (it + 1).coerceAtMost(bitCount)
                } else {
                    (it - 1).coerceAtLeast(1)
                }

                if (!finishedRollingList.contains(it)) {
                    finishedRollingList.add(it)
                    if (finishedRollingList.size == bitCount) {
                        onRollingFinished(endValue)
                    }
                }

            }
        }
    }
}

fun calculateListOfNumberList(
    rollerStartNumber: Int,
    rollerEndNumber: Int,
    decrease: Boolean = false,
    animateToSameNumber: Boolean = true
): List<List<Int>> {
    val maxNumber = maxOf(rollerStartNumber, rollerEndNumber)
    val bitCount = maxNumber.toString().length

    val resultList = mutableListOf<List<Int>>()

    fun loopToSameNumber(
        startNumberAtThisSlot: Int,
        numberListAtThisSlot: MutableList<Int>,
        endNumberAtThisSlot: Int,
        decrease: Boolean = false
    ) {
        if (decrease) {
            for (n in startNumberAtThisSlot downTo 0) {
                numberListAtThisSlot.add(n)
            }
            for (n in 9 downTo endNumberAtThisSlot) {
                numberListAtThisSlot.add(n)
            }
        } else {
            for (n in startNumberAtThisSlot..9) {
                numberListAtThisSlot.add(n)
            }
            for (n in 0..endNumberAtThisSlot) {
                numberListAtThisSlot.add(n)
            }
        }
    }

    for (i in bitCount downTo 1) {
        var startNumberAtThisSlot: Int
        var endNumberAtThisSlot: Int
        val numberListAtThisSlot = mutableListOf<Int>()

        val startNumberAsString = rollerStartNumber.toString()
        val startLen = startNumberAsString.length
        startNumberAtThisSlot = if (startLen >= i) {
            startNumberAsString[startLen - i].digitToInt()
        } else {
            0
        }

        val endNumberAsString = rollerEndNumber.toString()
        val endLen = endNumberAsString.length
        endNumberAtThisSlot = if (endLen >= i) {
            endNumberAsString[endLen - i].digitToInt()
        } else {
            0
        }


        if (decrease) {
            if (startNumberAtThisSlot == endNumberAtThisSlot) {
                if (animateToSameNumber) {
                    loopToSameNumber(startNumberAtThisSlot, numberListAtThisSlot, endNumberAtThisSlot, decrease)
                } else {
                    numberListAtThisSlot.add(startNumberAtThisSlot)
                }
            } else if (startNumberAtThisSlot > endNumberAtThisSlot) {
                for (n in startNumberAtThisSlot downTo endNumberAtThisSlot) {
                    numberListAtThisSlot.add(n)
                }
            } else {
                loopToSameNumber(startNumberAtThisSlot, numberListAtThisSlot, endNumberAtThisSlot, decrease)
            }
        } else {
            if (startNumberAtThisSlot == endNumberAtThisSlot) {
                if (animateToSameNumber) {
                    loopToSameNumber(startNumberAtThisSlot, numberListAtThisSlot, endNumberAtThisSlot)
                } else {
                    numberListAtThisSlot.add(startNumberAtThisSlot)
                }
            } else if (startNumberAtThisSlot > endNumberAtThisSlot) {
                loopToSameNumber(startNumberAtThisSlot, numberListAtThisSlot, endNumberAtThisSlot)
            } else {
                for (n in startNumberAtThisSlot .. endNumberAtThisSlot) {
                    numberListAtThisSlot.add(n)
                }
            }
        }

        resultList.add(numberListAtThisSlot)
    }

    return resultList
}

@Composable
private fun <T> RollerNumberComponent(
    numberList: List<T>,
    backgroundColor: Color,
    vPadding: Dp,
    textSize: TextUnit,
    textColor: Color,
    nth: Int,
    useAnimation: Boolean,
    useFading: Boolean,
    reverseDirection: Boolean,
    duration: Int? = null,
    rememberKey: Any = Unit,
    onLayoutFinished: (bottomDp: Dp) -> Unit,
    onFinished: (Int) -> Unit
) {

    var maxBaseline by remember { mutableStateOf(0f) }
    fun updateMaxBaseline(textLayoutResult: TextLayoutResult) {
        maxBaseline = max(maxBaseline, textLayoutResult.size.height - textLayoutResult.lastBaseline)
    }
    val topBaselinePadding = with(LocalDensity.current) { maxBaseline.toDp() }
    if (maxBaseline > 0) {
        onLayoutFinished(topBaselinePadding)
    }

    var size: Size by remember {
        mutableStateOf(Size.Zero)
    }
    var isAnimated by remember(rememberKey) {
        mutableStateOf(false)
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(backgroundColor)
            .clipToBounds()
            .onGloballyPositioned { layoutCoordinates ->
                size = layoutCoordinates.size.toSize()
            }
    ) {

        // val rollerAnimation = useNumberAnimation(listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0))
        val rollerAnimation = if (useAnimation) {
            useNumberAnimation(numberList, reverseDirection, duration, rememberKey)
        }
        else {
            if (isAnimated) {
                if (reverseDirection) {
                    RollerAnimation(numberList.first(), numberList.first())
                } else {
                    RollerAnimation(numberList.last(), numberList.last())
                }
            } else {
                if (reverseDirection) {
                    RollerAnimation(numberList.last(), numberList.last())
                } else {
                    RollerAnimation(numberList.first(), numberList.first())
                }
            }
        }

        val (value, nextValue, fraction, isFinished) = rollerAnimation
        if (isFinished) {
            isAnimated = true
            onFinished(nth)
        }

        // Upper part
        val upperOffset = -(size.height * fraction).toInt()
        val upperAlpha = if (useFading) (1 - fraction) else 1f
        Box(modifier = Modifier
            .clipToBounds()
            .padding(vertical = vPadding)
            .alpha(upperAlpha)
            .offset {
                IntOffset(x = 0, y = upperOffset)
            }
        ) {
            Text(
                text = value.toString(),
                fontSize = textSize,
                color = textColor,
                modifier = Modifier,
                onTextLayout = ::updateMaxBaseline
            )
        }


        // Lower part
        val lowerOffset = (size.height * (1 - fraction)).toInt()
        val lowerAlpha = if (useFading) fraction else 1f
        Box(modifier = Modifier
            .clipToBounds()
            .padding(vertical = vPadding)
            .alpha(lowerAlpha)
            .offset {
                IntOffset(
                    x = 0,
                    y = lowerOffset
                )
            }
        ) {
            Text(
                text = nextValue.toString(),
                fontSize = textSize,
                color = textColor,
                modifier = Modifier,
                onTextLayout = ::updateMaxBaseline
            )
        }
    }
}

data class RollerAnimation<T>(
    val value: T,
    val nextValue: T,
    val fraction: Float = 0f,
    val isFinished: Boolean = false
)

@Composable
private fun <T> useNumberAnimation(
    numbers: List<T>,
    reverseDirection: Boolean = false,
    duration: Int? = null,
    rememberKey: Any = Unit
): RollerAnimation<T> {
    val startIndex = 0
    val endIndex = numbers.lastIndex

    val state = remember(rememberKey) {
        MutableTransitionState(RollerState.Started)
    }
    state.targetState = RollerState.Ended

    val transition = updateTransition(state, label = "Roller Number Transition")

    val animDuration = duration?.run {
        duration
    } ?: (120 * numbers.size).coerceAtLeast(600)

    val floatTransition = transition.animateFloat(
        label = "Float value transition",
//        transitionSpec = {
//            spring(
//                dampingRatio = Spring.DampingRatioHighBouncy,
//                stiffness = Spring.StiffnessLow
//            )
//        }

         transitionSpec = { tween(durationMillis = animDuration, delayMillis = 100, easing = FastOutSlowInEasing) }
    ) { rollerState ->
        when(rollerState) {
            RollerState.Started -> if (reverseDirection) endIndex.toFloat() else startIndex.toFloat()
            RollerState.Ended -> if (reverseDirection) startIndex.toFloat() else endIndex.toFloat()
        }
    }

    val currentIndex = floatTransition.value.toInt()
    val currentValue = numbers[currentIndex]
    val nextValue = numbers[minOf(currentIndex + 1, endIndex)]
    val fraction = floatTransition.value - currentIndex

    val isFinished = transition.currentState == transition.targetState

    return RollerAnimation(currentValue, nextValue, fraction, isFinished)
}
