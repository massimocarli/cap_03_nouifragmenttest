package uk.co.massimocarli.nouifragmenttest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CounterFragment.CounterListener {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    if (savedInstanceState == null) {
      val fragment = CounterFragment().apply {
        retainInstance = true
      }
      supportFragmentManager
        .beginTransaction()
        .add(R.id.container, fragment)
        .commit()
    }
  }

  override fun count(countValue: Int) {
    runOnUiThread { output.text = "Counter: $countValue" }
  }
}
