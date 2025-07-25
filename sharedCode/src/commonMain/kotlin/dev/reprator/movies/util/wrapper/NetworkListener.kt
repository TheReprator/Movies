package dev.reprator.movies.util.wrapper

import kotlinx.coroutines.flow.Flow

interface NetworkListener {
    fun monitor(): Flow<Boolean>
}