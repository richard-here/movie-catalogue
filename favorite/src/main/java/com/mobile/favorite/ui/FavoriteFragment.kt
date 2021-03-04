package com.mobile.favorite.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mobile.favorite.R
import com.mobile.favorite.databinding.FragFavoriteBinding
import com.mobile.favorite.di.viewModelModule
import com.mobile.favorite.viewmodel.FavoriteViewModel
import com.mobile.favorite.viewpager.FavoriteViewPagerAdapter
import com.mobile.moviecatalogue.ui.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import com.mobile.res.R as ResR

/**
 * Karena alasan yang belum diketahui, sepertinya binding.VIEWAPAPUN tidak dapat diakses
 * saat run-time sehingga pada module dynamic feature ini, semua reference View
 * di-refer menggunakan findViewById
 */
class FavoriteFragment : BaseFragment() {
    private var _binding: FragFavoriteBinding? = null
    private val binding get() = _binding as FragFavoriteBinding
    private val viewmodel: FavoriteViewModel by viewModel()
    override var bottomNavigationViewVisibility = View.VISIBLE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragFavoriteBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        loadKoinModules(viewModelModule)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unloadKoinModules(viewModelModule)
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        observeNavigationEvent()
    }

    private fun setupViewPager() {
        val viewPagerAdapter =
            FavoriteViewPagerAdapter(
                childFragmentManager,
                viewLifecycleOwner.lifecycle
            )
        val viewPager = binding.root.findViewById<ViewPager2>(R.id.frag_favorite_view_pager)
        val tabLayout = binding.root.findViewById<TabLayout>(R.id.frag_favorite_tab_layout)
        viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(ResR.string.movie_toolbar_text)
                1 -> tab.text = getString(ResR.string.show_toolbar_text)
            }
        }.attach()
    }

    private fun observeNavigationEvent() {
        viewmodel.navigateToDetails.observe(viewLifecycleOwner, {
            if (it.first != -1) {
                val action =
                    if (it.second == ListFragment.TYPE_MOVIE) FavoriteFragmentDirections.actionFavoriteFragmentToMovieDetailsFragment2(
                        it.first
                    )
                    else FavoriteFragmentDirections.actionFavoriteFragmentToShowDetailsFragment2(it.first)
                findNavController().navigate(action)
                viewmodel.finishNavigatingToDetails()
            }
        })
    }
}