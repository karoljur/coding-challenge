package com.example.codingchallenge.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.codingchallenge.R
import com.example.codingchallenge.models.StockTicker
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI
import javax.net.ssl.SSLSocketFactory

class MainActivity : AppCompatActivity() {
    private lateinit var webSocketClient: WebSocketClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        initWebSocket()
    }

    override fun onPause() {
        super.onPause()
        webSocketClient.close()
    }

    private fun initWebSocket() {
        createWebSocketClient(URI(STOCK_TICKER_WEB_SOCKET_URL))
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

    private fun setupTickersData(message: String?){
        message?.let {
            val moshi = Moshi.Builder().build()
            val listOfTickersType = Types.newParameterizedType(List::class.java, StockTicker::class.java)
            val adapter: JsonAdapter<List<StockTicker>> = moshi.adapter(listOfTickersType)
            val stockTickersList = adapter.fromJson(message)
            runOnUiThread { refreshUI(stockTickersList) }
        }
    }

    private fun refreshUI(stockTickersList: List<StockTicker>?){

    }

    private fun subscribe() {
        webSocketClient.send("")
    }

    companion object {
        const val STOCK_TICKER_WEB_SOCKET_URL = "wss://interviews.yum.dev/ws/stocks"
    }
}