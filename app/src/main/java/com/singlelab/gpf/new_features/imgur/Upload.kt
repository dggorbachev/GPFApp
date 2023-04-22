package com.singlelab.gpf.new_features.imgur

import android.content.Context
import android.util.Log
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class LinkImg(@SerializedName("data") val data: LinkImgInside)
data class LinkImgInside(@SerializedName("link") val link: String)
object Upload {
    fun getRequest(
        theContext: Context,
        headerParams: HashMap<String, String>,
        urlRequested: String?,
        callback: (data: String?) -> Unit,
        method: Int,
        bodyParams: String?,
        messageToastSuccess: String?,
        messageToastFaillure: String?
    ) {
        val bodyContent = bodyParams ?: ""

        urlRequested!!.httpPost()
            .header(headerParams)
            .body(bodyContent)
            .responseString { _, _, result ->
                when (result) {
                    is Result.Failure -> {
                        val ex = result.getException()
                        Log.d("gpf", ex.toString())
                        Log.d("gpf", "FAILED")
                        callback(null)
//                        if (messageToastFaillure != null && messageToastFaillure != "") {
//                            Toast.makeText(theContext, messageToastFaillure, Toast.LENGTH_SHORT)
//                                .show()
//                        }
                    }

                    is Result.Success -> {
                        val data = result.get().trimIndent()

                        val gson = Gson()
                        callback(
                            gson.fromJson(data, LinkImg::class.java).data.link
                        )

//                        if (messageToastSuccess != null && messageToastSuccess != "") {
//                            Toast.makeText(theContext, messageToastSuccess, Toast.LENGTH_SHORT)
//                                .show()
//                        }
                    }
                }
            }
    }
}