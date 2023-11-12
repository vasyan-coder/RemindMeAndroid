package com.vasyancoder.remindme.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.vasyancoder.remindme.NoteApp
import com.vasyancoder.remindme.databinding.FragmentProfileBinding
import com.vasyancoder.remindme.ui.viewmodel.ProfileViewModel
import com.vasyancoder.remindme.ui.viewmodel.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ProfileViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as NoteApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val bitmap = Glide.with(requireContext())
                    .asBitmap()
                    .load(binding.editText.text.toString())
                    .submit()
                    .get()
                viewModel.saveImage(bitmap)

                withContext(Dispatchers.Main) {
                    binding.imageView.setImageBitmap(bitmap)
                }
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}