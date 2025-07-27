package dev.reprator.movies.features.home.data.repository.remote.model.tv

import dev.reprator.movies.features.home.domain.models.HomeEpisodeOverView
import dev.reprator.movies.util.api.Mapper
import me.tatarka.inject.annotations.Inject

@Inject
class TvMapper : Mapper<ResponseModelTv, HomeEpisodeOverView> {

    override suspend fun map(from: ResponseModelTv): HomeEpisodeOverView {
        return HomeEpisodeOverView(
            id = from.id.orEmpty(),
            typeId = from.tvId.orEmpty(),
            name = "${from.tvTitle.orEmpty()} (${from.tvYear.orEmpty()})",
            description = from.tvStory.orEmpty(),
            ratings = from.tvRatings.orEmpty(),
            posterImage = from.poster.orEmpty(),
            language = from.tvlang.orEmpty(),
            categoriesList = buildList {
                if (true == from.tvCategory?.trim()?.isNotEmpty()) {
                    add(from.tvCategory)
                }

                if (true == from.tvlang?.trim()?.isNotEmpty()) {
                    add(from.tvlang)
                }

                if (true == from.tvGenre?.trim()?.isNotEmpty()) {
                    from.tvGenre.split(",").map {
                        add(it)
                    }
                }
            })
    }
}