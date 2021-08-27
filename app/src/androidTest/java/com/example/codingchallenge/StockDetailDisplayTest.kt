package com.example.codingchallenge

import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.codingchallenge.api.StockRepository
import com.example.codingchallenge.models.StockDetail
import com.example.codingchallenge.ui.StockDetailDisplay
import com.example.codingchallenge.ui.StockRepositoryWrapper
import com.example.codingchallenge.utils.PriceUtils
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
class StockDetailDisplayTest {
    private val stockId: String = "1"
    private val stockRepositoryWrapper = mock(StockRepositoryWrapper::class.java)
    private val stockRepository = stockRepositoryWrapper.stockRepository
    private val lifecycleOwner: LifecycleOwner = mock(LifecycleOwner::class.java)
    private val lifecycle = LifecycleRegistry(mock(LifecycleOwner::class.java))
    private val stockDetailDisplay: StockDetailDisplay
    private val instrumentationContext = InstrumentationRegistry.getInstrumentation().context
    private val mutableLiveData: MutableLiveData<StockDetail> = MutableLiveData()


    init {
        `when`(lifecycleOwner.lifecycle).thenReturn(lifecycle)
        `when`(stockRepository.getResponseLiveData()).thenReturn(mutableLiveData)
        `when`(stockRepository.getStockDetails(stockId)).then {
            mutableLiveData.postValue(StockDetailTestData.getStockDetailTestData())
        }
        stockDetailDisplay =
            StockDetailDisplay(stockId, stockRepository, lifecycleOwner, instrumentationContext)
        lifecycle.currentState = Lifecycle.State.RESUMED
    }

    @Test
    fun testUiContent() {
        mutableLiveData.observe(lifecycleOwner, object: Observer<StockDetail> {
            override fun onChanged(t: StockDetail?) {
                println("testtesttest ${mutableLiveData.hasActiveObservers()}")
            }
        })
        val testData = StockDetailTestData.getStockDetailTestData()
        val name: TextView = stockDetailDisplay.findViewById(R.id.detail_display_name)
        val price: TextView = stockDetailDisplay.findViewById(R.id.detail_display_price)
        assert(name.text == testData.name)
        assert(price.text == PriceUtils.doublePriceRoundedToString(testData.price))
    }

    object StockDetailTestData {
        fun getStockDetailTestData(): StockDetail {
            return StockDetail(
                "YUM",
                "Yum! Brands, Inc.",
                86.94764755999583,
                listOf("Food", "Tech"),
                105.44,
                "1441 Gardiner Lane Louisville, KY 40213 United States",
                "https://interviews.yum.dev/static/images/yum_logo.png",
                "https://www.yum.com"
            )
        }
    }
}