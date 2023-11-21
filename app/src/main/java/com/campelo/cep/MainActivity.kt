package com.campelo.cep

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.campelo.cep.api.EnderecoAPI
import com.campelo.cep.api.RetrofitHelper
import com.campelo.cep.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.create

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val retrofit by lazy {
        RetrofitHelper.retrofit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnProcurar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val retorno = recuperarCep()

                MainScope().launch {
                    binding.txtResultado.text = retorno
                }
            }
        }
    }

    private suspend fun recuperarCep(): String{
        val servico = retrofit.create(EnderecoAPI::class.java)
        val cepDigitado = binding.txtCep.text.toString()
        val endereco = servico.recuperarCep(cepDigitado).body()
        var retornoEndereco = ""

        if(endereco != null){
            retornoEndereco = """
                Rua: ${endereco.logradouro}
                Bairro: ${endereco.bairro}
                Localidade: ${endereco.localidade}, ${endereco.uf}
            """.trimIndent()
        }

        return retornoEndereco
    }
}