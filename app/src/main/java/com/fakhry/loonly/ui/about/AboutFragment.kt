package com.fakhry.loonly.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fakhry.loonly.R
import com.fakhry.loonly.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {
    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnGithub.setOnClickListener {
                val githubUrl = getString(R.string.github_url)
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(githubUrl)))
            }
            btnMail.setOnClickListener {
                val gmail = getString(R.string.gmail_address)
                val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$gmail"))
                startActivity(intent)
            }
            btnInstagram.setOnClickListener {
                val instagramUrl = getString(R.string.instagram_url)
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl)))
            }
        }
    }
}