package dev.reprator.movies.features.home.presentation

import dev.reprator.movies.features.home.domain.models.HomeCategoryType
import dev.reprator.movies.features.home.domain.models.HomeModel
import dev.reprator.movies.features.home.domain.models.ItemTypeError
import dev.reprator.movies.features.home.domain.models.ItemTypeLoader
import dev.reprator.movies.features.home.domain.models.ResultStatus
import dev.reprator.movies.util.base.mvi.Reducer
import io.github.aakira.napier.Napier
import me.tatarka.inject.annotations.Inject

@Inject
class HomeScreenReducer : Reducer<HomeState, HomeAction, HomeEffect> {

    override fun reduce(
        previousState: HomeState,
        action: HomeAction
    ): Pair<HomeState, HomeEffect?> {
        return when (action) {

            is HomeAction.UpdateHomeList -> {
                setUpInitialSetup(previousState) to null
            }

            is HomeAction.UpdateHomeGenreList -> {
                updateGenre(previousState, action) to null
            }

            is HomeAction.UpdateHomeTvList -> {
                updateTv(previousState, action) to null
            }

            is HomeAction.UpdateHomeMovieList -> {
                updateMovie(previousState, action) to null
            }

            is HomeAction.UpdateHomeMovieError -> {
                updateErrorMovie(previousState, action) to null
            }

            is HomeAction.UpdateHomeTvError -> {
                updateErrorTv(previousState, action) to null
            }

            is HomeAction.RetryMovie -> {
                retryMovie(previousState, action) to null
            }
            is HomeAction.RetryMovieGenre -> {
                retryMovieGenre(previousState, action) to null
            }
            is HomeAction.RetryTv -> {
                retryTV(previousState, action) to null
            }
        }
    }

    private fun setUpInitialSetup(previousState: HomeState): HomeState {
        Napier.d { "setUpInitialSetup" }

        val movieGenre= HomeModel.ModelGenre( emptyList())
        val movieModel = HomeModel.ModelMovie("Popular this Week", emptyList())
        val tvModel = HomeModel.ModelTvSeries("Recent TV Series", emptyList())

        val l = listOf(movieGenre, movieModel, tvModel)
        Napier.d { "setUpInitialSetup ${l}" }
        return previousState.copy(itemList = l)
    }

    private fun updateGenre(previousState: HomeState, action: HomeAction.UpdateHomeGenreList): HomeState {
        Napier.d { "updateGenre" }

        val itemList = previousState.itemList.toMutableList()

        val index = itemList.indexOfFirst {
            it.id == HomeCategoryType.HOME_CATEGORY_GENRE
        }

        if(-1 == index) {
            return previousState
        }

        val genreItem = itemList[index] as HomeModel.ModelGenre

        val genreList = genreItem.genreList.toMutableList()
        genreList.addAll(action.itemList)

        val updatedGenreItem = genreItem.copy(
            genreList = genreList,
            resultStatus = ResultStatus.RESULT_STATUS_RESULT)

        itemList[index] = updatedGenreItem

        return previousState.copy(
            itemList = itemList
        )
    }

    private fun updateTv(previousState: HomeState, action: HomeAction.UpdateHomeTvList): HomeState {
        Napier.d { "updateTv" }

        val itemList = previousState.itemList.toMutableList()

        val index = itemList.indexOfFirst {
            it.id == HomeCategoryType.HOME_CATEGORY_TV
        }

        if(-1 == index) {
            return previousState
        }

        val tvItem = itemList[index] as HomeModel.ModelTvSeries
        val updatedTvItem = if(tvItem.resultStatus == ResultStatus.RESULT_STATUS_LOADER) {
            if(action.itemList.isEmpty()) {
                tvItem.copy(resultStatus = ResultStatus.RESULT_STATUS_EMPTY)
            } else {
                tvItem.copy(
                    resultStatus = ResultStatus.RESULT_STATUS_RESULT,
                    itemList = action.itemList)
            }
        } else {
            val tvList = tvItem.itemList.toMutableList()
            val indexItem = tvList.indexOfFirst {
                it.itemType == ResultStatus.RESULT_STATUS_LOADER
            }
            if(-1 == indexItem) {
                return previousState
            }
            tvList.removeAt(indexItem)
            tvList.addAll(action.itemList)

            tvItem.copy(itemList = tvList)
        }

        itemList[index] = updatedTvItem

        return previousState.copy(
            itemList = itemList
        )
    }

    private fun updateMovie(previousState: HomeState, action: HomeAction.UpdateHomeMovieList): HomeState {
        Napier.d { "updateMovie" }

        val itemList = previousState.itemList.toMutableList()

        val index = itemList.indexOfFirst {
            it.id == HomeCategoryType.HOME_CATEGORY_MOVIE
        }

        if(-1 == index) {
            return previousState
        }

        val movieItem = itemList[index] as HomeModel.ModelMovie

        val updatedMovieItem = if(movieItem.resultStatus == ResultStatus.RESULT_STATUS_LOADER) {
            if(action.itemList.isEmpty()){
                movieItem.copy(resultStatus = ResultStatus.RESULT_STATUS_EMPTY)
            } else {
                movieItem.copy(
                    resultStatus = ResultStatus.RESULT_STATUS_RESULT,
                    itemList = action.itemList)
            }
        } else {
            val movieList = movieItem.itemList.toMutableList()
            val indexItem = movieList.indexOfFirst {
                it.itemType == ResultStatus.RESULT_STATUS_LOADER
            }
            if(-1 == indexItem) {
                return previousState
            }
            movieList.removeAt(indexItem)
            movieList.addAll(action.itemList)
            movieItem.copy(itemList = movieList)
        }

        itemList[index] = updatedMovieItem

        return previousState.copy(
            itemList = itemList
        )
    }

