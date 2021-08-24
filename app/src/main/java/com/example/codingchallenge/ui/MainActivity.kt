package com.example.codingchallenge.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codingchallenge.R
import com.example.codingchallenge.models.StockTicker
import com.example.codingchallenge.models.StockTickerViewModel
import com.example.codingchallenge.ui.adapters.StockListAdapter
import com.example.codingchallenge.ui.listeners.StockListListener
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import javax.net.ssl.SSLSocketFactory


class MainActivity : AppCompatActivity(), StockListListener {
    private lateinit var webSocketClient: WebSocketClient
    private var recyclerView: RecyclerView? = null
    private var adapter: StockListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.stockTickerRecycler)
        adapter = StockListAdapter(this)
        val layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.addItemDecoration(dividerItemDecoration)
        recyclerView?.adapter = adapter
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
        adapter?.submitList(viewModelsList)
    }

    private fun subscribe() {
        // No need to provide any information, we can send empty string
        webSocketClient.send("")
    }

    override fun onStockClicked(id: String) {
        val intent = Intent(baseContext, StockDetailDisplay::class.java)
        intent.extras?.putString(STOCK_ID_KEY, id)
        startActivity(intent)
    }

    companion object {
        const val STOCK_ID_KEY = "stockIdKey"
        const val STOCK_TICKER_WEB_SOCKET_URL = "wss://interviews.yum.dev/ws/stocks"
    }
}