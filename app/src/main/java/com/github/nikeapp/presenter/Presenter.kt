package com.example.todoapp.presenter

import com.github.nikeapp.view.IMainActivity

class Presenter {

    var view: IMainActivity? = null

    fun onBind(view: IMainActivity){
        this.view = view
    }

    fun loadData(){
        // todo hold a reference to the previous
        // todo list of data.
        //view.displayData()
    }
}