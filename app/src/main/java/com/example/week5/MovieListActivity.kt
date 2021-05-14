package com.example.week5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.week5.adapter.NowPlayingMovieAdapter
import com.example.week5.adapter.TopRatedMovieAdapter
import com.example.week5.databinding.ActivityMovieListBinding
import com.example.week5.fragment.MovieDetailFragment
import com.example.week5.fragment.NowPlayingMovieFragment
import com.example.week5.fragment.TopRatedMovieFragment
import com.example.week5.movie.Movie
import com.example.week5.viewmodel.MovieViewModel

class MovieListActivity : AppCompatActivity() {
    private var currentLayout : Int = LINEAR_LAYOUT
    private lateinit var nowPlayingMovieFragment: NowPlayingMovieFragment
    private lateinit var topRatedMovieFragment: TopRatedMovieFragment
    private lateinit var nowPlayingMovieAdapter: NowPlayingMovieAdapter
    private lateinit var topRatedMovieAdapter: TopRatedMovieAdapter
    private lateinit var binding: ActivityMovieListBinding
    private lateinit var movieDetailFragment: MovieDetailFragment
    private lateinit var menu: Menu
    lateinit var movieViewModel: MovieViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        //ViewModel
        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        movieViewModel.listNowPlaying.observe(this, Observer {
            submitListAdapter(it,isNowPlayingMovieFragment = true)
        })
        movieViewModel.listTopRated.observe(this, Observer {
            submitListAdapter(it,isNowPlayingMovieFragment = false)
        })
        //Fragment & Adapter
        nowPlayingMovieAdapter = NowPlayingMovieAdapter(this)
        nowPlayingMovieFragment = NowPlayingMovieFragment(nowPlayingMovieAdapter)
        topRatedMovieAdapter = TopRatedMovieAdapter(this)
        topRatedMovieFragment = TopRatedMovieFragment(topRatedMovieAdapter)
        setCurrentFragment(nowPlayingMovieFragment)

        nowPlayingMovieAdapter.listener = object : NowPlayingMovieAdapter.NowPlayingMovieAdapterListener {
            override fun onClickItem(movie: Movie) {
                movieDetailFragment = MovieDetailFragment(movie)
                setCurrentFragment(movieDetailFragment)
            }
        }

        topRatedMovieAdapter.listener= object : TopRatedMovieAdapter.TopRatedMovieAdapterListener {
            override fun onClickItem(movie: Movie) {
                movieDetailFragment = MovieDetailFragment(movie)
                setCurrentFragment(movieDetailFragment)
            }
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_list)
        binding.navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_now_playing -> setCurrentFragment(nowPlayingMovieFragment)
                R.id.navigation_top_rated -> setCurrentFragment(topRatedMovieFragment)
            }
            true
        }
        //
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

    }

    override fun onStart() {
        super.onStart()
        movieViewModel.getNowPlaying()
        movieViewModel.getTopRated()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_switch, menu)
        this.menu = menu!!
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sw_layout -> {
                val icon = if (currentLayout == LINEAR_LAYOUT) R.drawable.ic_baseline_grid_off_24 else R.drawable.ic_baseline_grid_on_24
                if (currentLayout == LINEAR_LAYOUT) {
                    nowPlayingMovieFragment.changeLayout(GRID_LAYOUT)
                    topRatedMovieFragment.changeLayout(GRID_LAYOUT)
                    menu[0].icon =
                        ContextCompat.getDrawable(this, icon)
                    currentLayout = GRID_LAYOUT
                } else {
                    nowPlayingMovieFragment.changeLayout(LINEAR_LAYOUT)
                    topRatedMovieFragment.changeLayout(LINEAR_LAYOUT)
                    menu[0].icon =
                        ContextCompat.getDrawable(this, icon)
                    currentLayout = LINEAR_LAYOUT
                }
            }
        }
        return true
    }
    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragmentContainerView,fragment)
            addToBackStack(null)
        }

    }
    fun submitListAdapter(listMovie: List<Movie>?,isNowPlayingMovieFragment:Boolean){
        if (isNowPlayingMovieFragment){
            nowPlayingMovieAdapter.submitList(listMovie)
        } else {
            topRatedMovieAdapter.submitList(listMovie)
        }
    }
}