package com.dipuguide.shopsee.utils

import android.content.Context
import android.os.Message
import android.widget.Toast


//For Show Toast Message
fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
