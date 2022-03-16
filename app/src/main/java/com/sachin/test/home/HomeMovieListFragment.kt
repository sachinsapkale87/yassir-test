package com.sachin.test.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.sachin.test.databinding.FragmentMovieListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeMovieListFragment : Fragment() {
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentMovieListBinding.inflate(layoutInflater)
    }
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMovieListUsingStaeFlow()
        lifecycleScope.launchWhenStarted {
            viewModel.pageUiStateFlow.collectLatest {
                when(it){
                    is HomeViewModel.PageAPiuiState.Loading ->{
                        binding.loaderScreen.loaderView.visibility = View.VISIBLE
                    }

                    is HomeViewModel.PageAPiuiState.Success ->{
                        binding.loaderScreen.loaderView.visibility = View.GONE
                        val adapter = HomeAdapter(ImageClickListener,it.list)
                        binding.recyclerViewContainer.adapter = adapter
                    }

                    is HomeViewModel.PageAPiuiState.Error ->{
                        binding.loaderScreen.progressView.visibility = View.GONE
                        binding.loaderScreen.progressText.text = it.msg
                    }
                }
            }
        }
    }
}

object ImageClickListener : HomeAdapter.ListClickListener {
    override fun onImageClick(id: Int,v:View) {
        val action = HomeMovieListFragmentDirections.actionListToDetail(id)
        v.findNavController().navigate(action)
    }

}