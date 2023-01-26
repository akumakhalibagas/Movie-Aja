package com.makhalibagas.moviesaja.presentation.movie

import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.makhalibagas.moviesaja.base.BaseBottomSheet
import com.makhalibagas.moviesaja.databinding.BottomSheetFilterBinding
import com.makhalibagas.moviesaja.presentation.adapter.FilterAdapter
import com.makhalibagas.moviesaja.state.UiStateWrapper
import com.makhalibagas.moviesaja.utils.collectLifecycleFlow

class FilterBottomSheet : BaseBottomSheet<BottomSheetFilterBinding>() {

    private val viewModel by activityViewModels<MovieViewModel>()
    private lateinit var adapter: FilterAdapter

    override fun buildBinding(): BottomSheetFilterBinding =
        BottomSheetFilterBinding.inflate(
            LayoutInflater.from(requireContext()), null, false
        )

    override fun initView() {
        adapter = FilterAdapter()
        binding?.rvGenre?.adapter = adapter
    }

    override fun initListener() {
        adapter.onClick = {
            dismiss()
            viewModel.setGenre(it.id.toString())
        }
    }

    override fun initObserver() {
        viewModel.getMovieGenre()

        collectLifecycleFlow(viewModel.genreListState) { state ->
            when (state) {
                is UiStateWrapper.Loading -> {}
                is UiStateWrapper.Success -> adapter.setData(state.data)
                is UiStateWrapper.Error -> Toast.makeText(
                    requireContext(),
                    state.msg,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
