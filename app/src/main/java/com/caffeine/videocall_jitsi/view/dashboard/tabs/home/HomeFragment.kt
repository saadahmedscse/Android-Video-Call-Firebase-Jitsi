package com.caffeine.videocall_jitsi.view.dashboard.tabs.home

import android.os.Bundle
import android.view.View
import com.caffeine.videocall_jitsi.databinding.FragmentHomeBinding
import com.caffeine.videocall_jitsi.services.model.User
import com.caffeine.videocall_jitsi.utils.SessionManager
import com.caffeine.videocall_jitsi.view.dashboard.tabs.home.adapter.UserAdapter
import com.caffeine.videocall_jitsi.view.utils.CustomDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.saadahmedsoft.base.BaseFragment
import com.saadahmedsoft.base.helper.linearLayoutManager
import com.saadahmedsoft.base.utils.Constants.Booleans.FALSE
import com.saadahmedsoft.base.utils.Constants.Database.userRef
import com.saadahmedsoft.interfaces.OnItemActionListener

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate), OnItemActionListener<User> {
    override val title: String
        get() = "Home"
    override val isBackButtonVisible: Boolean
        get() = FALSE

    private val adapter by lazy {
        UserAdapter(this)
    }

    private lateinit var dialog: CustomDialog

    override fun onFragmentCreate(savedInstanceState: Bundle?) {
        dialog = CustomDialog.getInstance(requireContext())
        binding.recyclerView.layoutManager = linearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        fetchUsers()
    }

    override fun observeData() {}

    override fun onItemClickListener(view: View, item: User, position: Int) {
        //
    }

    private fun fetchUsers() {
        dialog.showProgressDialog()
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<User>()
                val listFiltered = ArrayList<User>()
                val uid = SessionManager.getInstance(requireContext()).uid
                for (item in snapshot.children) {
                    item.getValue(User::class.java)?.let { list.add(it) }
                }
                for (item in list) {
                    if (item.uid != uid) {
                        listFiltered.add(item)
                    }
                }
                adapter.addItems(listFiltered)
                dialog.dismissDialog()
            }

            override fun onCancelled(error: DatabaseError) {
                dialog.dismissDialog()
                longSnackBar(error.message)
            }
        })
    }
}