package com.example.mywebbrowser

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.email
import org.jetbrains.anko.sendSMS
import org.jetbrains.anko.share

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 웹 뷰 기본설정
        webView.apply{
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
        }

        webView.loadUrl("https://www.google.com")

        urlEditText.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                webView.loadUrl(urlEditText.text.toString())
                true
            } else {
                false
            }
        }

        //컨텍스트 메뉴 등록
        registerForContextMenu(webView)

    }

    override fun onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.action_google, R.id.action_home -> {
                webView.loadUrl("https://www.google.com")
                return true
            }

            R.id.action_naver -> {
                webView.loadUrl("http://www.naver.com")
                return true
            }

            R.id.action_daum -> {
                webView.loadUrl("http://www.daum.net")
                return true
            }

            R.id.action_call -> {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:031-123-4567")
                if(intent.resolveActivity(packageManager) != null){
                    startActivity(intent)
                }
                return true
            }

            R.id.action_send_text -> {
                sendSMS("031-123-4567", webView.url)
                return true
            }

            R.id.action_email -> {
                email("guswls6212@gmail.com", "좋은 사이트", webView.url)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.action_share -> {
                //페이지 공유
                share(webView.url)
                return true
            }
            R.id.action_browser -> {
                // 기본 웹 브라우저에서 열기
                browse(webView.url)
                return true
            }
        }
        return super.onContextItemSelected(item)
    }
}
