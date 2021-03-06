package com.mobile.moviecatalogue.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.snackbar.Snackbar
import com.mobile.core.presentation.adapter.rv.GenreRVAdapter
import com.mobile.moviecatalogue.R
import com.mobile.moviecatalogue.databinding.FragShowDetailsBinding
import com.mobile.moviecatalogue.viewmodel.ShowDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowDetailsFragment : BaseFragment() {
    private var _binding: FragShowDetailsBinding? = null
    private val binding get() = _binding as FragShowDetailsBinding
    private val viewmodel by viewModels<ShowDetailsViewModel>()
    override var bottomNavigationViewVisibility = View.GONE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragShowDetailsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewmodel
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupGenreRV()
        setupFab()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.details_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.share_detail -> startShareIntent()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupGenreRV() {
        val flexboxLayoutManager = FlexboxLayoutManager(context).apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
            flexWrap = FlexWrap.WRAP
        }
        val spaceSeparator = DividerItemDecoration(context, LinearLayoutManager.VERTICAL).apply {
            val drawable = ResourcesCompat.getDrawable(
                resources,
                R.drawable.rv_space_separator_small, null
            ) ?: return@apply
            setDrawable(drawable)
        }
        with(binding.fragShowDetailsGenreRv) {
            layoutManager = flexboxLayoutManager
            adapter = GenreRVAdapter()
            addItemDecoration(spaceSeparator)
        }
    }

    private fun setupFab() {
        var snackbarString = ""
        viewmodel.isShowFavorited.observe(viewLifecycleOwner, {
            snackbarString =
                if (it) getString(R.string.unfavorited_snackbar_text) else getString(R.string.favorited_snackbar_text)
        })

        val clickListener =
            ShowFabClickListener {
                viewmodel.handleFavoriteFabClick(it)
                Snackbar.make(binding.root, snackbarString, Snackbar.LENGTH_SHORT)
                    .apply {
                        setAction(getString(R.string.snackbar_dismiss_text)) { this.dismiss() }
                        show()
                    }
            }
        binding.fabClickListener = clickListener
    }

    private fun startShareIntent() {
        val show = viewmodel.show.value ?: return
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                resources.getString(R.string.share_show_text, show.title, show.firstAired, show.summary)
            )
            type="text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}