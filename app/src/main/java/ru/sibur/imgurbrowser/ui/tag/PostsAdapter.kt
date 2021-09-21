package ru.sibur.imgurbrowser.ui.tag

import android.view.ViewGroup
import androidx.core.view.doOnLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.sibur.imgurbrowser.databinding.ItemPostBinding
import ru.sibur.imgurbrowser.layoutInflater
import ru.sibur.imgurbrowser.model.Post

class PostsAdapter() :
    ListAdapter<Post, PostsAdapter.PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            ItemPostBinding.inflate(parent.layoutInflater(), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PostViewHolder(
        private val binding: ItemPostBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        lateinit var post: Post

        private var viewWidth = 0

        init {
            binding.root.doOnLayout {
                viewWidth = it.measuredWidth
                if (::post.isInitialized) {
                    bind(post)
                }
            }
        }

        fun bind(post: Post) {
            this.post = post

            binding.postTitleTv.text

            if (viewWidth == 0) return

            // TODO: set proper sizes

//            val accentColor = (tag.accentColor ?: 0xff555555).toInt()
//            binding.tagFadeView.background = ColorDrawable(accentColor)
//            if (tag.coverImage != null) {
//                Glide.with(itemView)
//                    .load(tag.coverImage)
//                    .optionalCenterCrop()
//                    .transition(DrawableTransitionOptions.withCrossFade())
//                    .into(binding.tagCoverIv)
//            } else {
//                binding.tagCoverIv.setImageDrawable(null)
//            }
        }
    }

    class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }
}
