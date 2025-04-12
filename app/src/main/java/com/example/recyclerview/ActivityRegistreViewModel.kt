package com.example.recyclerview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class EstatRegistre(
    var esValid: Boolean, // Indica si les dades són vàlides
    var errorNomUsuari: String?, // Missatge d'error per al nom d'usuari
    var errorEmail: String?, // Missatge d'error per a l'email
    var errorContrassenya: String? // Missatge d'error per a la contrasenya
)

class ActivityRegistreViewModel : ViewModel() {


    private var _nomUsuari:String=""
    private var _email :String=""
    private var _contrassenya :String=""

    private val _validaciodades = MutableLiveData<EstatRegistre>(EstatRegistre(
        esValid = false,
        errorNomUsuari = "",
        errorEmail = "",
        errorContrassenya = ""
    ))
    val validaciodades: LiveData<EstatRegistre> = _validaciodades


    fun actualitzanomUsuari(nomusuari: String) {
        _nomUsuari = nomusuari
        comprova_nomusuari()
    }

        private fun comprova_nomusuari() {
            if (_nomUsuari.isEmpty()) {
                _validaciodades.value?.errorNomUsuari = "El nom d'usuari és obligatori"
            }
        }
    fun actualitzaemail(email:String){
        TODO("Actualitza el livedata i fes les comprovacions.")
    }

    //Comprovació genérica. Comprova tots els camps.
    fun comprovadadesusuari() {
        comprova_nomusuari()
        //comprova_email()
        TODO("Fer totes les comprovacions pertinents")
    }


    fun registrarusuari() {
        comprovadadesusuari()
        if (_validaciodades.value?.esValid!!) {
            TODO("Cridar api retrofit per registrar usuari")
        }

    }

}
