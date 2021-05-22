package fr.squadella.saucisse.game

import fr.squadella.saucisse.constant.CellTypeEnum
import fr.squadella.saucisse.util.RandomUtils
import kotlinx.coroutines.flow.asFlow
import kotlin.random.nextInt
import kotlin.random.nextUInt

/**
 * Permet de manipuler le plateau de jeu.
 */
object BoardHelper {

    /**
     * Permet d'initialiser une colonne du tableau.
     *
     * @param height la hauteur du tableau
     * @param width la largeur du tableau
     */
    fun initBoard(height: Int, width: Int): List<List<CellTypeEnum>> {
        val board = ArrayList<List<CellTypeEnum>>()
        // On parcours toutes les lignes
        for (i in 0..height) {
            board.add(initBoardLine(width))
        }
        return board
    }

    /**
     * Permet d'initialiser un tableau de jeu qui est vide.
     *
     * @param height la hauteur du tableau.
     * @param width la largeur du tableau.
     */
    fun initEmptyBoard(height: Int, width: Int): ArrayList<ArrayList<CellTypeEnum>> {
        val board = ArrayList<ArrayList<CellTypeEnum>>()
        for (i in 0..height) {
            val line = ArrayList<CellTypeEnum>()
            for (j in 0..width) {
                line.add(CellTypeEnum.VIDE)
            }
            board.add(line)
        }
        return board
    }

    /**
     * Permet d'initialiser le tableau de jeu.
     *
     * @param width la largeur de la ligne du tableau.
     *
     */
    private fun initBoardLine(width: Int): List<CellTypeEnum> {
        val line = ArrayList<CellTypeEnum>()
        val random = RandomUtils.random
        for (i in 0..width) {
            when (random.nextInt(IntRange(0, 50))) {
                0 -> line.add(CellTypeEnum.SAUCE)
                else -> line.add(CellTypeEnum.VIDE)
            }
        }
        return line
    }
}