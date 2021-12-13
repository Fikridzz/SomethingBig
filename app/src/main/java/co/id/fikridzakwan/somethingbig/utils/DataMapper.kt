package co.id.fikridzakwan.somethingbig.utils

import co.id.fikridzakwan.somethingbig.data.source.response.ResultsItem
import co.id.fikridzakwan.somethingbig.domain.model.Movie

object DataMapper {

    fun ResultsItem.mapToMovie(): Movie =
        Movie(
            id = id ?: 0,
            title = title ?: "",
            overview = overview ?: "",
            genreIds = genreIds?.map { it.getGenre() }?.joinToString(separator = ", ") { it } ?: "",
            popularity = popularity ?: 0.0,
            voteAverage = voteAverage ?: 0.0,
            releaseDate = releaseDate ?: "",
            posterPath = posterPath ?: "",
            backdropPath = backdropPath ?: ""
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
}