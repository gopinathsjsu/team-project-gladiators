package com.example.studentportal.common.ui

import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.ActionProvider
import android.view.ContextMenu
import android.view.MenuItem
import android.view.SubMenu
import android.view.View
import androidx.annotation.IdRes
import io.mockk.mockk

class MockMenuItem(@IdRes val menuId: Int): MenuItem {
    override fun getItemId(): Int = menuId

    override fun getGroupId(): Int = 0

    override fun getOrder(): Int = 0

    override fun setTitle(title: CharSequence?): MenuItem {
        return this
    }

    override fun setTitle(title: Int): MenuItem {
        return this
    }

    override fun getTitle(): CharSequence {
        return ""
    }

    override fun setTitleCondensed(title: CharSequence?): MenuItem {
        return this
    }

    override fun getTitleCondensed(): CharSequence? {
        return ""
    }

    override fun setIcon(icon: Drawable?): MenuItem {
        return this
    }

    override fun setIcon(iconRes: Int): MenuItem {
        return this
    }

    override fun getIcon(): Drawable? {
        return null
    }

    override fun setIntent(intent: Intent?): MenuItem {
        return this
    }

    override fun getIntent(): Intent? {
        return null
    }

    override fun setShortcut(numericChar: Char, alphaChar: Char): MenuItem {
        return this
    }

    override fun setNumericShortcut(numericChar: Char): MenuItem {
        return this
    }

    override fun getNumericShortcut(): Char {
        return 'a'
    }

    override fun setAlphabeticShortcut(alphaChar: Char): MenuItem {
        return this
    }

    override fun getAlphabeticShortcut(): Char {
        return 'a'
    }

    override fun setCheckable(checkable: Boolean): MenuItem {
        return this
    }

    override fun isCheckable(): Boolean {
        return false
    }

    override fun setChecked(checked: Boolean): MenuItem {
        return this
    }

    override fun isChecked(): Boolean {
        return false
    }

    override fun setVisible(visible: Boolean): MenuItem {
        return this
    }

    override fun isVisible(): Boolean {
        return false
    }

    override fun setEnabled(enabled: Boolean): MenuItem {
        return this
    }

    override fun isEnabled(): Boolean {
        return false
    }

    override fun hasSubMenu(): Boolean {
        return false
    }

    override fun getSubMenu(): SubMenu? {
        return mockk(relaxed = true)
    }

    override fun setOnMenuItemClickListener(menuItemClickListener: MenuItem.OnMenuItemClickListener?): MenuItem {
        return this
    }

    override fun getMenuInfo(): ContextMenu.ContextMenuInfo? {
        return mockk(relaxed = true)
    }

    override fun setShowAsAction(actionEnum: Int) = Unit

    override fun setShowAsActionFlags(actionEnum: Int): MenuItem {
        return this
    }

    override fun setActionView(view: View?): MenuItem {
        return this
    }

    override fun setActionView(resId: Int): MenuItem {
        return this
    }

    override fun getActionView(): View? {
        return null
    }

    override fun setActionProvider(actionProvider: ActionProvider?): MenuItem {
        return this
    }

    override fun getActionProvider(): ActionProvider? {
        return null
    }

    override fun expandActionView(): Boolean {
        return false
    }

    override fun collapseActionView(): Boolean {
        return false
    }

    override fun isActionViewExpanded(): Boolean {
        return false
    }

    override fun setOnActionExpandListener(listener: MenuItem.OnActionExpandListener?): MenuItem {
        return this
    }

}