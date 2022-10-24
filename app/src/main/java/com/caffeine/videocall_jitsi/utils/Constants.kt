package com.saadahmedsoft.base.utils

import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import com.saadahmedsoft.base.utils.Constants.App.APP_NAME

class Constants {
    object App {
        const val APP_NAME = "Doctor BD"
    }

    object Database {
        private val databaseReference = FirebaseDatabase.getInstance().reference.child(APP_NAME)
        val userRef = databaseReference.child("users")
    }

    object Booleans {
        const val TRUE = true
        const val FALSE = false
    }

    object Durations {
        const val SNACK_SHORT = Snackbar.LENGTH_SHORT
        const val SNACK_LONG = Snackbar.LENGTH_LONG
        const val TOAST_SHORT = Toast.LENGTH_SHORT
        const val TOAST_LONG = Toast.LENGTH_LONG
    }

    object Api {
        const val BASE_URL = "https://topseba.com/api/"
    }

    object Auth {
        const val LOGGED_IN_STATE = "LOGGED_IN_STATE"
    }

    object Messages {
        const val UNEXPECTED_ERROR_OCCURRED = "Unexpected Error Occurred"
        const val NO_INTERNET = "No internet available"
    }

    object Data {
        const val BLANK = ""
    }
}