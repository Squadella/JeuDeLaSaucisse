package fr.squadella.saucisse.util

import java.util.*
import kotlin.random.Random

/**
 * Permet de manipuler des éléments randoms.
 */
object RandomUtils {

    val random = Random(Date().time)

    val randomJava = java.util.Random()

}