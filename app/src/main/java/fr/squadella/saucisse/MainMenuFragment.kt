package fr.squadella.saucisse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import fr.squadella.saucisse.databinding.FragmentMainMenuBinding


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
        // Inflating layout
        binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        // On définit l'action qui va être réalisée lors de l'appuie sur le bouton pour démarrer une partie.
        binding.startGameButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_gameFragment)
        }
        return binding.root
    }





}