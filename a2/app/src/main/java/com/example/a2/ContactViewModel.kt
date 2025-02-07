package com.example.a2

import android.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

data class ContactItem(val name: String, val email: String)

//take adaptor as a constructor param?
class ContactViewModel : ViewModel() {
    private val rand = Random.Default //just a normal private class member

    //MODEL part of MVVM
    //Simple enough here to just store it as a member of our VM, won't be the case in general

    private val actualList: MutableStateFlow<List<ContactItem>> =
        MutableStateFlow( // represents a value that changes over time
            listOf( //each of these values is a list that can be changed
                ContactItem("John Doe", "johndoe@gmail.com")
            )
        )

    // the public version is read only
    val observableList = actualList as StateFlow<List<ContactItem>>

    //pick a random color
    fun newItem(name: String, email: String) {
        actualList.update { list ->
            list.toMutableList() + ContactItem(
                name,
                email
            )
        }
        //adaptor.notifyElementAdded(actualList.value.size)

    }

    fun removeItem(item: ContactItem) {

        actualList.update { list ->
            list.filter { x -> !x.equals(item) } //create a new list without that item
        }

    }
}