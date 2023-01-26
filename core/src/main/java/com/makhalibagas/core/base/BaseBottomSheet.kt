package com.makhalibagas.core.base

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.makhalibagas.core.R

abstract class BaseBottomSheet<V : ViewBinding> : BottomSheetDialogFragment() {
    var bottomSheetBehavior: BottomSheetBehavior<*>? = null

    var binding: V? = null

    abstract fun buildBinding(): V

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        binding = buildBinding()
        dialog.setContentView(binding!!.root)
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        bottomSheetBehavior = BottomSheetBehavior.from(binding!!.root.parent as View)
        initView()
        initListener()
        initObserver()
        return dialog
    }

    override fun onStart() {
        super.onStart()
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
    }

    abstract fun initView()

    abstract fun initListener()

    abstract fun initObserver()

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}