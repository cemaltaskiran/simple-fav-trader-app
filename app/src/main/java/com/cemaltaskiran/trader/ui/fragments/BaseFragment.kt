package com.cemaltaskiran.trader.ui.fragments

import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.cemaltaskiran.trader.R

open class BaseFragment : Fragment() {

    private fun getResourceId(
        pVariableName: String?,
        pResourceName: String?
    ): Int {
        return try {
            resources.getIdentifier(pVariableName, pResourceName, activity?.packageName)
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        }
    }

    private fun getResourceStringOrError(pVariableName: String?): String {
        return try {
            val stringResourceId = getResourceId(pVariableName, "string")
            if (stringResourceId != -1) {
                getString(stringResourceId)
            } else {
                getString(R.string.error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            getString(R.string.error)
        }
    }

    protected fun bindMessage(
        recyclerView: RecyclerView,
        textView: TextView,
        messageResourceName: String?
    ) {
        recyclerView.visibility = View.GONE
        textView.visibility = View.VISIBLE
        textView.text = getResourceStringOrError(messageResourceName)
    }

}