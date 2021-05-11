package com.unava.dia.trellolightmvi.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.unava.dia.trellolightmvi.R
import com.unava.dia.trellolightmvi.util.Inflate
abstract class BaseFragment<VB : ViewBinding>(private val inflate: Inflate<VB>) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @LayoutRes
    abstract fun layoutId(): Int

    fun replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(
                R.id.main_fragment_container,
                fragment
            ).commit()

    }
    fun showToast(message: String, appContext: Context) {
        Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show()
    }
}