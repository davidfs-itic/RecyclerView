package com.example.recyclerview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActivityRegistreViewModel : ViewModel() {


    private var _nomUsuari:String=""
    private var _email :String=""
    private var _contrassenya :String=""

    private val _formularivalid=MutableLiveData<Boolean>(false)
    val  formularivalid:MutableLiveData<Boolean> = _formularivalid

    private val _errorNomUsuari=MutableLiveData<String>("")
    val errorNomUsuari:LiveData<String> = _errorNomUsuari


    fun actualitzanomUsuari(nomusuari: String) {
        _nomUsuari = nomusuari
    }



    public fun comprova_nomusuari() {

        if (_nomUsuari.isEmpty()) {
            _errorNomUsuari.value = "El nom d'usuari és obligatori"
        }else{
            _errorNomUsuari.value = ""
        }
    }
    fun actualitzaemail(email:String){
        TODO("Actualitza el livedata i fes les comprovacions.")
    }

    //Comprovació genérica. Comprova tots els camps.
    fun comprovadadesusuari() {
        comprova_nomusuari()
        //comprova_email()
        //TODO("Fer totes les comprovacions pertinents")
    }


    fun registrarusuari() {
        comprovadadesusuari()
        if (_formularivalid.value!!) {
            TODO("Cridar api retrofit per registrar usuari")
        }

    }

}
