package com.github.nikeapp.view

import com.github.nikeapp.model.Items

interface ICreateNewTask {
    fun onBindPresenter()
    fun inputData()
    fun initView()
    fun passDataBack(dataItem: Items)
}


