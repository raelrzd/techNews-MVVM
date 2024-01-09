package br.com.alura.technews.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.repository.NoticiaRepository
import br.com.alura.technews.repository.Resource

class VisualizaNoticiaViewModel(
    private val noticiaId: Long, private val repository: NoticiaRepository
) : ViewModel() {

    private val noticiaEncontrada = buscaPorId(noticiaId)

    fun buscaPorId(noticiaId: Long): LiveData<Noticia?> {
        return repository.buscaPorId(noticiaId)
    }

    fun remove(): LiveData<Resource<Void?>> {
        return noticiaEncontrada.value?.run {
            repository.remove(this)
        } ?: MutableLiveData<Resource<Void?>>().also {
            it.value = Resource(null, "Noticia não encontrada")
        }
    }
}