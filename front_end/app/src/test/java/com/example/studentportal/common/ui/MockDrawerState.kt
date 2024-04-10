package com.example.studentportal.common.ui

import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.STATE_IDLE

class MockDrawerState: DrawerLayout.DrawerListener {

    val state: State
        get() = _state
    private var _state = State.CLOSED

    enum class State{
        CLOSED, OPEN
    }
    override fun onDrawerSlide(drawerView: View, slideOffset: Float) = Unit

    override fun onDrawerOpened(drawerView: View) = Unit

    override fun onDrawerClosed(drawerView: View) = Unit

    override fun onDrawerStateChanged(newState: Int){
        this._state = if(newState == STATE_IDLE){State.CLOSED}else{State.OPEN}
    }

}