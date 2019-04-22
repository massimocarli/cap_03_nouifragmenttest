package uk.co.massimocarli.nouifragmenttest

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

class CounterFragment : Fragment() {

  /**
   * The Tag for the Log
   */
  private val LOG_TAG = "CounterFragment"

  /*
     * Key for the extra about counter state
     */
  private val COUNTER_EXTRA = "uk.co.maxcarli.nouifragmenttest.extra.COUNTER_EXTRA"

  /**
   * Interface to implement to listen to the CounterThread
   */
  interface CounterListener {

    /**
     * @param countValue The value of the counter to notify
     */
    fun count(countValue: Int)
  }

  /*
     * The Counter
     */
  private var counter: Int = 0

  /*
     * The CounterThread
     */
  private lateinit var counterThread: CounterThread

  /*
     * The listener of the counter
     */
  private var counterListener: CounterListener? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    retainInstance = false
    Log.d(LOG_TAG, "ONCREATE INVOKED ON FRAGMENT")
    if (savedInstanceState != null) {
      counter = savedInstanceState.getInt(COUNTER_EXTRA, 0)
    }
    counterThread = CounterThread().apply {
      start()
    }
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    outState.putInt(COUNTER_EXTRA, counter)
  }

  override fun onDestroy() {
    super.onDestroy()
    Log.d(LOG_TAG, "ONDESTROY INVOKED ON FRAGMENT")
    counterThread.stopCounter()
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    Log.d(LOG_TAG, "ONATTACH INVOKED ON FRAGMENT")
    if (context is CounterListener) {
      counterListener = context
    }
  }

  override fun onDetach() {
    super.onDetach()
    counterListener = null
    Log.d(LOG_TAG, "ONDETACH INVOKED ON FRAGMENT")
  }

  inner class CounterThread : Thread() {

    private var mRunner = true

    override fun run() {
      super.run()
      Log.d(LOG_TAG, "Counter STARTED from $counter")
      while (mRunner) {
        try {
          Thread.sleep(500L)
        } catch (ie: InterruptedException) {
        }

        counter++
        Log.d(LOG_TAG, "Counter in Fragment is: $counter")
        counterListener?.run {
          count(counter)
        }
      }
      Log.d(LOG_TAG, "Counter ENDED at $counter")
    }

    fun stopCounter() {
      mRunner = false
    }
  }
}