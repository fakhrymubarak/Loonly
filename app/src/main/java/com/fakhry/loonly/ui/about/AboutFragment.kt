package com.fakhry.loonly.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.fakhry.loonly.R
import com.fakhry.loonly.core.utils.viewBinding
import com.fakhry.loonly.databinding.FragmentAboutBinding

class AboutFragment : Fragment(R.layout.fragment_about) {
    private val binding: FragmentAboutBinding by viewBinding(FragmentAboutBinding::bind)

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