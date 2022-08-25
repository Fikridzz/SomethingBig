package co.id.fikridzakwan.domain.model

data class Detail(
    val id: Int,
    val title: String,
    val overview: String,
    val genres: String,
    val runtime: String,
    val popularity: Double,
    val voteAverage: Double,
    val releaseDate: String,
    val posterPath: String,
    val backdropPath: String,
//    var isFavorite: Boolean
)