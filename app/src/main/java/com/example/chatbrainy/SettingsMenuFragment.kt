package com.example.chatbrainy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.*
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import com.example.chatbrainy.view.SettingsActivity


class SettingsMenuFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings_menu, container, false)
//        view.findViewById<TextView>(R.id.tvAbout).setOnClickListener{
//            val manager:FragmentManager = activity?.supportFragmentManager
//            Navigation.findNavController(view).navigate(R.id.aboutFragment)
//
//        }

        view.findViewById<TextView>(R.id.tvAbout).setOnClickListener{
            val manager:FragmentManager = activity?.supportFragmentManager!!
            val nextFragment:Fragment = AboutFragment()
            manager.beginTransaction().setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right, R.anim.enter_right_to_left, R.anim.exit_right_to_left)
                .replace(R.id.fragmentContainerView, nextFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()

        }

        view.findViewById<TextView>(R.id.tvHelp).setOnClickListener{
            val manager:FragmentManager = activity?.supportFragmentManager!!
            val nextFragment:Fragment = HelpFragment()
            manager.beginTransaction().setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right, R.anim.enter_right_to_left, R.anim.exit_right_to_left)
                .replace(R.id.fragmentContainerView, nextFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()

        }

        view.findViewById<TextView>(R.id.tvPolicy).setOnClickListener{
            val manager:FragmentManager = activity?.supportFragmentManager!!
            val nextFragment:Fragment = PolicyFragment()
            manager.beginTransaction().setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right, R.anim.enter_right_to_left, R.anim.exit_right_to_left)
                .replace(R.id.fragmentContainerView, nextFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()

        }



        view.findViewById<ImageButton>(R.id.btnBack).setOnClickListener{
            activity?.finish()
        }



        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsMenuFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}