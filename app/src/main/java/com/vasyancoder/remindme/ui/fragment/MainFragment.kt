package com.vasyancoder.remindme.ui.fragment

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.vasyancoder.remindme.NoteApp
import com.vasyancoder.remindme.databinding.FragmentMainBinding
import com.vasyancoder.remindme.ui.adapter.NoteListAdapter
import com.vasyancoder.remindme.ui.viewmodel.NoteItemViewModel
import com.vasyancoder.remindme.ui.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[NoteItemViewModel::class.java]
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
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.notesList.layoutManager = LinearLayoutManager(requireContext())
        val adapter = NoteListAdapter()
        binding.notesList.adapter = adapter

        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager.isDefaultNetworkActive) {
            connectivityManager.registerDefaultNetworkCallback(object :
                ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    lifecycleScope.launch {
                        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                            viewModel.notesList.collect {
                                adapter.submitList(it)
                            }
                        }
                    }
                }

                override fun onLost(network: Network) {
                    lifecycleScope.launch {
                        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                            viewModel.notesListFromCache.collect {
                                adapter.submitList(it)
                            }
                        }
                    }
                }
            })
        } else {
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.notesListFromCache.collect {
                        adapter.submitList(it)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}