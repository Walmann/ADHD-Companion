package it.walmann.adhdcompanion.MyObjects

import android.content.Context
import com.google.gson.Gson
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import java.io.File
import java.util.Calendar

fun debugDeleteInternalStorage(context: Context) {
    val dir = context.filesDir
    val listOfFiles = dir.list()!!
    for (file in listOfFiles) {
        if (file != "profileInstalled") {
            val f = File(dir, file)
            f.delete()
        }
    }
}


//@Serializable
class myClass(val name: String, val number: Int, val calendar: Calendar)

fun ThingsToTest(context: Context) {


    val data2 = myReminder()

    val data = myClass(name = "myName! æøå", number = 1942, calendar = Calendar.getInstance())

    val encodedgson = Gson().toJson(data)

//    val encoded = Json.encodeToString(data)

    val temp1 = ""

}

//object myClassSerializer : KSerializer<myClass> {
//    override val descriptor: SerialDescriptor
//        get() = TODO("Not yet implemented")
//
//    override fun serialize(encoder: Encoder, value: myClass) {
//
//        val string = value.toString()
//        encoder.encodeString(string)
//        val temp = ""
//    }
//
//    override fun deserialize(decoder: Decoder): myClass {
//        val string = decoder.decodeString()
//        return myClass(name = string, number = 0, calendar = Calendar.getInstance())
//    }
//
//
//}