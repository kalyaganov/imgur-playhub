package ru.sibur.imgurbrowser.ui.account.user

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.doOnLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.coroutines.flow.collect
import ru.sibur.imgurbrowser.R
import ru.sibur.imgurbrowser.databinding.FragmentUserBinding
import ru.sibur.imgurbrowser.dp
import ru.sibur.imgurbrowser.launchAndRepeatWithViewLifecycle

@AndroidEntryPoint
class UserFragment : Fragment(R.layout.fragment_user) {
    private val binding: FragmentUserBinding by viewBinding(FragmentUserBinding::bind)

    private val viewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.userImageLl.applyInsetter {
            type(statusBars = true) {
                padding()
            }
        }

        binding.logoutButton.setOnClickListener { viewModel.logout() }

        launchAndRepeatWithViewLifecycle {
            viewModel.viewStateFlow.collect { render(it) }
        }
    }

    private fun render(viewState: UserViewModel.ViewState) {
        when (viewState) {
            is UserViewModel.ViewState.Data -> {
                binding.progress.isVisible = false
                binding.userImageLl.isVisible = true
                binding.userImageLl.removeAllViews()
                binding.root.background = null
                viewState.images.forEach { image ->
                    val imageView = ImageView(requireContext(), null, 0)
                    imageView.layoutParams = LinearLayout.LayoutParams(200.dp, 200.dp)
                    imageView.setPadding(8.dp, 8.dp, 8.dp, 8.dp)
                    binding.userImageLl.addView(imageView)
                    imageView.doOnLayout {
                        Glide.with(this)
                            .load(image.link)
                            .optionalCenterCrop()
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(imageView)
                    }
                }
                binding.userImageLl.requestLayout()
            }
            is UserViewModel.ViewState.Error -> {
                binding.progress.isVisible = false
                binding.userImageLl.isVisible = false
                binding.root.background = ColorDrawable(0xff0000)
            }
            UserViewModel.ViewState.Loading -> {
                binding.progress.isVisible = true
                binding.userImageLl.isVisible = false
                binding.root.background = null
            }
        }
    }
}
