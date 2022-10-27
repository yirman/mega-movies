package com.ionix.megamovies.ui.fragment

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ionix.megamovies.R
import com.ionix.megamovies.data.entities.Movie
import com.ionix.megamovies.data.entities.parseDate
import com.ionix.megamovies.databinding.FragmentMovieInfoBinding
import com.ionix.megamovies.ui.activity.MoviePlayerActivity
import com.ionix.megamovies.ui.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalCoroutinesApi::class)
@AndroidEntryPoint
class MovieInfoFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentMovieInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieViewModel by viewModels()

    private var idMovie: String? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener { dialog ->
            val dialog1 = dialog as BottomSheetDialog
            val bottomSheet: FrameLayout = dialog1.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
            BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
            BottomSheetBehavior.from(bottomSheet).skipCollapsed = true
            BottomSheetBehavior.from(bottomSheet).isHideable = true

            val layoutParams = bottomSheet.layoutParams
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
            bottomSheet.layoutParams = layoutParams
        }
        return bottomSheetDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        idMovie = arguments?.getString("id")?.apply {
            setupObservers().also {
                viewModel.queryMovieDetail(this)
            }
        }


        binding.btnTrailer.setOnClickListener {
            val intent = Intent(requireActivity(), MoviePlayerActivity::class.java)
            intent.putExtra("video_url", Movie.SAMPLE_VIDEO)
            startActivity(intent)
        }
    }
    private fun setupObservers(){
        viewModel.movieDetail.observe(viewLifecycleOwner) { movie ->
            Glide.with(binding.root)
                .load(movie.image)
                .placeholder(CircularProgressDrawable(binding.root.context).apply { start() })
                .signature(ObjectKey(movie))
                .into(binding.ivMovie.ivImage)

            binding.tvTitle.text = movie.title
            binding.tvReleaseDate.text = movie.parseDate()
            binding.tvDescription.text = movie.plot
            binding.tvActors.text = getString(R.string.stars, movie.stars)

        }
    }
}