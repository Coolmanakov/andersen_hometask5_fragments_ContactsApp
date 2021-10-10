package com.example.andersen_hometask5_fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.Fragment

class ContactFragment : Fragment(R.layout.fragment_contacts) {
    private lateinit var rootLinearLayout: LinearLayout
    private lateinit var first_contact: LinearLayout
    private lateinit var second_contact: LinearLayout
    private lateinit var third_contact: LinearLayout
    private lateinit var fourth_contact: LinearLayout



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        first_contact = view.findViewById(R.id.first_contact)
        second_contact = view.findViewById(R.id.second_contact)
        third_contact = view.findViewById(R.id.third_contact)
        fourth_contact = view.findViewById(R.id.fourth_contact)
        rootLinearLayout = view.findViewById(R.id.root)

        if (arguments != null) {
            var pos = arguments?.get(DetailFragment.LINEAR_LAYOUT_POS) as Int

            var newContact = Contact(
                arguments?.get(DetailFragment.NAME_EXTRA).toString(),
                arguments?.get(DetailFragment.SURNAME_EXTRA).toString(),
                arguments?.get(DetailFragment.PHONE_NUMBER_EXTRA).toString()
            )
            MainActivity.contactsList.removeAt(pos)
            MainActivity.contactsList.add(pos, newContact)
        }
        showContactList()

        setLinearLayoutClickListeners()

    }

    companion object {
        const val CONTACTS_FRAGMENT_TAG = "CONTACTS_FRAGMENT"
        fun newInstance() = ContactFragment()
        private lateinit var name: String
        private lateinit var surname: String
        private lateinit var phoneNumber: String

    }

    private fun setLinearLayoutClickListeners() {
        first_contact.setOnClickListener {
            contactDetails(it as LinearLayout)
            MainActivity.listener.showDetails(name, surname, phoneNumber, 0)
        }
        second_contact.setOnClickListener {
            contactDetails(it as LinearLayout)
            MainActivity.listener.showDetails(name, surname, phoneNumber, 1)
        }
        third_contact.setOnClickListener {
            contactDetails(it as LinearLayout)
            MainActivity.listener.showDetails(name, surname, phoneNumber, 2)
        }
        fourth_contact.setOnClickListener {
            contactDetails(it as LinearLayout)
            MainActivity.listener.showDetails(name, surname, phoneNumber, 3)
        }

    }

    fun contactDetails(linLayout: LinearLayout) {
        for (i in 0 until 3) {
            var child = linLayout[i] as TextView
            when (i) {
                0 -> surname =
                    child.text.toString()
                1 -> name =
                    child.text.toString()
                else -> phoneNumber =
                    child.text.toString()
            }

        }
    }

    private fun showContactList() {
        for (i in 0 until 4) {
            var currLayout = rootLinearLayout.getChildAt(i) as LinearLayout
            for (y in 0 until 3) {
                var currTextView = currLayout.getChildAt(y) as TextView
                currTextView.text = when (y) {
                    0 -> MainActivity.contactsList[i].surname
                    1 -> MainActivity.contactsList[i].name
                    else -> MainActivity.contactsList[i].number
                }
            }

        }

    }



}

