package com.example.week5.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week5.GRID_LAYOUT
import com.example.week5.LINEAR_LAYOUT
import com.example.week5.R
import com.example.week5.adapter.TopRatedMovieAdapter
import com.example.week5.movie.Movie

class TopRatedMovieFragment(val topRatedMovieAdapter: TopRatedMovieAdapter):Fragment(){
    private lateinit var rcList : RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_now_playing_movie,container,false)
        rcList = view.findViewById<RecyclerView>(R.id.rcFavorite)
        rcList.layoutManager = LinearLayoutManager(this.context)
        rcList.adapter = topRatedMovieAdapter
        return view
    }
    fun changeLayout(viewType:Int){
        when(viewType){
            LINEAR_LAYOUT ->{
                rcList.layoutManager = LinearLayoutManager(this.context)
                topRatedMovieAdapter.changeViewType(LINEAR_LAYOUT)
            }
            GRID_LAYOUT ->{
                rcList.layoutManager = GridLayoutManager(this.context,2)
                topRatedMovieAdapter.changeViewType(GRID_LAYOUT)
            }
            else ->{
                print("error")
            }
        }
    }
}