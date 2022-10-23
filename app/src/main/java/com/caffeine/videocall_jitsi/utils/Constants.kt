package com.saadahmedsoft.base.utils

import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class Constants {
    object App {
        const val APP_NAME = "Doctor BD"
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
        const val SMS_BASE_URL = "https://api.smsq.global/api/v2/"
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