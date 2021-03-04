package com.mobile.core.utils

import com.mobile.core.data.model.entity.MovieEntity
import com.mobile.core.data.model.entity.ShowEntity
import com.mobile.core.data.model.network.*
import com.mobile.core.data.source.remote.service.RetrofitService
import com.mobile.core.domain.model.Genre
import com.mobile.core.domain.model.movie.MovieOverview
import com.mobile.core.domain.model.show.ShowOverview
import com.mobile.core.domain.model.show.Show
import com.mobile.core.domain.model.movie.Movie

fun Movie.toDataModel() =
    MovieEntity(
        backdropUrl = this.backdropUrl,
        genres = this.genres,
        id = this.id,
        title = this.title,
        summary = this.summary,
        coverUrl = this.coverUrl,
        year = this.year,
        runtime = this.runtime,
        rating = this.rating,
        voteCount = this.voteCount
    )

fun Show.toDataModel() =
    ShowEntity(
        backdropUrl = this.backdropUrl,
        firstAired = this.firstAired,
        returningSeries = this.returningSeries,
        episodes = this.episodes,
        seasons = this.seasons,
        genres = this.genres,
        id = this.id,
        title = this.title,
        summary = this.summary,
        coverUrl = this.coverUrl,
        runtime = this.runtime,
        rating = this.rating,
        voteCount = this.voteCount
    )

fun ShowEntity.toDomainModel() =
    ShowOverview(
        firstAired = this.firstAired,
        id = this.id,
        title = this.title,
        summary = this.summary,
        coverUrl = this.coverUrl,
        rating = this.rating,
        genreIds = this.genres.map { it.id }
    )


fun MovieEntity.toDomainModel() =
    MovieOverview(
        id = this.id,
        title = this.title,
        summary = this.summary,
        coverUrl = this.coverUrl,
        year = this.year,
        rating = this.rating,
        genreIds = this.genres.map { it.id }
    )


fun NetworkGenre.toDomainModel() = Genre(
    id = this.id,
    name = this.name ?: "-"
)

fun NetworkMovieDetailsResponse.toDomainModel() =
    Movie(
        backdropUrl = "${RetrofitService.IMAGE_BASE_URL}/original${this.backdropPath}",
        genres = this.genres?.map { it.toDomainModel() } ?: listOf(),
        id = this.id,
        title = this.title ?: "-",
        summary = this.overview ?: "-",
        coverUrl = "${RetrofitService.IMAGE_BASE_URL}/original${this.posterPath}",
        year =
        if (this.releaseDate?.length != null
            && this.releaseDate.length >= 4
        ) this.releaseDate.slice(0..3).toInt()
        else 0,
        runtime = this.runtime ?: 0,
        rating = this.voteAverage ?: 0f,
        voteCount = this.voteCount ?: 0
    )


fun NetworkMovieOverview.toDomainModel() =
    MovieOverview(
        id = this.id,
        title = this.originalTitle ?: "-",
        rating = this.voteAverage ?: 0f,
        year =
        if (this.releaseDate?.length != null
            && this.releaseDate.length >= 4) this.releaseDate.slice(0..3).toInt()
        else 0,
        summary = this.overview ?: "-",
        coverUrl =
        "${RetrofitService.IMAGE_BASE_URL}/original${this.posterPath}",
        genreIds = this.genreIds ?: listOf()
    )

fun NetworkShowDetailsResponse.toDomainModel() =
    Show(
        backdropUrl = "${RetrofitService.IMAGE_BASE_URL}/original${this.backdropPath}",
        runtime = this.runtime?.average()?.toInt() ?: 0,
        firstAired = this.airDate ?: "-",
        genres = this.genres?.map { it.toDomainModel() } ?: listOf(),
        id = this.id,
        returningSeries = this.returningSeries == "Returning Series",
        title = this.title ?: "-",
        episodes = this.episodes ?: 0,
        seasons = this.seasons ?: 0,
        summary = this.overview ?: "-",
        coverUrl = "${RetrofitService.IMAGE_BASE_URL}/original${this.posterPath}",
        rating = this.voteAverage ?: 0f,
        voteCount = this.voteCount ?: 0
    )


fun NetworkShowOverview.toDomainModel() =
    ShowOverview(
        id = this.id,
        title = this.name ?: "-",
        rating = this.voteAverage ?: 0f,
        firstAired = this.firstAirDate ?: "-",
        summary = this.overview ?: "-",
        coverUrl =
        "${RetrofitService.IMAGE_BASE_URL}/original${this.posterPath}",
        genreIds = this.genreIds ?: listOf()
    )