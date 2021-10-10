package com.example.andersen_hometask5_fragments

interface ContactDetailContract {

    fun showDetails(name: String, surname: String, phoneNumber: String, pos : Int)

    fun saveDetails(name: String, surname: String, phoneNumber: String, pos : Int)
}