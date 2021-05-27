package fr.squadella.saucisse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import fr.squadella.saucisse.databinding.FragmentMainMenuBinding
import kotlin.system.exitProcess


/**
 * Permet d'afficher le menu du jeu.
 */
class MainMenuFragment : Fragment() {

    private lateinit var binding: FragmentMainMenuBinding

    /**
     * Création du menu principal de l'application.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Gestion de la fermeture de l'application pour qu'elle ne reste pas en mémoire.
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            requireActivity().finishAndRemoveTask()
            exitProcess(0)
        }
        // Inflating layout
        binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        // On définit l'action qui va être réalisée lors de l'appuie sur le bouton pour démarrer une partie.
        binding.startGameButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_gameFragment)
        }
        return binding.root
    }



}