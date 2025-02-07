package com.example.a2

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a2.databinding.FragmentContactListBinding
import kotlinx.coroutines.launch


class ContactListFragment : Fragment() {
    private val viewModel: ContactViewModel by activityViewModels()

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentContactListBinding.inflate(layoutInflater, container, false)
        val recycler by lazy{ binding.contactsRecyclerView }

        with(recycler){
            layoutManager = LinearLayoutManager(requireContext()) //NEW KOTLIN THING
            adapter = ContactListAdapter(listOf()){
                viewModel.removeItem(it)
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.observableList.collect {

                    (recycler.adapter as ContactListAdapter).updateList(it)
                    Log.e("LIST SIZE", "${it.size}")

                    binding.contactsCountTextView.text = "${it.size}"
                }
            }}

        binding.addButton.setOnClickListener{
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            if(name.isEmpty() || email.isEmpty()) {
                return@setOnClickListener //NEW KOTLIN THING
            }
            //if the lists get big
            //new Item returns the index of the added item
            //then call recycler.adaptor.notifyItemChanged(index)
            viewModel.newItem(name, email)
            binding.nameEditText.text?.clear()
            binding.emailEditText.text?.clear()

        }

        return binding.root
    }
}