package fr.squadella.saucisse.game

import fr.squadella.saucisse.constant.CellTypeEnum

/**
 * Permet de récupérer les informations sur les cases voisines.
 */
class BoardElementHelper(private val board: List<List<CellTypeEnum>>) {

    /**
     * Permet de récupérer l'information sur les voisins de la case.
     */
    fun getNeighbourInfo(column: Int, line: Int): NeighbourInfo {
        val info = NeighbourInfo()
        // Vérification des colonnes qui sont sur le haut
        if (column > 1) {
            // Vérification qu'on ne dépasse pas vers la gauche.
            if (line > 1) {
                // Ajout de la case en haut à gauche
                addNeighbourByType(board[column - 1][line - 1], info)
            }
            // Ajout de la case en haut
            addNeighbourByType(board[column - 1][line], info)
            // Vérification que l'on ne dépasse pas vers la droite
            if (line < board[column].size - 1) {
                // Ajout de la case en haut à droite
                addNeighbourByType(board[column - 1][line + 1], info)
            }
        }

        if (line > 1) {
            // Ajout de la case à gauche
            addNeighbourByType(board[column][line - 1], info)
        }

        if (line < board[column].size - 1) {
            // Ajout de la case à droite
            addNeighbourByType(board[column][line + 1], info)
        }

        if (column < board.size - 1) {
            // Vérification qu'on ne dépasse pas vers la gauche.
            if (line > 1) {
                // Ajout de la case en haut à gauche
                addNeighbourByType(board[column + 1][line - 1], info)
            }
            // Ajout de la case en haut
            addNeighbourByType(board[column + 1][line], info)
            // Vérification que l'on ne dépasse pas vers la droite
            if (line < board[column].size - 1) {
                // Ajout de la case en haut à droite
                addNeighbourByType(board[column + 1][line + 1], info)
            }
        }

        return info
    }

    /**
     * Permet d'ajouter les information sur le nouveau voisin qui a été trouvé.
     */
    fun addNeighbourByType(type: CellTypeEnum, info: NeighbourInfo) {
        when (type) {
            CellTypeEnum.SAUCE -> info.nbSauce++
            CellTypeEnum.SAUCISSE -> info.nbSaucisse++
            CellTypeEnum.DINER -> info.nbDiner++
            else -> return // On ne fait rien pour les autres type de case.
        }
    }

}