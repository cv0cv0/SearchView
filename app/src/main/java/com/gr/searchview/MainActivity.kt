package com.gr.searchview

import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*
import org.joor.Reflect

class MainActivity : AppCompatActivity() {
    lateinit var searchView: SearchView
    lateinit var searchAutoComplete: SearchView.SearchAutoComplete

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            if (searchAutoComplete.isShown) {
                searchAutoComplete.setText("")
                Reflect.on(searchView).call("onCloseClicked")
            } else {
                finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.search_menu)
        searchView = MenuItemCompat.getActionView(searchItem) as SearchView
        searchAutoComplete= searchView.findViewById(R.id.search_src_text) as SearchView.SearchAutoComplete

        searchView.queryHint = "搜索本地歌曲"
        searchView.onActionViewExpanded()
        searchView.isIconified=true
        Reflect.on(searchView)
                .field("mSearchPlate")
                .get<View>()
                .setBackgroundColor(Color.TRANSPARENT)

        searchAutoComplete.setHintTextColor(Color.parseColor("#9affffff"))
        searchAutoComplete.setTextColor(Color.WHITE)
        searchAutoComplete.textSize = 16f

        val searchFrame=searchView.findViewById(R.id.search_edit_frame) as LinearLayout
        val params=searchFrame.layoutParams as ViewGroup.MarginLayoutParams
        params.leftMargin=0
        searchFrame.layoutParams=params

        return super.onCreateOptionsMenu(menu)
    }

    override fun onMenuOpened(featureId: Int, menu: Menu?): Boolean {
        if (menu?.javaClass?.simpleName == "MenuBuilder"){
            Reflect.on(menu).call("setOptionalIconsVisible",true)
        }
        return super.onMenuOpened(featureId, menu)
    }
}
