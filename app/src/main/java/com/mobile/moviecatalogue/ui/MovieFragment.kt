package com.mobile.moviecatalogue.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.core.presentation.adapter.listener.ItemClickListener
import com.mobile.core.presentation.adapter.paging.MoviePagedRVAdapter
import com.mobile.moviecatalogue.R
import com.mobile.moviecatalogue.databinding.FragMovieBinding
import com.mobile.moviecatalogue.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : BaseFragment() {
    private var _binding: FragMovieBinding? = null
    private val binding get() = _binding as FragMovieBinding
    private val viewmodel by viewModels<MovieViewModel>()
    override var bottomNavigationViewVisibility = View.VISIBLE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragMovieBinding.inflate(inflater, container, false)
        binding.viewmodel = viewmodel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()
        observeNavigationEvent()
        observeLoadingState()
    }

    private fun setupRV() {
        val clickListener = ItemClickListener {
            viewmodel.startNavigatingToDetails(it)
        }
        val spaceSeparator = DividerItemDecoration(context, LinearLayoutManager.VERTICAL).apply {
            val drawable = ResourcesCompat.getDrawable(
                resources,
                R.drawable.rv_space_separator, null
            ) ?: return@apply
            setDrawable(drawable)
        }

        with(binding.fragMovieListRv) {
            layoutManager = LinearLayoutManager(context)
            this.adapter = MoviePagedRVAdapter(R.layout.vh_movie, clickListener)
            addItemDecoration(spaceSeparator)
        }
    }

    private fun observeNavigationEvent() {
        viewmodel.navigateToDetails.observe(viewLifecycleOwner, Observer {
            if (it == -1) return@Observer
            val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailsFragment2(it)
            findNavController().navigate(action)
            viewmodel.finishNavigatingToDetails()
        })
    }

    private fun observeLoadingState() {
        val xOffset = resources.getDimensionPixelOffset(R.dimen.keyline_4)
        val yOffset = resources.getDimensionPixelOffset(R.dimen.toolbar_height)
        fun makeToast(text: String): Toast {
            return Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).apply {
                setGravity(
                    Gravity.TOP or Gravity.END,
                    xOffset,
                    yOffset
                )
            }
        }
        fun dismissToastAfterDelay(toast: Toast, time: Long = 750) {
            Handler(Looper.getMainLooper()).postDelayed({
                toast.cancel()
            }, time)
        }
        viewmodel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it == false) return@Observer
            val toast = makeToast(getString(R.string.toast_load_more_data_text)).apply { show() }
            dismissToastAfterDelay(toast)
        })

        viewmodel.errorLoading.observe(viewLifecycleOwner, Observer {
            if (it == false) return@Observer
            val toast = makeToast(getString(R.string.toast_error_loading_text)).apply { show() }
            dismissToastAfterDelay(toast)
        })
    }
}