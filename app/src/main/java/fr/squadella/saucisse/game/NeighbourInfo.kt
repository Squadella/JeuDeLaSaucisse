package fr.squadella.saucisse.game

/**
 * Contient les informations sur les voisins d'une case
 */
class NeighbourInfo {

    var nbSauce = 0

    var nbSaucisse = 0

    var nbDiner = 0

    val totalNeighbour: Int
        get() {
            return nbSauce + nbSaucisse + nbDiner
        }
}