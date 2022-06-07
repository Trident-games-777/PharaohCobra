package inc.trilokia.gfxtoo.view

import android.animation.AnimatorSet
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import inc.trilokia.gfxtoo.databinding.ActivityGameCobraBinding
import inc.trilokia.gfxtoo.utils.anim_utils.Animations
import kotlin.random.Random

class GameCobraActivity : AppCompatActivity() {
    private lateinit var cobraBinding: ActivityGameCobraBinding
    private var firstImg = -1
    private var secondImg = -1
    private var thirdImg = -1
    private var total = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cobraBinding = ActivityGameCobraBinding.inflate(layoutInflater)
        val cobraGameView = cobraBinding.root
        setContentView(cobraGameView)

        cobraBinding.buttonSpin.setOnClickListener { button ->
            button.isClickable = false
            mainAnimator().start()
        }
    }

    private fun mainAnimator(): AnimatorSet {
        val animationUtil1 = Animations()
        val anim1 = animationUtil1.spinView(
            cobraBinding.img1,
            (1..2).random(Random(System.currentTimeMillis()))
        )
        anim1.doOnEnd { firstImg = animationUtil1.currentImg }

        val animationUtil2 = Animations()
        val anim2 = animationUtil2.spinView(
            cobraBinding.img2,
            (1..2).random(Random(System.currentTimeMillis()))
        )
        anim2.doOnEnd { secondImg = animationUtil2.currentImg }

        val animationUtil3 = Animations()
        val anim3 = animationUtil3.spinView(
            cobraBinding.img3,
            (1..2).random(Random(System.currentTimeMillis()))
        )
        anim3.doOnEnd { thirdImg = animationUtil3.currentImg }

        return AnimatorSet().apply {
            play(anim1).with(anim2)
            play(anim2).with(anim3)
            doOnEnd {
                if (firstImg == secondImg && secondImg == thirdImg) {
                    total++
                    cobraBinding.tvTotal.text = total.toString()
                    val up1 = Animations.animateScaleUp(cobraBinding.img1)
                    val up2 = Animations.animateScaleUp(cobraBinding.img2)
                    val up3 = Animations.animateScaleUp(cobraBinding.img3)

                    val down1 = Animations.animateScaleDown(cobraBinding.img1)
                    val down2 = Animations.animateScaleDown(cobraBinding.img2)
                    val down3 = Animations.animateScaleDown(cobraBinding.img3)

                    val up11 = Animations.animateScaleUp(cobraBinding.img1)
                    val up22 = Animations.animateScaleUp(cobraBinding.img2)
                    val up33 = Animations.animateScaleUp(cobraBinding.img3)

                    val down11 = Animations.animateScaleDown(cobraBinding.img1)
                    val down22 = Animations.animateScaleDown(cobraBinding.img2)
                    val down33 = Animations.animateScaleDown(cobraBinding.img3)

                    AnimatorSet().apply {
                        play(up1).before(down1)
                        play(up2).before(down2)
                        play(up3).before(down3)
                        play(up11).after(down1)
                        play(up22).after(down2)
                        play(up33).after(down3)
                        play(down11).after(up11)
                        play(down22).after(up22)
                        play(down33).after(up33)
                        doOnEnd { cobraBinding.buttonSpin.isClickable = true }
                        start()
                    }
                } else {
                    cobraBinding.buttonSpin.isClickable = true
                }
            }
        }
    }
}