    private fun updateErrorMovie(previousState: HomeState, action: HomeAction.UpdateHomeMovieError): HomeState {
        Napier.d { "updateMovieError" }

        val itemList = previousState.itemList.toMutableList()

        val index = itemList.indexOfFirst {
            it.id == HomeCategoryType.HOME_CATEGORY_MOVIE
        }

        if(-1 == index) {
            return previousState
        }

        val movieItem = itemList[index] as HomeModel.ModelMovie
        val updatedMovieItem = if(movieItem.resultStatus == ResultStatus.RESULT_STATUS_LOADER) {
            movieItem.copy(resultStatus = ResultStatus.RESULT_STATUS_ERROR)
        } else {
            val movieList = movieItem.itemList.toMutableList()
            val indexItem = movieList.indexOfFirst {
                it.itemType == ResultStatus.RESULT_STATUS_LOADER
            }
            if(-1 == indexItem) {
                return previousState
            }
            movieList.removeAt(indexItem)
            movieList.add(ItemTypeError())
            movieItem.copy(itemList = movieList)
        }

        itemList[index] = updatedMovieItem

        return previousState.copy(
            itemList = itemList
        )
    }

    private fun updateErrorTv(previousState: HomeState, action: HomeAction.UpdateHomeTvError): HomeState {
        Napier.d { "updateMovieError" }

        val itemList = previousState.itemList.toMutableList()

        val index = itemList.indexOfFirst {
            it.id == HomeCategoryType.HOME_CATEGORY_TV
        }

        if(-1 == index) {
            return previousState
        }

        val tvItem = itemList[index] as HomeModel.ModelTvSeries
        val updatedTvItem = if(tvItem.resultStatus == ResultStatus.RESULT_STATUS_LOADER) {
            tvItem.copy(resultStatus = ResultStatus.RESULT_STATUS_ERROR)
        } else {
            val tvList = tvItem.itemList.toMutableList()
            val indexItem = tvList.indexOfFirst {
                it.itemType == ResultStatus.RESULT_STATUS_LOADER
            }
            if(-1 == indexItem) {
                return previousState
            }
            tvList.removeAt(indexItem)
            tvList.add(ItemTypeError())

            tvItem.copy(itemList = tvList)
        }

        itemList[index] = updatedTvItem

        return previousState.copy(
            itemList = itemList
        )
    }

    private fun retryMovie(previousState: HomeState, action: HomeAction.RetryMovie): HomeState {
        Napier.d { "retryMovie" }

        val itemList = previousState.itemList.toMutableList()

        val index = itemList.indexOfFirst {
            it.id == HomeCategoryType.HOME_CATEGORY_MOVIE
        }

        if(-1 == index) {
            return previousState
        }

        val movieItem = itemList[index] as HomeModel.ModelMovie
        val updatedTvItem = if(movieItem.resultStatus == ResultStatus.RESULT_STATUS_ERROR) {
            movieItem.copy(resultStatus = ResultStatus.RESULT_STATUS_LOADER)
        } else {
            val movieList = movieItem.itemList.toMutableList()
            val indexItem = movieList.indexOfFirst {
                it.itemType == ResultStatus.RESULT_STATUS_ERROR
            }
            if(-1 == indexItem) {
                return previousState
            }
            movieList.removeAt(indexItem)

            movieList.add(ItemTypeLoader())

            movieItem.copy(itemList = movieList)
        }

        itemList[index] = updatedTvItem

        return previousState.copy(
            itemList = itemList
        )
    }

    private fun retryTV(previousState: HomeState, action: HomeAction.RetryTv): HomeState {
        Napier.d { "retryTV" }

        val itemList = previousState.itemList.toMutableList()

        val index = itemList.indexOfFirst {
            it.id == HomeCategoryType.HOME_CATEGORY_TV
        }

        if(-1 == index) {
            return previousState
        }

        val tvItem = itemList[index] as HomeModel.ModelTvSeries
        val updatedTvItem = if(tvItem.resultStatus == ResultStatus.RESULT_STATUS_ERROR) {
            tvItem.copy(resultStatus = ResultStatus.RESULT_STATUS_LOADER)
        } else {
            val tvList = tvItem.itemList.toMutableList()
            val indexItem = tvList.indexOfFirst {
                it.itemType == ResultStatus.RESULT_STATUS_ERROR
            }
            if(-1 == indexItem) {
                return previousState
            }
            tvList.removeAt(indexItem)

            tvList.add(ItemTypeLoader())

            tvItem.copy(itemList = tvList)
        }

        itemList[index] = updatedTvItem

        return previousState.copy(
            itemList = itemList
        )
    }

    private fun retryMovieGenre(previousState: HomeState, action: HomeAction.RetryMovieGenre): HomeState {
        Napier.d { "retryMovieGenre" }

        val itemList = previousState.itemList.toMutableList()

        val index = itemList.indexOfFirst {
            it.id == HomeCategoryType.HOME_CATEGORY_GENRE
        }

        if(-1 == index) {
            return previousState
        }

        val genreItem = itemList[index] as HomeModel.ModelGenre
        val updatedGenreItem = genreItem.copy(resultStatus = ResultStatus.RESULT_STATUS_LOADER)

        itemList[index] = updatedGenreItem

        return previousState.copy(
            itemList = itemList
        )
    }
}
