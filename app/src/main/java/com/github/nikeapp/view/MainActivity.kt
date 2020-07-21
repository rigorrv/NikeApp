package com.github.nikeapp.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.presenter.Presenter
import com.github.nikeapp.CustomAdapter
import com.github.nikeapp.R
import com.github.nikeapp.model.Items
import com.github.retrofittorecyclerviewkotlin.ApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.*


class MainActivity : AppCompatActivity() , IMainActivity {

    val TAG : String = "TAG"
    var word :String = "wat"
    var ratingSort : Boolean = true
    var isConnected: Boolean = true

    val presenter: Presenter by lazy{
        Presenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setSupportActionBar(toolbar)
        toggleButton.visibility = View.GONE



        connectionChecking()


    }

    //checking connection with internet
    fun connectionChecking(){
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        isConnected = activeNetwork?.isConnectedOrConnecting == true

        Log.d(TAG, "checkConnection: $isConnected")

        if(isConnected){
            loadList()
        }else{

            loadListLocal()

        }
    }

    override fun displayData(dataSet: Items.Info) {
        //adapter.data = listOf(dataSet)
    }

    override fun initViews() {
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onBind() {
        presenter.onBind(this)
        presenter.loadData()
    }

    override fun invokeCreateNewTask() {


    }


    //load from Local File (shared Preferences) if connection Doesn't detected
    fun loadListLocal() {



                val gson = Gson()
                try {
                    val br = BufferedReader(
                        FileReader("/data/data/com.github.nikeapp/files/codebeautify.json")
                    )

                    //convert the json string back to object
                    val obj: Items = gson.fromJson(br, Items::class.java)
                    showData(obj)

                    // System.out.println(obj)
                } catch (e: IOException) {
                    e.printStackTrace()
                }



    }

    //load from Network if connection detected
    fun loadList() {


        val retrofit = Retrofit.Builder()
                .baseUrl("https://mashape-community-urban-dictionary.p.rapidapi.com")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        val api = retrofit.create(ApiService::class.java)

        api.getRecipes(word).enqueue(object : Callback<Items> {
            override fun onFailure(call: Call<Items>, t: Throwable) {
               // Log.d("Data", "${t}")

            }

            override fun onResponse(
                call: Call<Items>, response: Response<Items>) {
                //Log.d(TAG, "onResponse: $host , $key ")
                if (response.isSuccessful) {
                    //Log.d(TAG, "onResponse: Response is successful...")
                }else{
                    Log.d(TAG, "Response code is: "+response.code())
                }
                Log.d(TAG, "onResponse: "+response.body())
                showData(response.body()!!)
                //saving json

                val f = File("/data/data/com.github.nikeapp/files/codebeautify.json")
                if (f.exists()) {
                    Log.d(TAG, "onResponse: File Exist")
                } else {
                    val jsonName = "codebeautify.json"
                    Log.d(TAG, "onResponse: No existe")
                    saveJson(jsonName, GsonBuilder().setPrettyPrinting().create().toJson(response.body()))
                }



                //Log.d("Data", "Working = " + GsonBuilder().setPrettyPrinting().create().toJson(response.body()) )

            }
        })
    }


    //transfer data to adapter
    fun showData(users: Items) {
        progress.visibility = View.GONE
        toggleButton.visibility = View.VISIBLE
        progressSort.visibility = View.GONE



        if (ratingSort) {
            var sort = users.list.sortedBy { it.thumbs_up  }
            recyclerView.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = CustomAdapter(sort)
            }
        }else {
            var sort = users.list.sortedByDescending { it.thumbs_up }

            recyclerView.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = CustomAdapter(sort)
            }
        }
    }

    //search word in JSON (network connection)
    fun searchWord(view: View) {
        connectionChecking()
        word = tv_search.text.toString()
        if(!word.isEmpty() && isConnected == true) {
            progress.visibility = View.GONE
            progressSort.visibility = View.VISIBLE

            Log.d(TAG, "searchWord: $word")
            loadList()
            tv_search.setText("")
        }else{
            if (isConnected == true){
            AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("you need to add a word")
                .setPositiveButton(android.R.string.ok, null)
                .show()
            }
            if (isConnected==false){
                AlertDialog.Builder(this)
                    .setTitle("Error of Connection")
                    .setMessage("Sorry but you don't have connection, but you can still navigating in the info")
                    .setPositiveButton(android.R.string.ok, null)
                    .show()
            }
        }

    }

    //sort by thumb
    fun sortRating(view: View) {
        toggleButton.visibility = View.GONE
        progressSort.visibility = View.VISIBLE
        ratingSort = !ratingSort
        if (isConnected) {
            loadList()
        }else{
            loadListLocal()
        }


    }

    //saving in shared preferences
    fun saveJson(name: String?, data: String?) {
        try {
            val outputStreamWriter = OutputStreamWriter(
                openFileOutput(
                    name,
                    Context.MODE_PRIVATE
                )
            )
            outputStreamWriter.write(data)
            outputStreamWriter.close()
        } catch (e: IOException) {
            Log.e("Exception", "File write failed: $e")
        }
    }



}




