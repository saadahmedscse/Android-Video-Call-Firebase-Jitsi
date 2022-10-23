package com.caffeine.videocall_jitsi.view.dashboard.tabs.home.adapter

import com.caffeine.videocall_jitsi.R
import com.caffeine.videocall_jitsi.databinding.ItemLayoutUserBinding
import com.caffeine.videocall_jitsi.services.model.User
import com.saadahmedsoft.base.BaseRecyclerAdapter
import com.saadahmedsoft.base.helper.onClicked
import com.saadahmedsoft.interfaces.OnItemActionListener

class UserAdapter(private val listener: OnItemActionListener<User>) :
    BaseRecyclerAdapter<User, ItemLayoutUserBinding>() {
    override val layoutRes: Int
        get() = R.layout.item_layout_user

    override fun onBind(binding: ItemLayoutUserBinding, item: User, position: Int) {
        binding.item = item
        binding.btnCall.onClicked {
            listener.onItemClickListener(binding.root, item, position)
        }
    }
}