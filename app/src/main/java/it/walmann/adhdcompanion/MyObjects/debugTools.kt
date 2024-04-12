package it.walmann.adhdcompanion.MyObjects

import android.content.Context
import java.io.File

fun debugDeleteInternalStorage(context: Context){
    val dir = context.filesDir
    val listOfFiles = dir.list()!!
    for (file in listOfFiles)
    {
        if (file != "profileInstalled"){
            val f = File(dir, file)
            f.delete()
        }
    }
}