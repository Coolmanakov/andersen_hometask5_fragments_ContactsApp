package com.example.andersen_hometask5_fragments


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout

class MainActivity : AppCompatActivity(R.layout.activity_main), ContactDetailContract {

    //programmatically track the tablet mode
    private var isDualMode = false

    companion object {
        //the listener implements ContactDetailContract,
        // responsible for displaying and saving new data
        lateinit var listener: ContactDetailContract
        private const val NOT_INITIALIZED_POS = -10000
        val contactsList by lazy {
            mutableListOf(
                Contact(
                    "Иван",
                    "Иванов",
                    "8 (911) 111 11 11"
                ),
                Contact(
                    "Пётр",
                    "Петров",
                    "8 (911) 111 11 11"
                ),
                Contact(
                    "Валерия",
                    "Валерьева",
                    "8 (911) 111 11 11"
                ),
                Contact(
                    "Светлана",
                    "Светланова",
                    "8 (911) 111 11 11"
                ),
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listener = this

        val view = findViewById<FrameLayout>(R.id.contactFrame)
        if (view != null) {
            isDualMode = true
            dualModeTransaction()
        } else singleModeTransaction()

    }

    override fun onBackPressed() {
        //if current fragment is ContactsFragment -> close app
        // else -> popBackStack
        val contactFragment =
            supportFragmentManager.findFragmentByTag(ContactFragment.CONTACTS_FRAGMENT_TAG)
        if (contactFragment != null && contactFragment.isVisible) {
            this.finishAffinity()
        }
        supportFragmentManager.popBackStack()

    }

    /**
     * start smartphone mode
     */
    private fun singleModeTransaction() {
        supportFragmentManager.beginTransaction().run {
            val contactFragment = ContactFragment.newInstance()
            replace(R.id.mainFrame, contactFragment, ContactFragment.CONTACTS_FRAGMENT_TAG)
            commit()
        }

    }

    /**
     * start tablet mode
     */
    private fun dualModeTransaction() {
        supportFragmentManager.beginTransaction().run {
            val contactFragment = ContactFragment.newInstance()
            val detailFragment = DetailFragment.newInstance("", "", "", NOT_INITIALIZED_POS)
            setReorderingAllowed(true)
            replace(R.id.contactFrame, contactFragment, ContactFragment.CONTACTS_FRAGMENT_TAG)
            replace(R.id.detailFrame, detailFragment, DetailFragment.DETAIL_FRAGMENT_TAG)
            commit()
        }

    }

    override fun showDetails(name: String, surname: String, phoneNumber: String, pos: Int) {
        supportFragmentManager.beginTransaction().run {
            val detailFragment = DetailFragment.newInstance(name, surname, phoneNumber, pos)
            if (isDualMode) {
                replace(R.id.detailFrame, detailFragment, DetailFragment.DETAIL_FRAGMENT_TAG)
            } else {
                setReorderingAllowed(true)
                addToBackStack("detailFragment")
                replace(R.id.mainFrame, detailFragment, DetailFragment.DETAIL_FRAGMENT_TAG)
            }
            commit()
        }
    }

    override fun saveDetails(name: String, surname: String, phoneNumber: String, pos: Int) {
        if (pos != NOT_INITIALIZED_POS) {
            if (isDualMode) {
                supportFragmentManager.beginTransaction().run {
                    val contactFragment = ContactFragment.newInstance()
                    contactFragment.arguments = Bundle().apply {
                        putString(DetailFragment.NAME_EXTRA, name)
                        putString(DetailFragment.SURNAME_EXTRA, surname)
                        putString(DetailFragment.PHONE_NUMBER_EXTRA, phoneNumber)
                        putInt(DetailFragment.LINEAR_LAYOUT_POS, pos)
                    }
                    replace(
                        R.id.contactFrame,
                        contactFragment,
                        ContactFragment.CONTACTS_FRAGMENT_TAG
                    )
                    commit()
                }
            } else {
                supportFragmentManager
                    .findFragmentByTag(ContactFragment.CONTACTS_FRAGMENT_TAG)?.apply {
                        arguments = Bundle().apply {
                            putString(DetailFragment.NAME_EXTRA, name)
                            putString(DetailFragment.SURNAME_EXTRA, surname)
                            putString(DetailFragment.PHONE_NUMBER_EXTRA, phoneNumber)
                            putInt(DetailFragment.LINEAR_LAYOUT_POS, pos)
                        }
                    }
            }
        }
    }

}