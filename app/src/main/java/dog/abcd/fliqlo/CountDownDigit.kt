package dog.abcd.fliqlo

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import dog.abcd.fliqlo.databinding.ViewCountdownClockDigitBinding

class CountDownDigit : FrameLayout {
    private var animationDuration = 1500L

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val bind: ViewCountdownClockDigitBinding

    init {
        inflate(context, R.layout.view_countdown_clock_digit, this)
        bind = ViewCountdownClockDigitBinding.bind(this)
        bind.frontUpperText.measure(0, 0)
        bind.frontLowerText.measure(0, 0)
        bind.backUpperText.measure(0, 0)
        bind.backLowerText.measure(0, 0)
    }

    fun setNewText(newText: String) {
        bind.frontUpper.clearAnimation()
        bind.frontLower.clearAnimation()

        bind.frontUpperText.text = newText
        bind.frontLowerText.text = newText
        bind.backUpperText.text = newText
        bind.backLowerText.text = newText
    }

    fun animateTextChange(newText: String) {
        if (bind.backUpperText.text == newText) {
            return
        }

        bind.frontUpper.clearAnimation()
        bind.frontLower.clearAnimation()

        bind.backUpperText.text = newText
        bind.frontUpper.pivotY = bind.frontUpper.bottom.toFloat()
        bind.frontLower.pivotY = bind.frontUpper.top.toFloat()
        bind.frontUpper.pivotX =
            (bind.frontUpper.right - ((bind.frontUpper.right - bind.frontUpper.left) / 2)).toFloat()
        bind.frontLower.pivotX =
            (bind.frontUpper.right - ((bind.frontUpper.right - bind.frontUpper.left) / 2)).toFloat()

        bind.frontUpper.animate().setDuration(getHalfOfAnimationDuration()).rotationX(-90f)
            .setInterpolator(LinearInterpolator())
            .withEndAction {
                bind.frontUpperText.text = bind.backUpperText.text
                bind.frontUpper.rotationX = 0f
                bind.frontLower.rotationX = 90f
                bind.frontLowerText.text = bind.backUpperText.text
                bind.frontLower.animate().setDuration(getHalfOfAnimationDuration()).rotationX(0f)
                    .setInterpolator(LinearInterpolator())
                    .withEndAction {
                        bind.backLowerText.text = bind.frontLowerText.text
                    }.start()
            }.start()
    }

    fun setAnimationDuration(duration: Long) {
        this.animationDuration = duration
    }

    private fun getHalfOfAnimationDuration(): Long {
        return animationDuration / 2
    }


    fun setTypeFace(typeFace: Typeface) {
        bind.frontUpperText.typeface = typeFace
        bind.frontLowerText.typeface = typeFace
        bind.backUpperText.typeface = typeFace
        bind.backLowerText.typeface = typeFace

    }
}
