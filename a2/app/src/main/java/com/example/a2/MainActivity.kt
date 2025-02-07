package com.example.a2

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a2.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    // Only compute on first access, lazy init
    private val binding: ActivityMainBinding by lazy {ActivityMainBinding.inflate(layoutInflater)}

    // the recyclerview
    private val recycler by lazy{ binding.contactsRecyclerView }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Log.e("SCREEN", "Screen wide? ${resources.getBoolean(R.bool.isScreenWide)}")

        //"DELEGATED PROPERTY".  Access to myViewModel calls viewModels.getValue which manages lifecycle stuff
        val myViewModel : ContactViewModel by viewModels()

        // Use with so you don't have to repeatedly
        // say recycler.something everytime you use its
        // properties
        with(recycler){
            layoutManager = LinearLayoutManager(this@MainActivity) //NEW KOTLIN THING
            adapter = ContactListAdapter(listOf()){
                myViewModel.removeItem(it)
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                myViewModel.observableList.collect {

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
            myViewModel.newItem(name, email)
            binding.nameEditText.text?.clear()
            binding.emailEditText.text?.clear()

        }

        setContentView(binding.root)
    }
}