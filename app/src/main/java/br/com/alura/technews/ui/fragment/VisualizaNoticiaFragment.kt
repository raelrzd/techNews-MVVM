package br.com.alura.technews.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.alura.technews.R
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.ui.activity.NOTICIA_ID_CHAVE
import br.com.alura.technews.ui.fragment.extensions.mostraErro
import br.com.alura.technews.ui.viewmodel.VisualizaNoticiaViewModel
import kotlinx.android.synthetic.main.fragment_visualiza_noticia.fragment_visualiza_noticia_texto
import kotlinx.android.synthetic.main.fragment_visualiza_noticia.fragment_visualiza_noticia_titulo
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

private const val NOTICIA_NAO_ENCONTRADA = "Notícia não encontrada"
private const val MENSAGEM_FALHA_REMOCAO = "Não foi possível remover notícia"

class VisualizaNoticiaFragment : Fragment() {

    private val noticiaId: Long by lazy {
        arguments?.getLong(NOTICIA_ID_CHAVE, 0) ?: throw IllegalArgumentException("Id Inválido")
    }
    private val viewModel: VisualizaNoticiaViewModel by viewModel() { parametersOf(noticiaId) }
    var quandoSelecionaMenuEdicao: () -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        buscaNoticiaSelecionada()
        verificaIdDaNoticia()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return layoutInflater.inflate(R.layout.fragment_visualiza_noticia, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.visualiza_noticia_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.visualiza_noticia_menu_edita -> quandoSelecionaMenuEdicao()
            R.id.visualiza_noticia_menu_remove -> remove()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun buscaNoticiaSelecionada() {
        viewModel.noticiaEncontrada.observe(this, Observer { noticiaEncontrada ->
            noticiaEncontrada?.let {
                preencheCampos(it)
            }
        })
    }

    private fun verificaIdDaNoticia() {
        if (noticiaId == 0L) {
            mostraErro(NOTICIA_NAO_ENCONTRADA)
            activity?.finish()
        }
    }

    private fun preencheCampos(noticia: Noticia) {
        fragment_visualiza_noticia_titulo.text = noticia.titulo
        fragment_visualiza_noticia_texto.text = noticia.texto
    }

    private fun remove() {
        viewModel.remove().observe(this, Observer { resource ->
            if (resource.erro == null) activity?.finish()
            else mostraErro(MENSAGEM_FALHA_REMOCAO)
        })
    }

}