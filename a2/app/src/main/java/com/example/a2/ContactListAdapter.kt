package com.example.a2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a2.databinding.ContactListItemBinding

class ContactListAdapter(private var contactItems: List<ContactItem>,
                      private val onClick : (item: ContactItem) -> Unit
) : RecyclerView.Adapter<ContactListAdapter.ViewHolder>() {


    fun updateList(newList: List<ContactItem>){
        contactItems = newList
        notifyDataSetChanged()
    }

    //wrapper for the a view of a single item.  Created/destroyed as needed
    inner class ViewHolder(val binding: ContactListItemBinding):
        RecyclerView.ViewHolder(binding.root)

    //called by RV when it needs a new view to show an item in
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //parent, false are important parameters for sizing reasons!!
        return ViewHolder(ContactListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = contactItems.size

    //called when filling in a view with an item from the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = contactItems[position]
        holder.binding.textView.text = item.name
        holder.binding.button2.setOnClickListener{
            //vm.remove(item)
            onClick(item)
        }
    }

}