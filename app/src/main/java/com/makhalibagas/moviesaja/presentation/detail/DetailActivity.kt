package com.makhalibagas.moviesaja.presentation.detail

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import coil.load
import com.makhalibagas.moviesaja.databinding.ActivityDetailBinding
import com.makhalibagas.core.adapter.ReviewAdapter
import com.makhalibagas.core.adapter.VideoAdapter
import com.makhalibagas.core.domain.model.Movie
import com.makhalibagas.core.state.UiStateWrapper
import com.makhalibagas.core.utils.collectLifecycleFlow
import com.makhalibagas.core.utils.movies_aja
import com.makhalibagas.core.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityDetailBinding::inflate)
    private val viewModel by viewModels<DetailViewModel>()
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var videoAdapter: VideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        initListener()
        initObserver()
    }

    private fun initView() {
        val data = intent.getParcelableExtra<Movie>(movies_aja)
        binding.apply {
            imgMovie.load(data?.backdropPath)
            tvTitle.text = data?.originalTitle
            tvDesc.text = data?.overview
            tvVote.text = data?.voteAverage.toString()
            reviewAdapter = ReviewAdapter()
            rvReview.adapter = reviewAdapter
            videoAdapter = VideoAdapter()
            rvVideo.adapter = videoAdapter
        }
    }

    private fun initListener() {
        videoAdapter.onClick = {
            playTrailer(this, it.key)
        }
    }

    private fun initObserver() {
        val data = intent.getParcelableExtra<Movie>(movies_aja)
        viewModel.setMovieId(data?.id.toString())

        viewModel.getMovieReview()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.listReviewState.collectLatest { result ->
                    reviewAdapter.submitData(lifecycle, PagingData.empty())
                    result?.let { reviewAdapter.submitData(it) }
                }
            }
        }

        collectLifecycleFlow(reviewAdapter.loadStateFlow) { loadStates ->
            when (val refreshedLoadState = loadStates.refresh) {
                is LoadState.NotLoading -> {}
                is LoadState.Loading -> {}
                is LoadState.Error -> {
                    Toast.makeText(
                        this,
                        refreshedLoadState.error.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    reviewAdapter.retry()
                }
            }
        }

        viewModel.getMovieVideo()
        collectLifecycleFlow(viewModel.videoListState) { state ->
            when (state) {
                is UiStateWrapper.Loading -> {}
                is UiStateWrapper.Success -> videoAdapter.setData(state.data)
                is UiStateWrapper.Error -> Toast.makeText(this, state.msg, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun playTrailer(context: Context, videoId: String) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$videoId"))
        val webIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=$videoId"))
        try {
            context.startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            context.startActivity(webIntent)
        }
    }
}