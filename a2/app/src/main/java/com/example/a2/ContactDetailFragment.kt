package com.example.a2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a2.databinding.FragmentContactDetailBinding

/**
 * A simple [Fragment] subclass.
 * Use the [ContactDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactDetailFragment : Fragment() {
    private lateinit var binding: FragmentContactDetailBinding
    private var clickCallback  : () -> Unit = {}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentContactDetailBinding.inflate(layoutInflater)
        binding.button2.setOnClickListener{
            clickCallback()
        }
        return binding.root
    }

    public fun setListener(listener: () -> Unit){
        clickCallback = listener
    }
}