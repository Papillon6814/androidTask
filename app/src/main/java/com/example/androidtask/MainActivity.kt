package com.example.androidtask

import android.content.Context
import android.database.DatabaseUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast.LENGTH_LONG
import com.example.androidtask.models.TitleModel
import com.example.androidtask.storage.TitleDataBaseHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val titleDataBaseHelper = TitleDataBaseHelper(this)

        if(titleDataBaseHelper.getRecordNum() != 0) {
            val initialText = titleDataBaseHelper.loadRecord()
            title_text.setText(initialText, TextView.BufferType.NORMAL)
        }

        // titleバーにリスナー設置
        title_text.setOnKeyListener { v, keyCode, event ->
            val editText = v as EditText

            // enterがクリックされたときの処理
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER ) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(editText.windowToken, 0)

                val text = editText.text

                val title = TitleModel(
                    "0",
                    text.toString()
                )

                Log.d("OMG debug:", titleDataBaseHelper.getRecordNum().toString())


                val result: Boolean

                if(titleDataBaseHelper.getRecordNum() == 0) {
                    result = titleDataBaseHelper.insertTitle(title)
                } else {
                    result = titleDataBaseHelper.updateTitle(title)
                }

                if(result) {
                    Toast.makeText(this, "Title saved", LENGTH_LONG).show()
                }
            }

            true
        }

    }
}
