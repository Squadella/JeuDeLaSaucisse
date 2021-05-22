package fr.squadella.saucisse.game

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import fr.squadella.saucisse.constant.CellTypeEnum
import fr.squadella.saucisse.databinding.FragmentGameBinding
import fr.squadella.saucisse.util.RandomUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * Permet d'afficher la zone de jeu.
 */
class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding

    // La liste des éléments qui vont être affichés.
    private val sprites = ArrayList<ArrayList<ImageView>>()

    private val viewModel: GameViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Récupération du binding pour modifier les éléments sur la vue
        binding = FragmentGameBinding.inflate(inflater, container, false)

        // Récupération de la taille de l'écran pour calculer le nombre d'éléments qui vont être affichés.
        val displayMetrics: DisplayMetrics = resources.displayMetrics

        val widthPx = displayMetrics.widthPixels
        val heightPx = displayMetrics.heightPixels

        // Ajout de l'observeur pour prendre en compte les modifications sur le tableau.
        viewModel.board.observe(viewLifecycleOwner) {
            refreshBoard(it)
        }

        // Initialisation du tableau de jeu
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.initBoard(heightPx, widthPx)
        }

        binding.gameContainer.setOnClickListener {
            viewModel.calculateNextState()
        }

        // Initialisation des images pour le jeu
        return binding.root
    }

    /**
     * Permet de rafraichir le tableau avec les données provenant du view model.
     * @param board le tableau des données.
     */
    private fun refreshBoard(board: List<List<CellTypeEnum>>) {
        // Si le tableau contenant les éléments qui sont a afficher est vide, on l'init.
        if (sprites.isEmpty()) {
            initBoard(board)
            return
        }
        // Mise à jour des éléments du tableau de jeu.
        sprites.forEachIndexed { i, lines ->
            lines.forEachIndexed { j, sprite ->
                SpriteHelper.updateSprite(sprite, board[i][j], requireContext())
            }
        }
    }

    /**
     * Création des éléments d'affichage.
     * @param board le tableau de jeu
     */
    private fun initBoard(board: List<List<CellTypeEnum>>) {
        // Si le tableau est vide, on ne fait rien.
        if (board.isEmpty()) {
            print("En fait non")
            return
        }
        // Création des éléments graphiques
        for (line in board) {
            val boardLine = ArrayList<ImageView>()
            sprites.add(boardLine)
            val linearLayout = createLineLayout()
            for (column in line) {
                val imageView = SpriteHelper.initSprite(column, requireContext())
                boardLine.add(imageView)
                linearLayout.addView(imageView)
            }
            binding.gameLayout.addView(linearLayout)
        }

    }



    /**
     * Permet d'initialiser le layout pour afficher une ligne.
     */
    private fun createLineLayout(): LinearLayout {
        return LinearLayout(context).apply {
            // Setting the size of the layout
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.HORIZONTAL
        }
    }

}