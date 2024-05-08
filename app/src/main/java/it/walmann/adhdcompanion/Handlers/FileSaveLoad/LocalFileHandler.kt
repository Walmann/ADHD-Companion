package it.walmann.adhdcompanion.Handlers.FileSaveLoad

import android.content.Context
import java.io.FileOutputStream
import java.io.ObjectOutputStream

fun saveFileToInternalStorage(context: Context, FileLoc: String, ObjectToSave: Any) {
    val fos: FileOutputStream =
        context.openFileOutput(FileLoc, Context.MODE_PRIVATE)
    val oos = ObjectOutputStream(fos)
    oos.writeObject(ObjectToSave)
    oos.close()
}