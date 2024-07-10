package com.example.contactapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class ContactViewmodel(private val repository: ContactRepository):ViewModel() {

    val allContacts: LiveData<List<Contact>> = repository.allContacts.asLiveData()

    fun addContact(image : String,name: String ,phoneNumber: String,email: String){
        viewModelScope.launch {
            val contact = Contact(0,image = image,name = name,phoneNumber = phoneNumber,email = email)
            repository.insert(contact)
        }
    }

    fun updateContact(contact: Contact){
        viewModelScope.launch {
            repository.delete(contact)
        }
    }

    fun deleteContact(contact: Contact){
        viewModelScope.launch {
            repository.delete(contact)
        }
    }
}

class ContactViewModelFactory(private val repository: ContactRepository):ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelclass:Class<T>):T {
        if (modelclass.isAssignableFrom(ContactViewmodel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ContactViewmodel(repository) as T
        }else{
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}