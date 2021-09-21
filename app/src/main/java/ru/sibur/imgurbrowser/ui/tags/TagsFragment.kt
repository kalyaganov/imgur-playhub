package ru.sibur.imgurbrowser.ui.tags

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.coroutines.flow.collect
import ru.sibur.imgurbrowser.*
import ru.sibur.imgurbrowser.databinding.FragmentTagsBinding
import ru.sibur.imgurbrowser.ui.common.GridSpacingItemDecoration

@AndroidEntryPoint
class TagsFragment : Fragment(R.layout.fragment_tags) {

    private val viewModel: TagsViewModel by viewModels()

    private val binding: FragmentTagsBinding by viewBinding(FragmentTagsBinding::bind)

    private val adapter: TagsRecyclerViewAdapter by lazy {
        TagsRecyclerViewAdapter { clickedTag ->
            viewModel.loadTagInfo(clickedTag)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listOf(binding.tagsContent.tagsRv, binding.tagsSkeleton.root).forEach { view ->
            view.applyInsetter {
                type(statusBars = true) {
                    padding()
                }
            }
        }

        binding.tagsError.errorButton.setOnClickListener { viewModel.refresh() }

        with(binding.tagsContent.tagsRv) {
            adapter = this@TagsFragment.adapter

            val spanCount = if (requireContext().isLandscape()) 3 else 2
            layoutManager = GridLayoutManager(requireContext(), spanCount)
            addItemDecoration(GridSpacingItemDecoration(spanCount, 16.dp, true))
        }

        launchAndRepeatWithViewLifecycle {
            viewModel.viewStateFlow.collect { render(it) }
        }
    }

    private fun render(viewState: TagsViewModel.ViewState) {
        when (viewState) {
            is TagsViewModel.ViewState.Data -> {
                binding.tagsContent.root.isVisible = true
                binding.tagsError.root.isVisible = false
                binding.tagsSkeleton.root.isVisible = false

                adapter.submitList(viewState.tags)
            }
            is TagsViewModel.ViewState.Error -> {
                binding.tagsContent.root.isVisible = false
                binding.tagsError.root.isVisible = true
                binding.tagsSkeleton.root.isVisible = false

                binding.tagsError.errorTv.text =
                    viewState.throwable.localizedMessage
                        ?: getString(R.string.layout_error_something_wrong)
            }
            TagsViewModel.ViewState.Loading -> {
                binding.tagsContent.root.isVisible = false
                binding.tagsError.root.isVisible = false
                binding.tagsSkeleton.root.isVisible = true
            }
        }
    }
}
