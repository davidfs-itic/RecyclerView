package com.example.recyclerview.login

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Usuari(

    @SerializedName("nom_usuari") val  nom_usuari: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("contrassenya") val contrassenya: String?,


): Parcelable
