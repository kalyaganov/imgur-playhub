package ru.sibur.imgurbrowser.ui.tag

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.sibur.imgurbrowser.domain.TagsInteractor
import ru.sibur.imgurbrowser.model.Post
import ru.sibur.imgurbrowser.model.Tag
import javax.inject.Inject

@HiltViewModel
class TagViewModel @Inject constructor(
    private val tagsInteractor: TagsInteractor,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        const val ARG_TAG = "arg-tag"
    }

    sealed class ViewState {
        object Loading : ViewState()
        class Data(val posts: List<Post>) : ViewState()
        class Error(val throwable: Throwable) : ViewState()
    }

    private val tag: Tag by lazy { savedStateHandle.get(ARG_TAG)!! }

    private val _viewStateFlow = MutableStateFlow<ViewState>(ViewState.Loading)
    val viewStateFlow: StateFlow<ViewState> get() = _viewStateFlow

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            kotlin.runCatching { tagsInteractor.getTagPosts(tag) }
                .onFailure { _viewStateFlow.emit(ViewState.Error(it)) }
                .onSuccess { _viewStateFlow.emit(ViewState.Data(it)) }
        }
    }
}
