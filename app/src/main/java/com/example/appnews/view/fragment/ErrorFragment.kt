package com.example.appnews.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.appnews.R
import com.example.appnews.databinding.FragmentErrorBinding
import com.example.appnews.model.Article

class ErrorFragment : Fragment(), View.OnClickListener {
    private var _binding:FragmentErrorBinding? = null
    private val binding get() = _binding!!

    private val args: ErrorFragmentArgs by navArgs()

    private lateinit var textError: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentErrorBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnUpdate.setOnClickListener(this)


        showMessageTextView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_update){
            findNavController().navigate(R.id.action_errorFragment_to_home)
        }
    }

    private fun showMessageTextView(){
        textError = args.error
        binding.txtError.text = textError

    }



}