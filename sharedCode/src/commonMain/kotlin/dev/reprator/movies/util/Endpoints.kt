package dev.reprator.movies.util

const val BASE_URL = "103.134.58.242"
private const val PICTURE_URL = "/Admin/main/images/%1s/poster/%2s"
private const val PICTURE_URL_TV = "/Admin/main/TVseries/%1s/poster/%2s"

fun getImageFromServerMovies(movieId: String, imageName: String): String {
    return PICTURE_URL.format(movieId, imageName)
}

fun getImageFromServerTv(tvSeriesId: String, imageName: String): String {
    return PICTURE_URL_TV.format(tvSeriesId, imageName)
}
