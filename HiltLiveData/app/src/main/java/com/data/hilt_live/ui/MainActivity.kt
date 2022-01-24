package com.data.hilt_live.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.data.hilt_live.R
import com.data.hilt_live.adapters.ImageAdapter
import com.data.hilt_live.adapters.SpacesItemDecoration
import com.data.hilt_live.model.ImageResponse
import com.data.hilt_live.model.Photo
import com.data.hilt_live.util.DataState
import com.data.hilt_live.viewModel.MainStateEvent
import com.data.hilt_live.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.lang.Exception

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ImageAdapter.ImageItemListener {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        subscribeObservers()
        viewModel.setStateEvent(MainStateEvent.GetPhotoEvents, et_search.text.toString().trim())

        et_search.setOnEditorActionListener { textView, i, keyEvent ->
            try {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyboard()
                    if (et_search.text.toString().trim().isNotEmpty())
                        viewModel.setStateEvent(MainStateEvent.GetPhotoEvents, et_search.text.toString().trim())
                    else
                        Toast.makeText(this,"Enter any keyword to search",Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            false
        }

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.setStateEvent(MainStateEvent.GetPhotoEvents, et_search.text.toString().trim())
        }

    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this, { dataState ->
            when (dataState) {
                is DataState.Success<ImageResponse> -> {
                    displayLoading(false)
                    dataState.data.photos?.let { populateRecyclerView(it) }
                }
                is DataState.Loading -> {
                    displayLoading(true)
                }
                is DataState.Error -> {
                    displayLoading(false)
                    displayError(dataState.exception.message)
                }
            }
        })
    }

    private fun displayError(message: String?) {
        if (message.isNullOrEmpty()) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Unknown error", Toast.LENGTH_LONG).show()
        }
    }

    private fun displayLoading(isLoading: Boolean) {
        swipeRefreshLayout.isRefreshing = isLoading
    }

    private fun populateRecyclerView(photos: List<Photo>) {
        if (photos.isNotEmpty()) adapter.setItems(photos)
    }

    private fun setupRecyclerView() {
        adapter = ImageAdapter(this)
        photo_recyclerview.layoutManager = GridLayoutManager(this, 3)
        photo_recyclerview.addItemDecoration(SpacesItemDecoration(4))
        photo_recyclerview.adapter = adapter
    }

    override fun onClickedImage(image: ArrayList<Photo>, position: Int) {
        val intent = Intent(this, ImageFullScreenActivity::class.java)
        intent.putExtra("imageList", image)
        intent.putExtra("pos", position)
        startActivity(intent)
    }

    fun hideKeyboard() {
        if (window != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            var view = currentFocus
            if (view == null) view = View(this)
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}