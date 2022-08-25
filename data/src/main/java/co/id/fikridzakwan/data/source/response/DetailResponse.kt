package co.id.fikridzakwan.data.source.response

import com.google.gson.annotations.SerializedName

data class DetailResponse(

    @field:SerializedName("original_language")
    val originalLanguage: String?,

    @field:SerializedName("imdb_id")
    val imdbId: String?,

    @field:SerializedName("video")
    val video: Boolean?,

    @field:SerializedName("title")
    val title: String?,

    @field:SerializedName("backdrop_path")
    val backdropPath: String?,

    @field:SerializedName("revenue")
    val revenue: Int?,

    @field:SerializedName("genres")
    val genres: List<GenresItem>?,

    @field:SerializedName("popularity")
    val popularity: Double?,

    @field:SerializedName("production_countries")
    val productionCountries: List<ProductionCountriesItem>?,

    @field:SerializedName("id")
    val id: Int?,

    @field:SerializedName("vote_count")
    val voteCount: Int?,

    @field:SerializedName("budget")
    val budget: Int?,

    @field:SerializedName("overview")
    val overview: String?,

    @field:SerializedName("original_title")
    val originalTitle: String?,

    @field:SerializedName("runtime")
    val runtime: Int?,

    @field:SerializedName("poster_path")
    val posterPath: String?,

    @field:SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguagesItem>?,

    @field:SerializedName("production_companies")
    val productionCompanies: List<ProductionCompaniesItem>?,

    @field:SerializedName("release_date")
    val releaseDate: String?,

    @field:SerializedName("vote_average")
    val voteAverage: Double?,

    @field:SerializedName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection?,

    @field:SerializedName("tagline")
    val tagline: String?,

    @field:SerializedName("adult")
    val adult: Boolean?,

    @field:SerializedName("homepage")
    val homepage: String?,

    @field:SerializedName("status")
    val status: String?,

    @field:SerializedName("credits")
    val credits: Credits
)

data class Credits(

    @field:SerializedName("cast")
    val cast: List<CastItem>,

    @field:SerializedName("crew")
    val crew: List<CrewItem>
)

data class CastItem(

    @field:SerializedName("cast_id")
    val castId: Int,

    @field:SerializedName("character")
    val character: String,

    @field:SerializedName("gender")
    val gender: Int,

    @field:SerializedName("credit_id")
    val creditId: String,

    @field:SerializedName("known_for_department")
    val knownForDepartment: String,

    @field:SerializedName("original_name")
    val originalName: String,

    @field:SerializedName("popularity")
    val popularity: Double,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("profile_path")
    val profilePath: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("adult")
    val adult: Boolean,

    @field:SerializedName("order")
    val order: Int
)

data class BelongsToCollection(

    @field:SerializedName("backdrop_path")
    val backdropPath: String?,

    @field:SerializedName("name")
    val name: String?,

    @field:SerializedName("id")
    val id: Int?,

    @field:SerializedName("poster_path")
    val posterPath: String?
)

data class SpokenLanguagesItem(

    @field:SerializedName("name")
    val name: String?,

    @field:SerializedName("iso_639_1")
    val iso6391: String?,

    @field:SerializedName("english_name")
    val englishName: String?
)

data class ProductionCountriesItem(

    @field:SerializedName("iso_3166_1")
    val iso31661: String?,

    @field:SerializedName("name")
    val name: String?
)

data class GenresItem(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int
)

data class ProductionCompaniesItem(

    @field:SerializedName("logo_path")
    val logoPath: String?,

    @field:SerializedName("name")
    val name: String?,

    @field:SerializedName("id")
    val id: Int?,

    @field:SerializedName("origin_country")
    val originCountry: String?
)

data class CrewItem(

    @field:SerializedName("gender")
    val gender: Int,

    @field:SerializedName("credit_id")
    val creditId: String,

    @field:SerializedName("known_for_department")
    val knownForDepartment: String,

    @field:SerializedName("original_name")
    val originalName: String,

    @field:SerializedName("popularity")
    val popularity: Double,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("profile_path")
    val profilePath: Any,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("adult")
    val adult: Boolean,

    @field:SerializedName("department")
    val department: String,

    @field:SerializedName("job")
    val job: String
)
