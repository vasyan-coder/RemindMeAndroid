package com.vasyancoder.remindme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vasyancoder.remindme.databinding.FragmentGreetingBinding


private const val ARG_TITLE = "titleNote"
private const val ARG_CONTENT = "contentNote"

class NoteFragment : Fragment() {
    private var paramTitle: String? = null
    private var paramContent: String? = null

    private var _binding: FragmentGreetingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            paramTitle = it.getString(ARG_TITLE)
            paramContent = it.getString(ARG_CONTENT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGreetingBinding.inflate(inflater, container, false)
        return binding.root    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(paramTitle: String, param2: String) =
            NoteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, paramContent)
                    putString(ARG_CONTENT, param2)
                }
            }
    }
}