package ru.sibur.imgurbrowser.ui.tag

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.coroutines.flow.collect
import ru.sibur.imgurbrowser.*
import ru.sibur.imgurbrowser.databinding.FragmentTagBinding
import ru.sibur.imgurbrowser.model.Tag
import ru.sibur.imgurbrowser.ui.common.GridSpacingItemDecoration

@AndroidEntryPoint
class TagFragment : Fragment(R.layout.fragment_tag) {

    companion object {
        fun create(tag: Tag): TagFragment {
            return TagFragment().apply {
                arguments = bundleOf(TagViewModel.ARG_TAG to tag)
            }
        }
    }

    private val viewModel: TagViewModel by viewModels()

    private val binding: FragmentTagBinding by viewBinding(FragmentTagBinding::bind)

    private val adapter: PostsAdapter by lazy {
        PostsAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listOf(binding.tagContent.tagRv, binding.tagSkeleton.root).forEach { view ->
            view.applyInsetter {
                type(navigationBars = true, statusBars = true) {
                    padding()
                }
            }
        }

        binding.tagError.errorButton.setOnClickListener { viewModel.refresh() }

        with(binding.tagContent.tagRv) {
            adapter = this@TagFragment.adapter

            val spanCount = if (requireContext().isLandscape()) 3 else 2
            layoutManager = GridLayoutManager(requireContext(), spanCount)
            addItemDecoration(GridSpacingItemDecoration(spanCount, 16.dp, true))
        }

        launchAndRepeatWithViewLifecycle {
            viewModel.viewStateFlow.collect { render(it) }
        }
    }

    private fun render(viewState: TagViewModel.ViewState) {
        when (viewState) {
            is TagViewModel.ViewState.Data -> {
                binding.tagContent.root.isVisible = true
                binding.tagError.root.isVisible = false
                binding.tagSkeleton.root.isVisible = false

                adapter.submitList(viewState.posts)
            }
            is TagViewModel.ViewState.Error -> {
                binding.tagContent.root.isVisible = false
                binding.tagError.root.isVisible = true
                binding.tagSkeleton.root.isVisible = false

                binding.tagError.errorTv.text =
                    viewState.throwable.localizedMessage
                        ?: getString(R.string.layout_error_something_wrong)
            }
            TagViewModel.ViewState.Loading -> {
                binding.tagContent.root.isVisible = false
                binding.tagError.root.isVisible = false
                binding.tagSkeleton.root.isVisible = true
            }
        }
    }
}
