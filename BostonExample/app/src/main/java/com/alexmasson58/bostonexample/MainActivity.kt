package com.alexmasson58.bostonexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import butterknife.BindView
import butterknife.ButterKnife
import com.alexmasson58.bostonkotlin.loader.BostonLoader

class MainActivity : AppCompatActivity() {

    @BindView(R.id.bostonLoader)
    lateinit var bostonLoader: BostonLoader


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        bostonLoader.requestLayout()
        bostonLoader.animateMe()

        Handler().postDelayed(
                {
                    setWorking()
                    Handler().postDelayed(
                            {
                                setError()


                            }, 2000
                    )


                }, 2000
        )

    }

    fun setWorking() {
        bostonLoader.setWorking()
        Handler().postDelayed({
            bostonLoader.setStandBy()
        }, 2000)
    }

    fun setError() {
        bostonLoader.setError()
        Handler().postDelayed({
            bostonLoader.setStandBy()
        }, 2000)
    }
}
