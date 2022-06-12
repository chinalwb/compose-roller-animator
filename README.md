# ComposeRollerAnimator
Roller animation effect with Compose

### Looks like:

![roller_basic](https://user-images.githubusercontent.com/1758864/173230887-2c64c52d-3c72-4aef-b07d-10033b7594c4.gif)





### Sample code:

```
RollerNumberAnimator(
    startValue = 1990,
    endValue = 2022
)
```

### Decrease and reverse:

![reverse](https://user-images.githubusercontent.com/1758864/173231256-040e1f16-8158-46fb-a302-3fc906becc76.gif)


### Sample code:

```
RollerNumberAnimator(
    startValue = 2022,
    endValue = 1990,
    decrease = true
)
```


### Simple Demo


![simple_demo](https://user-images.githubusercontent.com/1758864/173231043-fda4acb0-f02f-40fd-9fa0-7152e0d37d9b.gif)


### API and parmas:

- Simple, check it out as blow:
- Params are pretty self explanatory.
```
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
)
```


### Just another demo

https://user-images.githubusercontent.com/1758864/173230803-26a60ec9-7de5-4a40-80a0-2f5f0aa74b0d.mp4



