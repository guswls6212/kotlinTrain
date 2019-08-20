package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    private var time = 0
    private var timerTask: Timer? = null
    private var isRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            isRunning = !isRunning

            if(isRunning){
                start()
            } else{
                pause()
            }

        }
    }

    private fun start(){
        fab.setImageResource(R.drawable.ic_pause_black_24dp)

        //오래 걸리는 작업을 하는 워커 스레드에서 동작하는 코드
        timerTask = timer(period = 10){
            //0.01초마다 이 변수를 증가
            time++
            val sec = time / 100
            val milli = time % 100
            //워커 스레드에서는 UI를 조작할 수 없으므로 runOnUiThread로 감싸서 UI조작이 가능하게
            runOnUiThread {
                secTextView.text = "$sec"
                milliTextView.text = "$milli"
            }
        }
    }

    private fun pause(){
        fab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        // 실행중인 타이머가 있다면 타이머를 취소
        timerTask?.cancel()
    }
}
