package com.example.a2

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a2.databinding.FragmentContactListBinding
import kotlinx.coroutines.launch

class ContactListFragment : Fragment() {
    // get our Shared ViewModel
    private val viewModel: ContactViewModel by activityViewModels()

    // get the Contact List Binding
    private var _binding: FragmentContactListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeContacts()
        setupClickListeners()
    }

    // Setup the RecyclerView for navigation
    private fun setupRecyclerView() {
        binding.contactsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ContactListAdapter(emptyList()) { contact ->
                navigateToDetails(contact.id)
            }
        }
    }

    // Observe the Contact List for changes
    private fun observeContacts() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.observableList.collect { contactList ->
                    (binding.contactsRecyclerView.adapter as ContactListAdapter).updateList(contactList)
                    binding.contactsCountTextView.text = contactList.size.toString()
                }
            }
        }
    }

    // Setup the Add Button
    private fun setupClickListeners() {
        binding.addButton.setOnClickListener {
            // get all the field data
            val fName = binding.firstNameEditText.text.toString().trim()
            val lName = binding.lastNameEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            val phone = binding.phoneNumberEditText.text.toString().trim()

            // make sure that none of them were left empty
            if (fName.isEmpty() || lName.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                Toast.makeText(requireContext(), "Missing Field(s)", Toast.LENGTH_SHORT).show()
            } else {
                // create the new contact
                viewModel.newItem(fName, lName, email, phone)

                // clear the entered fields
                binding.firstNameEditText.text?.clear()
                binding.lastNameEditText.text?.clear()
                binding.emailEditText.text?.clear()
                binding.phoneNumberEditText.text?.clear()

                // clear focus on text views
                binding.root.clearFocus()

                // Hide the keyboard
                val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)

                // Show a toast confirmation
                Toast.makeText(requireContext(), "Contact Added", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Navigate to the Contact Detail Fragment (pass in the contact id)
    private fun navigateToDetails(contactId: String) {
        val bundle = Bundle().apply {
            putString("contactId", contactId)
        }
        findNavController().navigate(R.id.contactDetailFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}