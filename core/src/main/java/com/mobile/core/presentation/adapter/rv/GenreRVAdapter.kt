package com.mobile.core.presentation.adapter.rv

import com.mobile.core.R
import com.mobile.core.presentation.adapter.diff.GenreDiffCallback
import com.mobile.core.domain.model.Genre

class GenreRVAdapter : DataBindingRVAdapter<Genre>(R.layout.vh_genre, GenreDiffCallback(), null)