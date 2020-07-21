package com.github.nikeapp.view

import com.github.nikeapp.ClassNewJson
import com.github.nikeapp.model.Items

interface IMainActivity {
    fun displayData(dataSet: Items.Info)
    fun initViews()
    fun onBind()
    fun invokeCreateNewTask()
}