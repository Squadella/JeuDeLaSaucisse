package fr.squadella.saucisse.util

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import kotlin.random.Random

/**
 * Permet de manipuler des éléments randoms.
 */
object RandomUtils {

    val random = Random(Date().time)

}