package co.id.fikridzakwan.core.domain.model

data class Movie(
    val id: Int?,
    val title: String?,
    val overview: String?,
    val genres: String?,
    val releaseDate: String?,
    val popularity: Double?,
    val voteAverage: Double?,
    val posterPath: String?,
    val backdropPath: String?
)