package br.com.alura.technews.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.alura.technews.R
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.ui.fragment.extensions.mostraErro
import br.com.alura.technews.ui.recyclerview.adapter.ListaNoticiasAdapter
import br.com.alura.technews.ui.viewmodel.ListaNoticiasViewModel
import kotlinx.android.synthetic.main.fragment_lista_noticia.fragment_lista_noticias_fab_salva_noticia
import kotlinx.android.synthetic.main.fragment_lista_noticia.fragment_lista_noticias_recyclerview
import org.koin.android.viewmodel.ext.android.viewModel

private const val MENSAGEM_FALHA_CARREGAR_NOTICIAS = "Não foi possível carregar as novas notícias"

class ListaNoticiaFragment : Fragment() {

    private val viewModel: ListaNoticiasViewModel by viewModel()
    private val adapter by lazy {
        ListaNoticiasAdapter(context = requireContext())
    }
    var quandoSelecionaNoticia: (noticia: Noticia) -> Unit = {}
    var quandoAdicionaNoticia: () -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        buscaNoticias()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_lista_noticia, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configuraRecyclerView()
        configuraFabAdicionaNoticia()
    }

    private fun configuraFabAdicionaNoticia() {
        fragment_lista_noticias_fab_salva_noticia.setOnClickListener {
            quandoAdicionaNoticia()
        }
    }

    private fun configuraRecyclerView() {
        val divisor = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        fragment_lista_noticias_recyclerview.addItemDecoration(divisor)
        fragment_lista_noticias_recyclerview.adapter = adapter
        configuraAdapter()
    }

    private fun configuraAdapter() {
        adapter.quandoItemClicado = { quandoSelecionaNoticia(it) }
    }

    private fun buscaNoticias() {
        viewModel.buscaTodos().observe(this, Observer { resource ->
            resource.dado?.let { adapter.atualiza(it) }
            resource.erro?.let { mostraErro(MENSAGEM_FALHA_CARREGAR_NOTICIAS) }
        })
    }

}

