package com.github.nikeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.nikeapp.model.Items
import kotlinx.android.synthetic.main.item_view.view.*


class CustomAdapter(val users: List<Items.Info>) : RecyclerView.Adapter<ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_view,parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(users[position])
    }
}
class ViewHolder (view : View) : RecyclerView.ViewHolder(view){
    val TAG : String = "TAG"

    val author = itemView.author
    val current_vote = itemView.current_vote
    val defid = itemView.defid
    val definition = itemView.definition
    val example = itemView.example
    val permalink = itemView.permalink
    val sound_urls = itemView.sound_urls
    val thumbs_down = itemView.thumbs_down
    val thumbs_up = itemView.thumbs_up
    val word = itemView.word
    val written_on = itemView.written_on
    val raiting = itemView.raiting
    val display = itemView.displayImage


    var myImageList = intArrayOf(R.drawable.icon_0001, R.drawable.icon_0002,R.drawable.icon_0003,R.drawable.icon_0004,R.drawable.icon_0005,R.drawable.icon_0006,R.drawable.icon_0007,R.drawable.icon_0008,R.drawable.icon_0009)


    fun onBind(items: Items.Info) {
        author.text = items.author
        current_vote.text = items.current_vote
        //defid.text = items.defid.toString()
        definition.text = items.definition
        example.text = items.example
        permalink.text = items.permalink
        //music here
        //sound_urls.text = items.sound_urls.toString()
        thumbs_down.text = items.thumbs_down.toString()
        thumbs_up.text = items.thumbs_up.toString()
        word.text = items.word
        written_on.text = items.written_on
        //rateStars.rating = 5f
        raiting.progress =   items.thumbs_up
        var randomImg = (0..8).random()

        display.setBackgroundResource(myImageList[randomImg])


    }

}