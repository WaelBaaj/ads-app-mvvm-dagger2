package com.wa82bj.ads_mvvm_github.ui.main.news

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import com.wa82bj.ads_mvvm_github.data.repository.news.NewsRepository
import com.wa82bj.ads_mvvm_github.ui.common.toResult
import com.wa82bj.ads_mvvm_github.util.ext.map
import com.wa82bj.ads_mvvm_github.util.ext.toLiveData
import com.wa82bj.ads_mvvm_github.util.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val repository: NewsRepository,
    private val schedulerProvider: SchedulerProvider
) : ViewModel(), LifecycleObserver {

    private val compositeDisposable = CompositeDisposable()
    private val page = MutableLiveData<Int>()
    private val _isLoadingRefresh = ObservableBoolean()
    val isLoadingRefresh: ObservableBoolean = _isLoadingRefresh
    private val _refresh = MutableLiveData<Boolean>()
    val refresh: LiveData<Boolean> = _refresh


    private val _news = Transformations
        .switchMap(page) { page ->
            return@switchMap repository.loadAllNewsApi()
                .toResult(schedulerProvider)
                .toLiveData()
        }
    val news = _news

    private val _newsDB = Transformations
        .switchMap(page) { page ->
            return@switchMap repository.loadAllNewsFromDb()
                .toResult(schedulerProvider)
                .toLiveData()
        }

    val newsDB = _newsDB

    private val _isLoading: LiveData<Boolean> by lazy {
        _news.map {
            _isLoadingRefresh.set(it.inProgress)
            it.inProgress
        }
    }

    val isLoading = _isLoading

    fun loadNews(page: Int = 1) {
        this.page.value = page
    }

    fun onRefresh() {
        _refresh.value = true
        page.value = 1
    }

    fun retry() {
        page.value = page.value
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

