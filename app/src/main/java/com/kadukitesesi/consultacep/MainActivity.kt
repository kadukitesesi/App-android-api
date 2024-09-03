package com.kadukitesesi.consultacep

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kadukitesesi.consultacep.Retrofit.EndPoint
import com.kadukitesesi.consultacep.Retrofit.Posts
import retrofit2.Callback
import com.kadukitesesi.consultacep.Retrofit.RetrofitNetwork
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var corpoPost: TextView
    lateinit var tituloPost: TextView
    lateinit var editText: EditText
    lateinit var btnBusca: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //getData()
         corpoPost = findViewById(R.id.corpoPost)
         editText = findViewById(R.id.buscarPost)
         btnBusca = findViewById(R.id.buscar)
        tituloPost = findViewById(R.id.tituloPost)

        btnBusca.setOnClickListener {
            val postId = editText.text.toString().trim()
            if (postId.isNotEmpty()) {
                getPostById(postId.toInt())
            } else {
                Toast.makeText(this,"Por favor, insira um id válido", Toast.LENGTH_SHORT).show()
            }
        }


    }


    private fun getPostById(id: Int) {
        val retrofitClient = RetrofitNetwork.getInstanceUrl("https://jsonplaceholder.typicode.com")
        val endPoint = retrofitClient.create(EndPoint::class.java)

        val call = endPoint.getPostsById(id)

        call.enqueue(object : Callback<Posts> {
            override fun onFailure(call: Call<Posts>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Posts>, response: Response<Posts>) {
                if (response.isSuccessful) {
                    val post = response.body()
                    if (post != null) {
                        tituloPost.setText(post.title)
                        corpoPost.setText(post.body)
                        }

                } else {
                    corpoPost.text = "Error: ${response.code()}"
                }
            }
        })


        /* fun getData() {
         val retrofitClient = RetrofitNetwork
             .getInstanceUrl("https://jsonplaceholder.typicode.com")

         val endPoint = retrofitClient.create(EndPoint::class.java)

         val callback = endPoint.getPosts()

         callback.enqueue(object :  Callback<List<Posts>> {
             override fun onFailure(call: Call<List<Posts>>, t: Throwable) {
                 Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
             }

             override fun onResponse(call: Call<List<Posts>>, response: Response<List<Posts>>) {
                 response.body()?.forEach {
                     textView.text = textView.text.toString().plus(it.body)
                 }
             }
         })

     } */

    }
}