package com.example.week5.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.week5.GRID_LAYOUT
import com.example.week5.LINEAR_LAYOUT
import com.example.week5.R
import com.example.week5.movie.Movie

class TopRatedMovieAdapter(val ctx:Context):ListAdapter<Movie, TopRatedMovieAdapter.ViewHolder>(TopRatedMovieDiffUtilCallback()) {
    private var currentLayout = LINEAR_LAYOUT

    interface TopRatedMovieAdapterListener{
        fun onClickItem(movie: Movie)
    }
    var listener : TopRatedMovieAdapterListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View
        if (viewType == LINEAR_LAYOUT){
            view = LayoutInflater.from(ctx).inflate(R.layout.item_movie_linear,parent,false)
        } else {
            view = LayoutInflater.from(ctx).inflate(R.layout.item_movie_grid,parent,false)
        }
        return ViewHolder(view)
    }
    override fun getItemViewType(position: Int): Int {
        if (currentLayout == GRID_LAYOUT){
            return GRID_LAYOUT
        }
        return LINEAR_LAYOUT
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,listener)
    }
    fun changeViewType(viewType: Int){
        currentLayout = viewType
    }
    class ViewHolder(val itemView: View):RecyclerView.ViewHolder(itemView){
        val itemName : TextView = itemView.findViewById(R.id.tv_name)
        val itemAddress : TextView = itemView.findViewById(R.id.tv_address)
        val itemPicture : ImageView = itemView.findViewById(R.id.iv_image)
        val picturePath = "https://image.tmdb.org/t/p/w500"
        fun bind(movie: Movie,listener: TopRatedMovieAdapterListener?){
            itemName.text = movie.title
            itemAddress.text = movie.overview
            Glide.with(itemView.context).load(picturePath+movie.posterPath).into(itemPicture)
            itemView.setOnClickListener {
                listener?.onClickItem(movie)
            }
        }
    }
    class TopRatedMovieDiffUtilCallback : DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}