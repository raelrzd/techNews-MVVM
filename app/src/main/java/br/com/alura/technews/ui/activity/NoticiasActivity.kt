package br.com.alura.technews.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.alura.technews.R
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.ui.fragment.ListaNoticiaFragment
import br.com.alura.technews.ui.fragment.VisualizaNoticiaFragment

private const val TITULO_APPBAR = "Notícias"

class NoticiasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noticias)
        title = TITULO_APPBAR


        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.add(R.id.activity_noticias_container, ListaNoticiaFragment())
        beginTransaction.commit()

    }

    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)
        if (fragment is ListaNoticiaFragment) {
            fragment.quandoSelecionaNoticia = {
                abreVisualizadorNoticia(it)
            }
            fragment.quandoAdicionaNoticia = {
                abreFormularioModoCriacao()
            }
        }
        if (fragment is VisualizaNoticiaFragment) {
            fragment.quandoSelecionaMenuEdicao =
                { noticiaSelecionada -> abreFormularioEdicao(noticiaSelecionada) }
        }
    }

    private fun abreFormularioEdicao(noticia: Noticia) {
        val intent = Intent(this, FormularioNoticiaActivity::class.java)
        intent.putExtra(NOTICIA_ID_CHAVE, noticia.id)
        startActivity(intent)
    }

    private fun abreFormularioModoCriacao() {
        val intent = Intent(this, FormularioNoticiaActivity::class.java)
        startActivity(intent)
    }

    private fun abreVisualizadorNoticia(noticia: Noticia) {
        supportFragmentManager.findFragmentByTag("")
        val transacao = supportFragmentManager.beginTransaction()
        val fragment = VisualizaNoticiaFragment()
        val dados = Bundle()
        dados.putLong(NOTICIA_ID_CHAVE, noticia.id)
        fragment.arguments = dados
        transacao.replace(R.id.activity_noticias_container, fragment)
        transacao.commit()
    }

}
