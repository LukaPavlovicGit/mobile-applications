package com.example.dnevnjak.utilities

import androidx.compose.ui.graphics.Color

class Constants {
    companion object{
        const val SHARED_PREFERENCES_PATH = "java.com.example.dnevnjak.utilities";

        const val EMAIL_KEY = "email_key"
        const val USERNAME_KEY = "username_key"

        const val LOGIN_FRAGMENT_TAG = "login_fragment_tag"
        const val MAIN_FRAGMENT_TAG = "main_fragment_tag"

        val LOW_PRIORITY_COLOR = Color.Green
        val MID_PRIORITY_COLOR = Color.Yellow
        val HIGH_PRIORITY_COLOR = Color.Red
    }


}

//// fragments
//public static final String TODO_FRAGMENT_TAG = "todo";
//public static final String IN_PROGRESS_FRAGMENT_TAG = "in_progress";
//public static final String DONE_FRAGMENT_TAG = "done";
//public static final String EDIT_NEW_FRAGMENT_TAG = "edit_new_fragment_tag";
//public static final String SINGLE_TICKET_FRAGMENT_TAG = "single_ticket_fragment_tag";
//public static final String LOGIN_FRAGMENT_TAG = "log_in_fragment_tag";
//public static final String MAIN_FRAGMENT_TAG = "main_fragment_tag";