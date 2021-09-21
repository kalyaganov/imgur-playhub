package ru.sibur.imgurbrowser.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.sibur.imgurbrowser.R
import ru.sibur.imgurbrowser.databinding.FragmentMainBinding
import ru.sibur.imgurbrowser.ui.account.AccountFlowFragment
import ru.sibur.imgurbrowser.ui.tags.TagsFragment

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding: FragmentMainBinding by viewBinding(FragmentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mainBottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    childFragmentManager.commit {
                        replace<TagsFragment>(R.id.main_fragment_container_view)
                    }
                }
                R.id.menu_account -> {
                    childFragmentManager.commit {
                        replace<AccountFlowFragment>(R.id.main_fragment_container_view)
                    }
                }
                else -> {
                }
            }
            true
        }

        if (childFragmentManager.fragments.isEmpty()) {
            binding.mainBottomNavigationView.selectedItemId = R.id.menu_home
        }
    }
}
