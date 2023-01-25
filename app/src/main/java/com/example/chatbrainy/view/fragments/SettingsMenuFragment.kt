package com.example.chatbrainy.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.*
import com.example.chatbrainy.R
import com.example.chatbrainy.view.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


class SettingsMenuFragment : Fragment() {
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        userId = FirebaseAuth.getInstance().currentUser?.uid

        var docRef: DocumentReference = db.collection("Users").document(userId!!)
        docRef .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            view?.findViewById<TextView>(R.id.tvAccount)?.text = querySnapshot?.getString("email")
        }
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
            manager.beginTransaction().setCustomAnimations(
                R.anim.enter_left_to_right,
                R.anim.exit_left_to_right,
                R.anim.enter_right_to_left,
                R.anim.exit_right_to_left
            )
                .replace(R.id.fragmentContainerView, nextFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()

        }

        view.findViewById<TextView>(R.id.tvHelp).setOnClickListener{
            val manager:FragmentManager = activity?.supportFragmentManager!!
            val nextFragment:Fragment = HelpFragment()
            manager.beginTransaction().setCustomAnimations(
                R.anim.enter_left_to_right,
                R.anim.exit_left_to_right,
                R.anim.enter_right_to_left,
                R.anim.exit_right_to_left
            )
                .replace(R.id.fragmentContainerView, nextFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()

        }

        view.findViewById<TextView>(R.id.tvPolicy).setOnClickListener{
            val manager:FragmentManager = activity?.supportFragmentManager!!
            val nextFragment:Fragment = PolicyFragment()
            manager.beginTransaction().setCustomAnimations(
                R.anim.enter_left_to_right,
                R.anim.exit_left_to_right,
                R.anim.enter_right_to_left,
                R.anim.exit_right_to_left
            )
                .replace(R.id.fragmentContainerView, nextFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()

        }

        view.findViewById<TextView>(R.id.tvChat).setOnClickListener{
            val manager:FragmentManager = activity?.supportFragmentManager!!
            val nextFragment:Fragment = ChatConfigFragment()
            manager.beginTransaction().setCustomAnimations(
                R.anim.enter_left_to_right,
                R.anim.exit_left_to_right,
                R.anim.enter_right_to_left,
                R.anim.exit_right_to_left
            )
                .replace(R.id.fragmentContainerView, nextFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()

        }

        view.findViewById<TextView>(R.id.tvExit).setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val intent = Intent (getActivity(), LoginActivity::class.java)
            activity?.finishAffinity()
            getActivity()?.startActivity(intent)
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