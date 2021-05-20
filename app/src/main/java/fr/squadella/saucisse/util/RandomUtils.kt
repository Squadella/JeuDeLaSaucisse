package fr.squadella.saucisse.util

import java.time.LocalDateTime
import java.time.ZoneId
import kotlin.random.Random

/**
 * Permet de manipuler des éléments randoms.
 */
object RandomUtils {

    val random = Random(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())

}