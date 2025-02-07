package com.example.a2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.a2.databinding.FragmentContactDetailBinding

class ContactDetailFragment : Fragment() {
    // get our Shared ViewModel
    private val viewModel: ContactViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the Contact Detail Layout
        val binding = FragmentContactDetailBinding.inflate(inflater, container, false)

        // Get the clicked on Contact
        val contactId = arguments?.getString("contactId")
        val contact = viewModel.getContactById(contactId.orEmpty())

        // make sure the contact was pulled correctly
        if (contact == null) {
            Toast.makeText(requireContext(), "Contact not found", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack() // Exit fragment
            return binding.root
        }

        // Populate the data
        binding.contactNameTextView.text = "${contact?.firstName} ${contact?.lastName}"
        binding.contactEmailTextView.text = "Email:   " + contact?.email
        binding.contactPhoneTextView.text = "Phone:   " + contact?.phoneNumber

        // Delete Button
        binding.deleteButton.setOnClickListener {
            if (contact != null) {
                viewModel.removeItem(contact)
                Toast.makeText(requireContext(), "Contact deleted", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack() // Navigate back to the list
            }
        }

        return binding.root
    }
}