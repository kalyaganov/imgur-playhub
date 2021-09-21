package ru.sibur.imgurbrowser.ui.tags

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.sibur.imgurbrowser.domain.TagsInteractor
import ru.sibur.imgurbrowser.model.Tag
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TagsViewModel @Inject constructor(private val tagsInteractor: TagsInteractor) : ViewModel() {

    sealed class ViewState {
        object Loading : ViewState()
        class Data(val tags: List<Tag>) : ViewState()
        class Error(val throwable: Throwable) : ViewState()
    }

    private val _viewStateFlow = MutableStateFlow<ViewState>(ViewState.Loading)
    val viewStateFlow: StateFlow<ViewState> get() = _viewStateFlow

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            kotlin.runCatching { tagsInteractor.getTags() }
                .onFailure { _viewStateFlow.emit(ViewState.Error(it)) }
                .onSuccess { _viewStateFlow.emit(ViewState.Data(it)) }
        }
    }

    fun loadTagInfo(tag: Tag) {
        viewModelScope.launch {
            kotlin.runCatching { tagsInteractor.getTagPosts(tag) }
                .onFailure { Timber.e(it) }
                .onSuccess { posts -> posts.forEach { Timber.d(it.toString()) } }
        }
    }
}
