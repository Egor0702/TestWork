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
    lateinit var text: TextView
    lateinit var valuteObj : Valute
    private val retrofitImpl: RetrofitImpl = RetrofitImpl()
    lateinit var swipeRefreshLayout : SwipeRefreshLayout
    lateinit var editText :EditText
    lateinit var spinner : Spinner

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
        if (savedInstanceState != null) { // проверяем запуск данной активности и если это так присваиваем переменным значением
            valuteObj = savedInstanceState.getParcelable("valute")!!
            text.setText(valuteObj.toString())
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
        outState.putParcelable("valute", valuteObj) // текущий вопрос
    }
    fun setSpinner(){
    val mainArray : Array<String?> = arrayOf(valuteObj.AUD.Name,
            valuteObj.AZN.Name,
            valuteObj.EUR.Name,
            valuteObj.USD.Name,
            valuteObj.GBP.Name,
            valuteObj.BYN.Name,
            valuteObj.BGN.Name,
            valuteObj.BRL.Name,
            valuteObj.HUF.Name,
            valuteObj.HKD.Name,
            valuteObj.DKK.Name,
            valuteObj.INR.Name,
            valuteObj.KZT.Name,
            valuteObj.CAD.Name,
            valuteObj.KGS.Name,
            valuteObj.CNY.Name,
            valuteObj.MDL.Name,
            valuteObj.NOK.Name,
            valuteObj.PLN.Name)
        if (mainArray != null) {
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mainArray)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.setAdapter(adapter)
        }else
            Log.d("Main", "Пустой массив")
    }
}