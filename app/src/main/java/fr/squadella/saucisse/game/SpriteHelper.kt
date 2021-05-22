package fr.squadella.saucisse.game

import android.content.Context
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.view.setPadding
import fr.squadella.saucisse.R
import fr.squadella.saucisse.constant.CellTypeEnum
import fr.squadella.saucisse.util.RandomUtils

/**
 * Permet de dessiner les sprites.
 */
object SpriteHelper {

    /**
     * Permet de mettre à jour les sprites du tableau.
     * @param sprite l'image qui est affiché actuellement sur le tableau de jeu
     */
    fun updateSprite(sprite: ImageView, type: CellTypeEnum, context: Context) {
        val newResource = getImageResourceByType(type)
        if (newResource != sprite.tag as Int) {
            val oldRotation = sprite.rotation
            sprite.setImageResource(newResource)
            sprite.tag = newResource
            sprite.rotation = oldRotation
            when (type) {
                CellTypeEnum.VIDE -> return
                CellTypeEnum.EXPLOSION -> setFastRotationAnimation(sprite, context)
                else -> setSlowRotationAnimation(sprite, context)
            }
        }
    }

    /**
     * Création d'un élément graphique.
     *
     * @param type le type d'élément qui doit être affiché.
     * @param context le contexte de l'activité ou du fragment dans le
     */
    fun initSprite(type: CellTypeEnum, context: Context): ImageView {
        val imageView = ImageView(context)

        imageView.setImageResource(getImageResourceByType(type))
        imageView.tag = getImageResourceByType(type)

        imageView.rotation = RandomUtils.random.nextInt(0, 12) * 30f

        imageView.layoutParams = ViewGroup.LayoutParams(49, 49)

        if (type != CellTypeEnum.VIDE) {
            setSlowRotationAnimation(imageView, context)
        } else if (type != CellTypeEnum.EXPLOSION) {
            setFastRotationAnimation(imageView, context)
        }

        imageView.setPadding(1)

        return imageView
    }

    /**
     * Permet de mettre une animation sur une image view.
     * @param imageView l'image qui est a changé
     * @param context le contexte de l'activité ou du fragment.
     */
    private fun setSlowRotationAnimation(imageView: ImageView, context: Context) {
        val rotation: Animation =
            AnimationUtils.loadAnimation(context, R.anim.slow_clockwise_rotation)
        rotation.repeatCount = Animation.INFINITE
        imageView.startAnimation(rotation)

    }

    /**
     * Permet de mettre une animation sur une image view.
     * @param imageView l'image qui est a changé
     * @param context le contexte de l'activité ou du fragment.
     */
    private fun setFastRotationAnimation(imageView: ImageView, context: Context) {
        val rotation: Animation =
            AnimationUtils.loadAnimation(context, R.anim.fast_clockwise_rotation)
        rotation.repeatCount = Animation.INFINITE
        imageView.startAnimation(rotation)

    }

    /**
     * Permet de récupérer le type d'image en fonction du type d'élément
     * @param type le type de données qui est a afficher.
     */
    private fun getImageResourceByType(type: CellTypeEnum): Int {
        return when (type) {
            CellTypeEnum.SAUCE -> R.drawable.ic_ketchup
            CellTypeEnum.SAUCISSE -> R.drawable.saucisse
            CellTypeEnum.DINER -> R.drawable.diner
            CellTypeEnum.PATATE -> R.drawable.patate
            CellTypeEnum.VIDE -> R.drawable.vide
            CellTypeEnum.EXPLOSION -> R.drawable.ic_bomb
        }
    }

}