package fr.squadella.saucisse.game

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.squadella.saucisse.constant.CellTypeEnum
import kotlin.math.floor
import kotlin.properties.Delegates

/**
 * Contient les informations sur le jeu en cours.
 */
class GameViewModel : ViewModel() {

    /** La liste des informations contenu sur le tableau de jeu. */
    val board: MutableLiveData<List<ArrayList<CellTypeEnum>>> =
        MutableLiveData<List<ArrayList<CellTypeEnum>>>()

    val interruptUi = MutableLiveData(false)

    private var height by Delegates.notNull<Int>()
    private var width by Delegates.notNull<Int>()

    /**
     * Permet d'initialiser le plateau de jeu.
     *
     * @param pixelHeight la hauteur en pixel de l'écran.
     * @param pixelWidth la largeur en pixel de l'écran.
     */
    fun initBoard(pixelHeight: Int, pixelWidth: Int) {
        Log.e("VM", "Board init")
        // Calcul de la largeur et de la hauteur en pixel.
        height = floor(pixelHeight / 50f).toInt() - 1
        width = floor(pixelWidth / 50f).toInt() - 1
        // On signal à l'UI que le tableau est modifié.
        board.postValue(BoardHelper.initBoard(height, width))
    }

    /**
     * Permet de calculer l'état du tableau à la prochaine ittération
     */
    fun calculateNextState() {
        val newBoard = BoardHelper.initEmptyBoard(height, width)
        val helper = BoardElementHelper(board.value!!)
        val patateHelper = PatateHelper(width, height, board.value!!)
        // Vérification de chaque cellule.
        for (i in 0..height) {
            for (j in 0..width) {
                // Si la case est déjà remplie par une explosion on ne fait rien
                if (newBoard[i][j] == CellTypeEnum.EXPLOSION) {
                    continue
                }
                val info = helper.getNeighbourInfo(i, j)
                val newState = when (board.value!![i][j]) {
                    CellTypeEnum.VIDE -> handleNextStateEmptyCell(info)
                    CellTypeEnum.SAUCE -> handleNextStateSauceCell(info)
                    CellTypeEnum.SAUCISSE -> handleNexStateSaucisseCell(info)
                    CellTypeEnum.DINER -> handleNextStateDinerCell(info)
                    CellTypeEnum.PATATE -> patateHelper.explodePatate(i, j, newBoard)
                    CellTypeEnum.EXPLOSION -> CellTypeEnum.VIDE
                }
                if (newState == CellTypeEnum.EXPLOSION) {
                    interruptUi.postValue(true)
                }
                // Ajout du nouveau élément dans le tableau
                newBoard[i][j] = newState
            }
        }
        // Remplacement des informations de l'ancien tableau par le nouveau.
        board.postValue(newBoard)
    }

    /**
     * Permet de gérer les cellules vide lors du passage à l'état suivant
     *
     * @param info les informations sur le voisinage de la case qui est a déterminer.
     */
    private fun handleNextStateEmptyCell(info: NeighbourInfo): CellTypeEnum {
        // Cas de la création d'une sauce.
        if (info.nbSauce == 2) {
            return CellTypeEnum.SAUCE
        }
        // Dans les autres cas, rien ne se passe.
        return CellTypeEnum.VIDE
    }

    /**
     * Permet de gérer les cellules contenant des sauce lors du passage à l'état suivant
     *
     * @param info les informations sur le voisinage de la case qui est a déterminer.
     */
    private fun handleNextStateSauceCell(info: NeighbourInfo): CellTypeEnum {
        // Cas ou la sauce meurt
        if (isElementDying(info) || info.nbSauce == 0) {
            return CellTypeEnum.VIDE
        }
        // Cas ou la saucisse se transforme en saucisse.
        if (info.nbSauce == 3) {
            return CellTypeEnum.SAUCISSE
        }
        // Dans les autres cas, la sauce survit
        return CellTypeEnum.SAUCE
    }

    /**
     * Permet de gérer les cellules contenant des saucisses lors du passage à l'état suivant.
     *
     * @param info les informations sur le voisinage de la case qui est a déterminer.
     */
    private fun handleNexStateSaucisseCell(info: NeighbourInfo): CellTypeEnum {
        // Cas ou la saucisse meurt
        if (isElementDying(info)) {
            return CellTypeEnum.VIDE
        }
        // Cas ou la saucisse se transforme diner
        if (info.nbSaucisse == 3) {
            return CellTypeEnum.DINER
        }
        // Dans les autre cas, la saucisse survit
        return CellTypeEnum.SAUCISSE
    }

    /**
     * Permet de gérer les cellules contenant des diner lors du passage à l'état suivant.
     *
     * @param info les informations sur le voisinage de la case qui est a déterminer.
     */
    private fun handleNextStateDinerCell(info: NeighbourInfo): CellTypeEnum {
        // Cas ou le diner meurt
        if (info.nbDiner > 5 || info.nbSaucisse > 3 || info.nbSauce > 3) {
            return CellTypeEnum.VIDE
        }
        // Cas ou le diner se transforme en patate bleu
        if (info.nbDiner == 4) {
            return CellTypeEnum.PATATE
        }
        // Dans les autres cas, le diner survit
        return CellTypeEnum.DINER
    }

    /**
     * Permet de vérifier qu'un élément du tableau va mourrir.
     *
     * @param info les informations sur l'élément en cours.
     */
    private fun isElementDying(info: NeighbourInfo): Boolean {
        return info.nbDiner > 3 || info.nbSaucisse > 3 || info.nbSauce > 3
    }

}