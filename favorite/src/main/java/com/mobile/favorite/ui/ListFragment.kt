package com.mobile.favorite.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.mobile.core.presentation.adapter.listener.ItemClickListener
import com.mobile.core.presentation.adapter.paging.MoviePagedRVAdapter
import com.mobile.core.presentation.adapter.paging.ShowPagedRVAdapter
import com.mobile.favorite.R
import com.mobile.favorite.databinding.FragListBinding
import com.mobile.favorite.viewmodel.FavoriteViewModel
import com.mobile.favorite.viewmodel.FavoriteViewModel.Companion.SortConfig
import com.mobile.core.R as CoreR
import com.mobile.res.R as ResR

class ListFragment : Fragment() {
    private var _binding: FragListBinding? = null
    private val binding get() = _binding as FragListBinding
    private val viewmodel: FavoriteViewModel by lazy {
        ViewModelProvider(requireParentFragment()).get(FavoriteViewModel::class.java)
    }
    private var type: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragListBinding.inflate(inflater, container, false)
        type = this.arguments?.getString(ARG_TYPE_KEY)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSort()
        setupRV()
        populateRV()
    }

    private fun setupSort() {
        val setSortConfig =
            if (type == TYPE_MOVIE) viewmodel::setMovieSortConfig
            else viewmodel::setShowSortConfig
        val chipGroup = binding.root.findViewById<ChipGroup>(R.id.frag_sort_chip_group)
        chipGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.frag_chip_alphabetically_ascending -> setSortConfig.invoke(SortConfig.ALPHABETICALLY_ASCENDING)
                R.id.frag_chip_alphabetically_descending -> setSortConfig.invoke(SortConfig.ALPHABETICALLY_DESCENDING)
                R.id.frag_chip_chronologically_ascending -> setSortConfig.invoke(SortConfig.CHRONOLOGICALLY_ASCENDING)
                R.id.frag_chip_chronologically_descending -> setSortConfig.invoke(SortConfig.CHRONOLOGICALLY_DESCENDING)
            }
        }
    }

    private fun setupRV() {
        val clickListener = ItemClickListener {
            viewmodel.startNavigatingToDetails(it, type ?: TYPE_MOVIE)
        }
        val spaceSeparator = DividerItemDecoration(context, LinearLayoutManager.VERTICAL).apply {
            val drawable = ResourcesCompat.getDrawable(
                resources,
                ResR.drawable.rv_space_separator, null
            ) ?: return@apply
            setDrawable(drawable)
        }
        val listRv = binding.root.findViewById<RecyclerView>(R.id.frag_list_rv)
        with(listRv) {
            layoutManager = LinearLayoutManager(context)
            adapter =
                if (type == TYPE_MOVIE) MoviePagedRVAdapter(CoreR.layout.vh_movie, clickListener)
                else ShowPagedRVAdapter(CoreR.layout.vh_show, clickListener)
            addItemDecoration(spaceSeparator)
        }
    }

    private fun populateRV() {
        val listEmptyState: LiveData<Boolean>?
        val setEmptyState: Function<Unit>?
        val listRv = binding.root.findViewById<RecyclerView>(R.id.frag_list_rv)
        if (type == TYPE_MOVIE) {
            listEmptyState = viewmodel.isMovieEmpty
            setEmptyState = viewmodel::setMovieEmptyState
            viewmodel.movieList.observe(viewLifecycleOwner, {
                setEmptyState.invoke(it.size == 0)
                (listRv.adapter as MoviePagedRVAdapter).submitList(it)
            })
        } else {
            listEmptyState = viewmodel.isShowEmpty
            setEmptyState = viewmodel::setShowEmptyState
            viewmodel.showList.observe(viewLifecycleOwner, {
                setEmptyState.invoke(it.size == 0)
                (listRv.adapter as ShowPagedRVAdapter).submitList(it)
            })
        }

        binding.isListEmpty = listEmptyState
    }

    companion object {
        const val TYPE_MOVIE = "movie"
        const val TYPE_SHOW = "show"
        const val ARG_TYPE_KEY = "type"
        fun newInstance(type: String): ListFragment {
            val args = Bundle()
            args.putString(ARG_TYPE_KEY, type)
            val fragment = ListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}