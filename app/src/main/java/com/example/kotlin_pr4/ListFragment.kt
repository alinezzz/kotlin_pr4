package com.example.kotlin_pr4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_pr4.databinding.FragmentListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var fileNames: MutableList<String>
    private lateinit var  images: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container,false)
        CoroutineScope(Dispatchers.IO).launch {
            val files = File("/data/data/com.example.kotlin_pr4/files/photos").listFiles()
            fileNames = mutableListOf<String>()
            images = mutableListOf<String>()
            files?.map { file ->
                fileNames.add(file.name.substring(0, file.name.length - 4))
                images.add(file.absolutePath)
            }

            withContext(Dispatchers.Main) {
                val adapter = PhotoListAdapter(fileNames, images)
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.recyclerView.adapter = adapter
            }

        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}