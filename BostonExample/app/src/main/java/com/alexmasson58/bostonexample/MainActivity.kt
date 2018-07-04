package com.alexmasson58.bostonexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.alexmasson58.bostonkotlin.loader.BostonLoader
import com.alexmasson58.bostonkotlin.textviews.UnlockableBostonTextView

class MainActivity : AppCompatActivity() {

    @BindView(R.id.bostonLoader)
    lateinit var bostonLoader: BostonLoader

    @BindView(R.id.unlockbtv)
    lateinit var unlockbtv: UnlockableBostonTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        bostonLoader.requestLayout()


        bostonLoader.animateMe()

        animateMe()

    }

    private fun animateMe() {
        Handler().postDelayed(
                {
                    setWorking()
                    Handler().postDelayed(
                            {
                                setError()
                                /* animateMe()*/

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

    @OnClick(R.id.unlockbtv)
    fun changeLock() {
        unlockbtv.switchLock()
    }

    @OnClick(R.id.bostonLoader)
    fun loaderClicked() {
        animateMe()
    }
}
