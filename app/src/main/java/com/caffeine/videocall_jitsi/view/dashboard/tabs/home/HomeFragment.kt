package com.caffeine.videocall_jitsi.view.dashboard.tabs.home

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.caffeine.videocall_jitsi.R
import com.caffeine.videocall_jitsi.databinding.FragmentHomeBinding
import com.caffeine.videocall_jitsi.services.model.common.User
import com.caffeine.videocall_jitsi.utils.SessionManager
import com.caffeine.videocall_jitsi.view.auth.AuthActivity
import com.caffeine.videocall_jitsi.view.calling.OutgoingCallActivity
import com.caffeine.videocall_jitsi.view.dashboard.tabs.home.adapter.UserAdapter
import com.caffeine.videocall_jitsi.view.utils.CustomDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.saadahmedsoft.base.BaseFragment
import com.saadahmedsoft.base.helper.linearLayoutManager
import com.saadahmedsoft.base.helper.onClicked
import com.saadahmedsoft.base.utils.Constants.Booleans.FALSE
import com.saadahmedsoft.base.utils.Constants.Database.userRef
import com.saadahmedsoft.interfaces.OnItemActionListener
import com.saadahmedsoft.popupdialog.PopupDialog
import com.saadahmedsoft.popupdialog.Styles
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener
import com.saadahmedsoft.shortintent.Anim
import com.saadahmedsoft.shortintent.ShortIntent

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

        binding.btnLogout.onClicked {
            onLogoutButtonClicked()
        }
    }

    override fun observeData() {}

    override fun onItemClickListener(view: View, item: User, position: Int) {
        shortToast("Calling ${item.name}")
        val i = Intent(requireContext(), OutgoingCallActivity::class.java)
        i.putExtra("uid", item.uid)
        i.putExtra("name", item.name)
        i.putExtra("email", item.email)
        requireContext().startActivity(i)
    }

    private fun fetchUsers() {
        dialog.showProgressDialog()
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<User>()
                val uid = SessionManager.getInstance(requireContext()).uid
                for (item in snapshot.children) {
                    if (item.getValue(User::class.java)?.uid != uid) {
                        item.getValue(User::class.java)?.let { list.add(it) }
                    }
                }
                adapter.addItems(list)
                dialog.dismissDialog()
            }

            override fun onCancelled(error: DatabaseError) {
                dialog.dismissDialog()
                longSnackBar(error.message)
            }
        })
    }

    private fun onLogoutButtonClicked() {
        PopupDialog.getInstance(requireContext())
            .setStyle(Styles.STANDARD)
            .setHeading("Logout")
            .setDescription("Are you sure you want to logout? This action cannot be undone.")
            .setPopupDialogIcon(R.drawable.ic_logout)
            .setPopupDialogIconTint(R.color.colorRed)
            .setPositiveButtonText("Logout")
            .setPositiveButtonBackground(R.drawable.ripple_bg_main_5)
            .setNegativeButtonBackground(R.drawable.ripple_bg_light_grey_5)
            .showDialog(object : OnDialogButtonClickListener() {
                override fun onNegativeClicked(dialog: Dialog?) {
                    super.onNegativeClicked(dialog)
                }

                override fun onPositiveClicked(dialog: Dialog?) {
                    onDialogPositiveButtonClicked(dialog!!)
                }
            })
    }

    private fun onDialogPositiveButtonClicked(dialog: Dialog) {
        val session = SessionManager.getInstance(requireContext())
        session.uid = ""
        FirebaseAuth.getInstance().signOut()

        ShortIntent.getInstance(requireActivity())
            .addDestination(AuthActivity::class.java)
            .addTransition(Anim.FADE)
            .finish(requireActivity())
        dialog.dismiss()
    }
}