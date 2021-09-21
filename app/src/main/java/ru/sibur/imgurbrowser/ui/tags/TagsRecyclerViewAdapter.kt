package ru.sibur.imgurbrowser.ui.tags

import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ru.sibur.imgurbrowser.databinding.ItemTagBinding
import ru.sibur.imgurbrowser.layoutInflater
import ru.sibur.imgurbrowser.model.Tag

class TagsRecyclerViewAdapter(private val onTagClick: (tag: Tag) -> Unit) :
    ListAdapter<Tag, TagsRecyclerViewAdapter.TagViewHolder>(TagDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        return TagViewHolder(
            onTagClick,
            ItemTagBinding.inflate(parent.layoutInflater(), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TagViewHolder(
        private val onTagClick: (tag: Tag) -> Unit,
        private val binding: ItemTagBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        lateinit var tag: Tag

        init {
            binding.root.setOnClickListener {
                onTagClick(tag)
            }
        }

        fun bind(tag: Tag) {
            this.tag = tag

            binding.tagNameTv.text = tag.name

            val accentColor = (tag.accentColor ?: 0xff555555).toInt()
            binding.tagFadeView.background = ColorDrawable(accentColor)
            if (tag.coverImage != null) {
                Glide.with(itemView)
                    .load(tag.coverImage)
                    .optionalCenterCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.tagCoverIv)
            } else {
                binding.tagCoverIv.setImageDrawable(null)
            }
        }
    }

    class TagDiffCallback : DiffUtil.ItemCallback<Tag>() {
        override fun areItemsTheSame(oldItem: Tag, newItem: Tag): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Tag, newItem: Tag): Boolean {
            return oldItem == newItem
        }
    }
}
