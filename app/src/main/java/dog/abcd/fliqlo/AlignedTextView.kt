package dog.abcd.fliqlo

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatTextView

class AlignedTextView : AppCompatTextView {
    private var alignment = ProperTextAlignment.TOP

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        attrs?.let {
            val typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.AlignedTextView, defStyleAttr, 0)

            val alignment = typedArray.getInt(R.styleable.AlignedTextView_alignment, 0)
            if (alignment != 0) {
                setAlignment(alignment)
            } else {
                Log.e(
                    "AlignedTextView",
                    "You did not set an alignment for an AlignedTextView. Default is top alignment."
                )
            }

            invalidate()
            typedArray.recycle()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.let {

            val width = measuredWidth
            val height = measuredHeight

            paint.textAlign = Paint.Align.CENTER
            paint.color = currentTextColor
            paint.textSize = textSize


            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val c = Canvas(bitmap)


            val xPos = width / 2
            val yPos = height / 2 - (paint.descent() + paint.ascent()) / 2


            when (alignment) {
                ProperTextAlignment.BOTTOM -> {
                    c.drawText(text.toString(), xPos.toFloat(), yPos + height / 2, paint)
                }
                else -> {
                    c.drawText(text.toString(), xPos.toFloat(), yPos - height / 2, paint)
                }
            }


            c.save()
            c.restore()

            canvas.drawBitmap(bitmap, 0f, 0f, null)

        }
    }

    private fun setAlignment(alignment: Int) {
        if (alignment == 1) {
            this.alignment = ProperTextAlignment.TOP
        } else if (alignment == 2) {
            this.alignment = ProperTextAlignment.BOTTOM
        }
    }

    private enum class ProperTextAlignment {
        TOP,
        BOTTOM
    }
}
