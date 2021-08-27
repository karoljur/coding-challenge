package com.example.codingchallenge

import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.MutableLiveData
import androidx.test.platform.app.InstrumentationRegistry
import com.example.codingchallenge.api.StockRepository
import com.example.codingchallenge.models.StockDetail
import com.example.codingchallenge.ui.StockDetailDisplay
import com.example.codingchallenge.utils.PriceUtils
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
@RunWith(AndroidJUnit4::class)
class StockDetailDisplayUnitTest {
    private val stockId: String = "YUM"
    private val stockRepository = mock(StockRepository::class.java)
    private val lifecycleOwner: LifecycleOwner = mock(LifecycleOwner::class.java)
    private val lifecycle : LifecycleRegistry = LifecycleRegistry(lifecycleOwner)
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

        println("testtest"+mutableLiveData.hasActiveObservers().toString())
    }

    @Test
    fun testUiContent() {
        val testData = StockDetailTestData.getStockDetailTestData()
        val name: TextView = stockDetailDisplay.findViewById(R.id.detail_display_name)
        val price: TextView = stockDetailDisplay.findViewById(R.id.detail_display_price)
        assertEquals(testData.name, name.text)
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

