package co.id.fikridzakwan.somethingbig.presentation.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.id.fikridzakwan.somethingbig.R
import co.id.fikridzakwan.somethingbig.utils.AppConstants.EXTRA_ID

class DetailMovieActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context, id: Int) {
            val intent = Intent(context, DetailMovieActivity::class.java)
            intent.putExtra(EXTRA_ID, id)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
    }
}