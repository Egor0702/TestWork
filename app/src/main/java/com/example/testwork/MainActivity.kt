package com.example.testwork
import android.os.Bundle
import android.os.SystemClock.sleep
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.lang.Thread.sleep
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    var result : String = ""
    lateinit var text: TextView
    lateinit var valuteObj : Valute
    private val retrofitImpl: RetrofitImpl = RetrofitImpl()
    lateinit var swipeRefreshLayout : SwipeRefreshLayout
    lateinit var editText :EditText
    lateinit var spinner : Spinner
    lateinit var mapValute : MutableMap
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
        spinner.setOnItemSelectedListener{
            try{
        var userValue = Integer.parseInt(editText.text)
            }catch{Toast.makeText(this, "Ошибка. Введите число", Toast.LENGTH_LONG)}
            var selected = spinner.getSelectedItem().toString()
            var valuteValue = mapValute.get(selected)
            result = (userValue * valuteValue).toString()
            editText.setText (result)
            
        }
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
        mapValute = mutableOfMap()
        mapValute.put(valuteObj.AUD.Name, valuteObj.AUD.Value)
        mapValute.put(valuteObj.AZN.Name, valuteObj.AZN.Value)
        mapValute.put(valuteObj.EUR.Name, valuteObj.EUR.Value)
        mapValute.put(valuteObj.USD.Name, valuteObj.USD.Value)
        mapValute.put(valuteObj.GBP.Name, valuteObj.GBP.Value)
        mapValute.put(valuteObj.BYN.Name, valuteObj.BYN.Value)
        mapValute.put(valuteObj.BGN.Name, valuteObj.BGN.Value)
        mapValute.put(valuteObj.BRL.Name, valuteObj.BRL.Value)
        mapValute.put(valuteObj.HUF.Name, valuteObj.HUF.Value)
        mapValute.put(valuteObj.HKD.Name, valuteObj.HKD.Value)
        mapValute.put(valuteObj.DKK.Name, valuteObj.DKK.Value)
        mapValute.put(valuteObj.INR.Name, valuteObj.INR.Value)
        mapValute.put(valuteObj.KZT.Name, valuteObj.KZT.Value)
        mapValute.put(valuteObj.CAD.Name, valuteObj.CAD.Value)
        mapValute.put(valuteObj.KGS.Name, valuteObj.KGS.Value)
        mapValute.put(valuteObj.CNY.Name, valuteObj.CNY.Value)
        mapValute.put(valuteObj.MDL.Name, valuteObj.MDL.Value)
        mapValute.put(valuteObj.NOK.Name, valuteObj.NOK.Value)
        mapValute.put(valuteObj.PLN.Name, valuteObj.PLN.Value)
         set = mapValute.keys
        var valuteArray = arrayOfNulls<String>(set.size)
        for(s in set)
        valuteArray += s
        if (valuteArray != null) {
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mainArray)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.setAdapter(adapter)
        }else
            Log.d("Main", "Пустой массив")
    }
}
