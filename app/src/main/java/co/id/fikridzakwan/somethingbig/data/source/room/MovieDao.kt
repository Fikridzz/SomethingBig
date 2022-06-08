package co.id.fikridzakwan.somethingbig.data.source.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.id.fikridzakwan.somethingbig.data.source.model.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie_entities")
    fun getFavoriteMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie_entities where movie_id= :movieId")
    fun getFavoriteMovieById(movieId: Int) : Flow<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(data: MovieEntity)

    @Query("DELETE FROM movie_entities WHERE movie_id= :movieId")
    suspend fun deleteFavoriteMovie(movieId: Int)

    @Query("DELETE FROM movie_entities")
    suspend fun deleteAllFavoriteMovies()
}