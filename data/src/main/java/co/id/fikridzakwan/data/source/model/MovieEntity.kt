package co.id.fikridzakwan.data.source.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_entities")
data class MovieEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "movie_id")
    var id: Int,

    @ColumnInfo(name = "title")
    var title: String?,

    @ColumnInfo(name = "overview")
    var overview: String?,

    @ColumnInfo(name = "runtime")
    var runtime: String?,

    @ColumnInfo(name = "genres")
    var genres: String?,

    @ColumnInfo(name = "release_date")
    var releaseDate: String?,

    @ColumnInfo(name = "popularity")
    var popularity: Double?,

    @ColumnInfo(name = "vote_average")
    var voteAverage: Double?,

    @ColumnInfo(name = "poster_path")
    var posterPath: String?,

    @ColumnInfo(name = "backdrop_path")
    var backdropPath: String?,

//    @ColumnInfo(name = "isFavorite")
//    var isFavorite: Boolean = false
)