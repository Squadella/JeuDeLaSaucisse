package fr.squadella.saucisse.game

import fr.squadella.saucisse.constant.CellTypeEnum
import fr.squadella.saucisse.util.RandomUtils
import kotlin.math.*

/**
 * Permet d'approcher de la gestion des patates bleues.
 */
class PatateHelper(
    private val width: Int,
    private val height: Int,
    private val board: List<ArrayList<CellTypeEnum>>
) {


    /**
     * Permet de faire exploser une patate bleue.
     * @param currentLine la ligne sur laquelle est situé la patate bleue.
     * @param currentColumn la colonne sur laquelle est situé la patate bleue.
     * @param newBoard le tableau à l'état n+1
     */
    fun explodePatate(
        currentLine: Int,
        currentColumn: Int,
        newBoard: ArrayList<ArrayList<CellTypeEnum>>
    ): CellTypeEnum {
        // Récupération de la taille de l'explosion par défaut
        val radius = calculateExplosionRadius(currentLine, currentColumn, newBoard)


        // Parcours des cases pour placer des explosions
        for (i in Integer.max(currentColumn - radius, 0)..min(currentColumn + radius, width)) {
            for (j in Integer.max(currentLine - radius, 0)..min(currentLine + radius, height)) {
                if (distance(i, j, currentColumn, currentLine) <= radius) {
                    newBoard[j][i] = CellTypeEnum.EXPLOSION
                }
            }
        }

        return CellTypeEnum.EXPLOSION
    }

    /**
     * Permet de calculer le rayon de l'explosion en fonction du nombre de patate.
     * @param currentLine la ligne de départ de l'explosion
     * @param currentColumn la colonne de départ de l'explosion
     * @param newBoard le tableau à l'état n+1
     * @param numberPatate le nombre de patate qui a été trouvé à l'itération précédente.
     */
    private fun calculateExplosionRadius(
        currentLine: Int,
        currentColumn: Int,
        newBoard: ArrayList<ArrayList<CellTypeEnum>>,
        radius: Int = 0,
        numberPatate: Int = 0
    ): Int {
        // Initialisation du rayon.
        var newRadius = if (radius == 0) {
            // Rayon de base
            ((abs(RandomUtils.randomJava.nextGaussian()) + 1) * 3).toInt()
        } else {
            radius
        }
        val numberPatateCurrent = getNombrePatateInRadius(currentColumn, currentLine, newRadius)
        // Si le nombre de pomme de terre est le même, l'explosion ne va pas s'agrandir.
        if (numberPatateCurrent == numberPatate || numberPatateCurrent == -1) {
            return newRadius
        }
        // Calcul du nouveau rayon
        newRadius += ((abs(RandomUtils.randomJava.nextGaussian()) + 1) * numberPatateCurrent + 1).toInt()
        return calculateExplosionRadius(
            currentLine,
            currentColumn,
            newBoard,
            newRadius,
            numberPatateCurrent
        )
    }

    /**
     * Permet de récupérer le nombre de patate bleue dans un rayon.
     */
    private fun getNombrePatateInRadius(currentColumn: Int, currentLine: Int, newRadius: Int): Int {
        var numberPatateCurrent = -1 // On enlève la patate qui est la source de l'explosion.
        // Calcul du nombre de patate bleue qui vont être dans le nouveau rayon d'explosion
        for (i in Integer.max(currentColumn - newRadius, 0)..min(
            currentColumn + newRadius,
            width
        )) {
            for (j in Integer.max(currentLine - newRadius, 0)..min(
                currentLine + newRadius,
                height
            )) {
                if (distance(i, j, currentColumn, currentLine) <= newRadius
                    && board[j][i] == CellTypeEnum.PATATE
                ) {
                    // On supprime les pommes de terre qui sont pris par explosion.
                    board[j][i] = CellTypeEnum.VIDE
                    numberPatateCurrent++
                }
            }
        }
        return numberPatateCurrent
    }

    /**
     * Permet de calculer la disantance entre deux points.
     * @param startColumn la position en colonne de départ
     * @param startLine la position en ligne de départ
     * @param endColumn la position en colonne d'arrivée
     * @param endLine la position en ligne d'arrivée
     */
    private fun distance(startColumn: Int, startLine: Int, endColumn: Int, endLine: Int): Int {
        return ceil(
            sqrt(
                (endColumn - startColumn).toFloat().pow(2) + (endLine - startLine).toFloat().pow(2)
            )
        ).toInt()
    }

}