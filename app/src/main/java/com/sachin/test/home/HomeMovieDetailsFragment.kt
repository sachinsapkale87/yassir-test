package com.sachin.test.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.sachin.test.databinding.FragmentMovieBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class HomeMovieDetailsFragment : Fragment(){
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentMovieBinding.inflate(layoutInflater)
    }
    private val viewModel: HomeViewModel by viewModels()
    private val args: HomeMovieDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMovieDetailsUsingStateFlow(args.id)
        lifecycleScope.launchWhenStarted {
            viewModel.pageUiStateFlow.collectLatest {
                when(it){
                    is HomeViewModel.PageAPiuiState.Loading ->{
                        binding.loaderScreen.loaderView.visibility = View.VISIBLE
                        binding.loaderScreen.progressText.text = "Loading movie details"
                    }

                    is HomeViewModel.PageAPiuiState.SuccessImage ->{
                        binding.loaderScreen.loaderView.visibility = View.GONE
                        val url = "https://image.tmdb.org/t/p/w500/@path".replace("@path",it.movieObj.posterPath)
                        Glide.with(requireContext())
                            .load(url)
                            .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                            .into(binding.itemImage)
                        binding.itemOverview.text = "Overview:\n" + it.movieObj.overview
                        binding.itemTitle.text = it.movieObj.title
                    }

                    is HomeViewModel.PageAPiuiState.Error ->{
                        binding.loaderScreen.progressView.visibility = View.GONE
                        binding.loaderScreen.progressText.text = it.msg
                    }
                }
            }
        }
        binding.backTv.setOnClickListener { activity?.onBackPressed() }
    }
}