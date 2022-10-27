package com.ionix.megamovies.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ionix.megamovies.R
import com.ionix.megamovies.databinding.FragmentCarouselsBinding
import com.ionix.megamovies.ui.adapter.CarouselAdapter
import com.ionix.megamovies.ui.viewmodel.MovieViewModel
import com.ionix.megamovies.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@AndroidEntryPoint
class MovieCarouselFragment : Fragment(), CarouselAdapter.MovieHandler {

    private var _binding: FragmentCarouselsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MovieViewModel
    private lateinit var adapter: CarouselAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarouselsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        setupRecyclerView()
        setupObserver()
        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = CarouselAdapter(this)
        binding.rvCarousels.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCarousels.adapter = adapter
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.queryMovies(refreshApi = true)
        }
    }

    private fun setupObserver(){
        viewModel.movies.observe(requireActivity()) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.swipeRefresh.isRefreshing = false
                    if (!it.data.isNullOrEmpty()) {
                        adapter.setItems(ArrayList(it.data))
                        adapter.notifyDataSetChanged()
                        binding.tvNoData.visibility = View.GONE
                    } else {
                        binding.tvNoData.visibility = View.VISIBLE
                    }
                }

                Resource.Status.ERROR -> {
                    binding.swipeRefresh.isRefreshing = false
                    Toast.makeText(
                        requireContext(),
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    if (adapter.itemCount == 0) binding.tvNoData.visibility = View.VISIBLE
                }

                Resource.Status.LOADING -> {
                    binding.swipeRefresh.isRefreshing = true
                    binding.tvNoData.visibility = View.GONE
                }
            }
        }
        viewModel.queryMovies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickMovie(idMovie: String) {
        findNavController().navigate(
            R.id.action_carouselsFragment_to_movieInfoFragment,
            bundleOf("id" to idMovie)
        )
    }

}