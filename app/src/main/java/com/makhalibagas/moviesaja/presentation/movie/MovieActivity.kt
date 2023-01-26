package com.makhalibagas.moviesaja.presentation.movie

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.makhalibagas.moviesaja.databinding.ActivityMovieBinding
import com.makhalibagas.core.adapter.MovieAdapter
import com.makhalibagas.moviesaja.presentation.detail.DetailActivity
import com.makhalibagas.core.utils.collectLifecycleFlow
import com.makhalibagas.core.utils.movies_aja
import com.makhalibagas.core.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMovieBinding::inflate)
    private val viewModel by viewModels<MovieViewModel>()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        initListener()
        initObserver()
    }

    private fun initView() {
        movieAdapter = MovieAdapter()
        binding.rvMovie.adapter = movieAdapter
    }

    private fun initListener() {
        movieAdapter.onDetailClick = {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(movies_aja, it)
            startActivity(intent)
        }

        binding.imgFilter.setOnClickListener {
            val sheet = FilterBottomSheet()
            if (!sheet.isAdded) sheet.show(supportFragmentManager, sheet.tag)
        }
    }

    private fun initObserver() {
        viewModel.getMovie()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.listMovieState.collectLatest { result ->
                    movieAdapter.submitData(lifecycle, PagingData.empty())
                    result?.let { movieAdapter.submitData(it) }
                }
            }
        }

        collectLifecycleFlow(movieAdapter.loadStateFlow) { loadStates ->
            when (val refreshedLoadState = loadStates.refresh) {
                is LoadState.NotLoading -> {}
                is LoadState.Loading -> {}
                is LoadState.Error -> {
                    Toast.makeText(
                        this,
                        refreshedLoadState.error.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    movieAdapter.retry()
                }
            }
        }
    }

}
