package com.github.nikeapp.presenter


import com.github.nikeapp.model.Items
import com.github.nikeapp.model.Repository
import com.github.nikeapp.view.ICreateNewTask

class PresenterCreateNewTask {

    var view: ICreateNewTask? = null

    fun onBind(view: ICreateNewTask) {
        this.view = view
    }

    fun validateData(textInput: Items): Boolean {
        if (textInput == null) {
            return true
        } else {
            createModel(textInput)
            return false
        }
    }

    private fun createModel(textInput: Items) {
        view!!.passDataBack(Repository.createObject(textInput))
    }
}



