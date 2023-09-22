package com.haibox.room

import android.content.DialogInterface
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.DialogFragment
import com.google.android.material.tabs.TabLayout.TabGravity
import com.haibox.room.data.entity.Employee
import com.haibox.room.databinding.DialogDataBinding


class DataDialog: DialogFragment(), View.OnClickListener, DialogInterface.OnKeyListener {
    companion object {
        const val TAG = "DataDialog"
    }
    private lateinit var binding: DialogDataBinding

    private var positiveButtonListener: View.OnClickListener? = null
    private var negativeButtonListener: View.OnClickListener? = null
    private var onDismissListener: DialogInterface.OnDismissListener? = null
    private var onCancelListener: DialogInterface.OnCancelListener? = null
    private var dialogCancelable = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogDataBinding.inflate(inflater, container, false)
        isCancelable = dialogCancelable
        dialog!!.setOnKeyListener(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (dialog?.window == null) return
        val params = dialog!!.window!!.attributes

        params.width = 100
        params.height = 50
        val displayMetrics = resources.displayMetrics
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            params.width = displayMetrics.heightPixels * 4 / 5
            params.height = displayMetrics.heightPixels
        } else if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            params.width = displayMetrics.widthPixels * 4 / 5
            params.height = displayMetrics.widthPixels
        }
        params.gravity = Gravity.CENTER
        dialog!!.window!!.attributes = params

        binding.btnOK.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v == binding.btnOK) {
            if (tryToInsert()) {
                dismiss()
                showTips("OK")
                if (positiveButtonListener != null) {
                    positiveButtonListener!!.onClick(v)
                }
            }
        } else if (v == binding.btnCancel) {
            dismiss()
            if (negativeButtonListener != null) {
                negativeButtonListener!!.onClick(v)
            }
        }
    }

    override fun onKey(dialog: DialogInterface?, keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (onCancelListener != null) {
                onCancelListener!!.onCancel(dialog)
                dismiss()
                return true
            }
        }
        return false
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (onDismissListener != null) {
            onDismissListener!!.onDismiss(dialog)
        }
    }

    fun setPositiveListener(listener: View.OnClickListener) {
        positiveButtonListener = listener
    }

    fun setNegativeListener(listener: View.OnClickListener) {
        negativeButtonListener = listener
    }

    private fun tryToInsert(): Boolean {
        val id = binding.etId.text.toString().trim()
        val username = binding.etUsername.text.toString().trim()
        val genderStr = binding.etGender.text.toString().trim()
        val ageStr = binding.etAge.text.toString().trim()
        val salaryStr = binding.etSalary.text.toString().trim()
        if (id.isEmpty()) {
            showTips("ID cannot be empty")
            return false
        }
        if (username.isEmpty()) {
            showTips("Username cannot be empty")
            return false
        }
        var gender = 0
        if (genderStr.isNotEmpty() && genderStr.isDigitsOnly()) {
            gender = genderStr.toInt()
        }

        var age = 0
        if (ageStr.isNotEmpty() && ageStr.isDigitsOnly()) {
            age = ageStr.toInt()
        }

        var salary = 0L
        if (salaryStr.isNotEmpty() && salaryStr.isDigitsOnly()) {
            salary = salaryStr.toLong()
        }
        val employee = Employee(id.toLong(), gender, username, age, salary)
        App.getApp().database.EmployeeDao().insert(employee)
        return true
    }

    private fun showTips(msg: String) {
        Toast.makeText(App.getApp(), msg, Toast.LENGTH_LONG).show()
    }
}