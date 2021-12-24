package co.id.fikridzakwan.somethingbig.utils

import co.id.fikridzakwan.somethingbig.BuildConfig
import co.id.fikridzakwan.somethingbig.data.source.response.DetailResponse
import co.id.fikridzakwan.somethingbig.data.source.response.ResultsItem
import co.id.fikridzakwan.somethingbig.domain.model.Detail
import co.id.fikridzakwan.somethingbig.domain.model.Movie
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object DataMapper {

    fun ResultsItem.mapToMovie(): Movie =
        Movie(
            id = id ?: 0,
            title = title ?: "",
            overview = overview ?: "",
            genreIds = genreIds?.map { it.getGenre() }?.joinToString(separator = ", ") { it } ?: "",
            popularity = popularity ?: 0.0,
            voteAverage = voteAverage ?: 0.0,
            posterPath = posterPath?.loadImage() ?: "",
            backdropPath = backdropPath?.loadImageOriginal() ?: "",
            releaseDate = releaseDate?.convertDate() ?: ""
        )

    fun DetailResponse.mapToDetail(): Detail =
        Detail(
            id = id ?: 0,
            title = title ?: "",
            overview = overview ?: "",
            genres = genres?.joinToString(separator = " • ") { it.name } ?: "",
            runtime = runtime?.convertTime() ?: "",
            popularity = popularity ?: 0.0,
            voteAverage = voteAverage ?: 0.0,
            releaseDate = releaseDate?.convertDate() ?: "",
            posterPath = posterPath?.loadImage() ?: "",
            backdropPath = backdropPath?.loadImageOriginal() ?: ""
        )

    private fun Int.getGenre() =
        when (this) {
            28 -> "Action"
            12 -> "Adventure"
            16 -> "Animation"
            35 -> "Comedy"
            80 -> "Crime"
            99 -> "Documentary"
            18 -> "Drama"
            10751 -> "Family"
            14 -> "Fantasy"
            36 -> "History"
            27 -> "Horror"
            10402 -> "Music"
            9648 -> "Mystery"
            10749 -> "Romance"
            878 -> "Science Fiction"
            10770 -> "TV Movie"
            53 -> "Thriller"
            10752 -> "War"
            37 -> "Western"
            else -> "No genres available for $this"
        }

    private fun String.convertDate(): String {
        return if (this == "") {
            "Data kosong"
        } else {
            val input = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(this)
            val date = Date(input.time)
            val convert = SimpleDateFormat("MMM, dd yyyy", Locale.getDefault())
            convert.format(date)
        }
    }

    private fun Int.convertTime(): String {
        val hours = this / 60
        val minute = this % 60

        return "$hours hours $minute minute"
    }

    private fun String.loadImage(): String {
        val url = BuildConfig.BASE_URL_IMAGE
        val size = "w500"
        return url + size + this
    }

    private fun String.loadImageOriginal(): String {
        val url = BuildConfig.BASE_URL_IMAGE
        val size = "original"
        return url + size + this
    }
}