package br.com.alura.technews.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel

class ListaNoticiasViewModel: ViewModel() {

    init {
        Log.i("raeldev", "Criando ViewModel ")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("raeldev", "onCleared: Destruindo ViewModel")
    }


}