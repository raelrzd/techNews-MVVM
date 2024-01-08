package br.com.alura.technews.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.repository.NoticiaRepository

class ListaNoticiasViewModel(private val repository: NoticiaRepository) : ViewModel() {

    init {
        Log.i("raeldev", "Criando ViewModel ")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("raeldev", "onCleared: Destruindo ViewModel")
    }

    fun buscaTodos(quandoSucesso: (List<Noticia>) -> Unit, quandoFalha: (String?) -> Unit) {
        repository.buscaTodos(quandoSucesso, quandoFalha)
    }


}