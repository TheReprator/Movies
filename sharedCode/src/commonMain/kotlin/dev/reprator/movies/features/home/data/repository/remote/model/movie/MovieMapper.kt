package dev.reprator.movies.features.home.data.repository.remote.model.movie

import dev.reprator.movies.features.home.domain.models.MovieGenreItem
import dev.reprator.movies.features.home.domain.models.HomeMovieDisplayableItem
import dev.reprator.movies.util.api.Mapper
import me.tatarka.inject.annotations.Inject

@Inject
class MovieMapper : Mapper<ResponseModelMovie, HomeMovieDisplayableItem> {

    override suspend fun map(from: ResponseModelMovie): HomeMovieDisplayableItem {
        return HomeMovieDisplayableItem(
            id = from.id.orEmpty(),
            typeId = from.movieID.orEmpty(),
            name = "${from.movieTitle.orEmpty()} (${from.movieYear.orEmpty()})",
            description = from.movieStory.orEmpty(),
            ratings = from.movieRatings.orEmpty(),
            posterImage = from.poster.orEmpty(),
            language = from.movielang.orEmpty(),
            watchLink = from.movieWatchLink.orEmpty(),
            movieSize = from.movieSize.orEmpty(),
            movieRuntime = from.movieRuntime.orEmpty(),
            castAndCrew = from.movieActors.orEmpty(),
            categoriesList = buildList {
                add("Movie")

                if (true == from.movieCategory?.trim()?.isNotEmpty()) {
                    add(from.movieCategory)
                }

                if (true == from.movielang?.trim()?.isNotEmpty()) {
                    add(from.movielang)
                }

                if (true == from.movieQuality?.trim()?.isNotEmpty()) {
                    add(from.movieQuality)
                }

                if (true == from.movieGenre?.trim()?.isNotEmpty()) {
                    from.movieGenre.split(",").map {
                        add(it)
                    }
                }
            })
    }


    fun mapGenreToModal(input: List<ResponseModelMovieGenre>?): List<MovieGenreItem> {
        return input?.map {
            MovieGenreItem(it.id.orEmpty(), it.name.orEmpty())
        } ?: emptyList()
    }
}