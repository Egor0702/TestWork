package com.example.testwork

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    var result : String = ""
    lateinit var text: TextView
    lateinit var valuteObj : Valute
    private val retrofitImpl: RetrofitImpl = RetrofitImpl()
    lateinit var swipeRefreshLayout : SwipeRefreshLayout
    lateinit var editText :EditText
    lateinit var spinner : Spinner
    lateinit var mapValute : MutableMap<String, Double>
    lateinit var setValute : MutableSet <String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        text = findViewById(R.id.text)
        editText = findViewById(R.id.edit_text)
        spinner = findViewById(R.id.spinner)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

        val thred : Thread = Thread() {
            try {
                TimeUnit.HOURS.sleep(1)
            }catch (ex:Exception){ex.printStackTrace() }
            Log.d("Thred", "Обновляем")
            sendRequest() }
        thred.start()

        swipeRefreshLayout.setOnRefreshListener {
            sendRequest()
            swipeRefreshLayout.isRefreshing = false
        }
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.black
        )
        editText.setText("0")

        if (savedInstanceState != null) { // проверяем запуск данной активности и если это так присваиваем переменным значением
            valuteObj = savedInstanceState.getParcelable("valute")!!
            text.setText(valuteObj.toString())
            editText.setText(result)
            return
        }
        sendRequest()
    }

    private fun sendRequest() {
        retrofitImpl.getRequest().showValue().enqueue(object :
                Callback<DataValute> {

            override fun onResponse(call: Call<DataValute>, response: Response<DataValute>) {
                if (response.isSuccessful) {

                    processingData(response.body(), null)
                } else {
                    processingData(null, Throwable("Ответ не получен"))
                }
            }

            override fun onFailure(call: Call<DataValute>, t: Throwable) {
                processingData(null, t)
            }
        })
    }

    private fun processingData(dataValute: DataValute?, error: Throwable?) {
        if (dataValute == null || error != null) {
            //Ошибка
            Toast.makeText(this, error!!.message, Toast.LENGTH_SHORT).show()
            Log.d("Main", error!!.message.toString())
        } else {
            valuteObj = dataValute.Valute
            if (valuteObj == null) {
                //Данные не пришли
                Toast.makeText(this, "Данные не были получены", Toast.LENGTH_LONG).show()
            }
            else {
                text.text = valuteObj.toString()
                setSpinner()
            }
        }
    }
    interface ShowValute {
        @GET("daily_json.js")
        fun showValue(): Call<DataValute>
    }
    class RetrofitImpl {
        fun getRequest(): ShowValute {
            val retroVal = Retrofit.Builder()
                .baseUrl("https://www.cbr-xml-daily.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retroVal.create(ShowValute::class.java)
        }
    }
    override fun onSaveInstanceState(outState: Bundle) { // на случай уничтожения активности сохраним ключевую информацию
        super.onSaveInstanceState(outState)
        outState.putString("result", result)
        outState.putParcelable("valute", valuteObj) // текущий вопрос
    }
    fun setSpinner(){
        var s : String = " "
        mapValute = mutableMapOf<String,Double>()
        try {
            mapValute.put(valuteObj.AUD.Name!!, valuteObj.AUD.Value!!)
        mapValute.put(valuteObj.AZN.Name.toString(), valuteObj.AZN.Value!!)
        mapValute.put(valuteObj.EUR.Name.toString(), valuteObj.EUR.Value!!)
        mapValute.put(valuteObj.USD.Name.toString(), valuteObj.USD.Value!!)
        mapValute.put(valuteObj.GBP.Name.toString(), valuteObj.GBP.Value!!)
        mapValute.put(valuteObj.BYN.Name.toString(), valuteObj.BYN.Value!!)
        mapValute.put(valuteObj.BGN.Name.toString(), valuteObj.BGN.Value!!)
        mapValute.put(valuteObj.BRL.Name.toString(), valuteObj.BRL.Value!!)
        mapValute.put(valuteObj.HUF.Name.toString(), valuteObj.HUF.Value!!)
        mapValute.put(valuteObj.HKD.Name.toString(), valuteObj.HKD.Value!!)
        mapValute.put(valuteObj.DKK.Name.toString(), valuteObj.DKK.Value!!)
        mapValute.put(valuteObj.INR.Name.toString(), valuteObj.INR.Value!!)
        mapValute.put(valuteObj.KZT.Name.toString(), valuteObj.KZT.Value!!)
        mapValute.put(valuteObj.CAD.Name.toString(), valuteObj.CAD.Value!!)
        mapValute.put(valuteObj.KGS.Name.toString(), valuteObj.KGS.Value!!)
        mapValute.put(valuteObj.CNY.Name.toString(), valuteObj.CNY.Value!!)
        mapValute.put(valuteObj.MDL.Name.toString(), valuteObj.MDL.Value!!)
        mapValute.put(valuteObj.NOK.Name.toString(), valuteObj.NOK.Value!!)
        mapValute.put(valuteObj.PLN.Name.toString(), valuteObj.PLN.Value!!)
        }catch(e : NullPointerException){
            e.printStackTrace()
            Log.d("Main", "Пустая ссылка")
        }
        setValute = mapValute.keys
        var valuteArray = arrayOfNulls<String>(setValute.size) 
                for (i in 0..setValute.size - 1) {
                valuteArray[i] = setValute.elementAt(i)
                println(valuteArray[i])
            }
        }
        if (valuteArray != null) {
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, valuteArray)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.setAdapter(adapter)
            Log.d("Main", "Выполнено")
        }else
            Log.d("Main", "Пустой массив")

        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?,
                                        itemSelected: View?, selectedItemPosition: Int, selectedId: Long) {
                var userValue = 0.0
                try{
                    userValue = (editText.text.toString()).toDouble()
                }catch(e:Exception){
                    Toast.makeText(this@MainActivity,R.string.warning, Toast.LENGTH_LONG).show()
                }
                var selected = spinner.getSelectedItem().toString()
                var valuteValue = mapValute.get(selected)
                result = (userValue * valuteValue!!).toString()
                editText.setText (result)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}
