package com.example.a2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a2.databinding.ContactListItemBinding

class ContactListAdapter(
    private var contactList: List<Contact>,
    private val onContactClick: (Contact) -> Unit
) : RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>() {

    // ViewHolder class
    class ContactViewHolder(val binding: ContactListItemBinding) : RecyclerView.ViewHolder(binding.root)

    // Create ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ContactListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    // Bind data to ViewHolder
    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contactList[position]
        holder.binding.contactNameTextView.text = contact.firstName + " " + contact.lastName
        holder.itemView.setOnClickListener { onContactClick(contact) }
    }

    // Return the number of contacts
    override fun getItemCount() = contactList.size

    // Update the contact list
    fun updateList(newList: List<Contact>) {
        contactList = newList
        notifyDataSetChanged()
    }
}