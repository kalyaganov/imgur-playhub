package ru.sibur.imgurbrowser.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import dagger.hilt.android.AndroidEntryPoint
import ru.sibur.imgurbrowser.R
import ru.sibur.imgurbrowser.databinding.ActivityLaunchBinding
import ru.sibur.imgurbrowser.ui.main.MainFragment

@AndroidEntryPoint
class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val binding = ActivityLaunchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (supportFragmentManager.fragments.isEmpty()) {
            supportFragmentManager.commit {
                replace<MainFragment>(R.id.fragment_container_view_tag)
            }
        }
    }
}
