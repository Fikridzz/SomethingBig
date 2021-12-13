package co.id.fikridzakwan.somethingbig.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val genreIds: String,
    val popularity: Double,
    val voteAverage: Double,
    val releaseDate: String,
    val posterPath: String,
    val backdropPath: String
)