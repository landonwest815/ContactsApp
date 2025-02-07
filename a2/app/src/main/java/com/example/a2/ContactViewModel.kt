package com.example.a2

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

data class Contact(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String
)

class ContactViewModel : ViewModel() {
    private val _observableList = MutableStateFlow<List<Contact>>(emptyList())
    val observableList: StateFlow<List<Contact>> = _observableList

    fun newItem(firstName: String, lastName: String, email: String, phoneNumber: String) {
        val newContact = Contact(UUID.randomUUID().toString(), firstName, lastName, email, phoneNumber)
        _observableList.value = _observableList.value + newContact // Reassign with new list
    }

    fun removeItem(contact: Contact) {
        _observableList.value = _observableList.value.filter { it.id != contact.id } // Create a new list
    }

    fun getContactById(id: String): Contact? {
        return _observableList.value.find { it.id == id }
    }
}