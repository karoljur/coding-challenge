package com.example.codingchallenge.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.codingchallenge.R
import com.example.codingchallenge.models.StockTicker
import com.example.codingchallenge.models.StockTickerViewModel
import com.example.codingchallenge.ui.StockListDisplay
import com.example.codingchallenge.ui.listeners.StockListListener
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import javax.net.ssl.SSLSocketFactory

class StockListActivity : AppCompatActivity(), StockListListener {
    private lateinit var webSocketClient: WebSocketClient
    private var stockListDisplay: StockListDisplay? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_list)
        stockListDisplay = findViewById(R.id.main_stock_list_display)
        stockListDisplay?.setStockListListener(this)
    }

    override fun onResume() {
        super.onResume()
        createWebSocketClient(URI(STOCK_TICKER_WEB_SOCKET_URL))
    }

    override fun onPause() {
        super.onPause()
        webSocketClient.close()
    }

    override fun onStockClicked(id: String) {
        val intent = Intent(this, StockDetailActivity::class.java)
        intent.putExtra(STOCK_ID_KEY, id)
        startActivity(intent)
    }

    private fun createWebSocketClient(stockUri: URI?) {
        webSocketClient = object : WebSocketClient(stockUri) {
            override fun onOpen(handshakedata: ServerHandshake?) {
                subscribe()
            }

            override fun onMessage(message: String?) {
                setupTickersData(message)
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                // do nothing
            }

            override fun onError(ex: Exception?) {
                // do nothing
            }
        }
        val socketFactory: SSLSocketFactory = SSLSocketFactory.getDefault() as SSLSocketFactory
        webSocketClient.setSocketFactory(socketFactory)
        webSocketClient.connect()
    }

    private fun setupTickersData(message: String?) {
        message?.let {
            val moshi = Moshi.Builder().build()
            val listOfTickersType =
                Types.newParameterizedType(List::class.java, StockTicker::class.java)
            val adapter: JsonAdapter<List<StockTicker>> = moshi.adapter(listOfTickersType)
            val stockTickersList = adapter.fromJson(message)
            runOnUiThread { refreshUI(stockTickersList) }
        }
    }

    private fun refreshUI(stockTickersList: List<StockTicker>?) {
        val viewModelsList = ArrayList<StockTickerViewModel>()
        stockTickersList?.forEach { viewModelsList.add(StockTickerViewModel(it)) }
        stockListDisplay?.setStockTickerList(viewModelsList)
    }

    private fun subscribe() {
        // No need to provide any information, we can send empty string
        webSocketClient.send("")
    }

    companion object {
        const val STOCK_ID_KEY = "stockIdKey"
        const val STOCK_TICKER_WEB_SOCKET_URL = "wss://interviews.yum.dev/ws/stocks"
    }
}