package inc.trilokia.gfxtoo.utils.anim_utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.widget.ImageView
import androidx.core.animation.doOnEnd
import inc.trilokia.gfxtoo.R
import kotlin.random.Random

class Animations {

    private val imgList: List<Int> = listOf(
        R.drawable.img1,
        R.drawable.img2,
        R.drawable.img3,
        R.drawable.img4,
        R.drawable.img5,
    )

    var currentImg = imgList[0]

    fun spinView(img: ImageView, repeatCount: Int): AnimatorSet {
        return AnimatorSet().apply {
            playSequentially(List(repeatCount) { createSpinAnimator(img, (it + 1) * 200L) })
        }
    }

    private fun createSpinAnimator(img: ImageView, time: Long): ObjectAnimator {
        val rotationAnimator = ObjectAnimator.ofFloat(
            img,
            "rotationX",
            0f,
            360f
        ).apply {
            duration = time
        }

        rotationAnimator.doOnEnd {
            val nextImg = imgList.random(Random(System.currentTimeMillis()))
            currentImg = nextImg
            img.setImageResource(currentImg)
        }
        return rotationAnimator
    }

    companion object {
        fun animateScaleUp(view: View): AnimatorSet {
            val animX = ObjectAnimator.ofFloat(
                view,
                "scaleX",
                1f, 1.1f
            ).apply {
                duration = 300
            }

            val animY = ObjectAnimator.ofFloat(
                view,
                "scaleY",
                1f, 1.1f
            ).apply {
                duration = 300
            }
            return AnimatorSet().apply {
                playTogether(animX, animY)
            }
        }

        fun animateScaleDown(view: View): AnimatorSet {
            val animX = ObjectAnimator.ofFloat(
                view,
                "scaleX",
                1.1f, 1f
            ).apply {
                duration = 300
            }

            val animY = ObjectAnimator.ofFloat(
                view,
                "scaleY",
                1.1f, 1f
            ).apply {
                duration = 300
            }
            return AnimatorSet().apply {
                playTogether(animX, animY)
            }
        }
    }
}