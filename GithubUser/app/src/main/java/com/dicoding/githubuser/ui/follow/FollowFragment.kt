package com.dicoding.githubuser.ui.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.databinding.FragmentFollowBinding
import com.dicoding.githubuser.ui.main.UserAdapter


class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var followViewModel: FollowViewModel
    private var userAdapter: UserAdapter? = null

    private var position: Int = 0
    private var username: String? =null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followViewModel = ViewModelProvider(this).get(FollowViewModel::class.java)

        userAdapter = UserAdapter(requireContext(), emptyList())

        binding.rvFollow.layoutManager = LinearLayoutManager(requireActivity())

        binding.rvFollow.adapter = userAdapter

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        followViewModel.followersLive.observe(viewLifecycleOwner, { followersList ->
            if (followersList.isNotEmpty()) {
                userAdapter?.submitList(followersList)
                showList()
            } else {
                showEmptyView()
            }
        })

        followViewModel.followingLive.observe(viewLifecycleOwner, { followingList ->
            if (followingList.isNotEmpty()) {
                userAdapter?.submitList(followingList)
                showList()
            } else {
                showEmptyView()
            }
        })

        followViewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })

        if (position == 1){
            followViewModel.setFollower(username?:"")
        } else {
            followViewModel.setFollowing(username?:"")
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.textView.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showList() {
        binding.rvFollow.visibility = View.VISIBLE
        binding.textView.visibility = View.GONE
    }

    private fun showEmptyView() {
        binding.rvFollow.visibility = View.GONE
        binding.textView.visibility = View.VISIBLE
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

}