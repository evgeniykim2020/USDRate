package com.evgeniykim.usdrate.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.evgeniykim.usdrate.R
import com.evgeniykim.usdrate.databinding.AlarmDialogBinding

class AlarmDialog : DialogFragment(R.layout.alarm_dialog) {

    private lateinit var binding: AlarmDialogBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = AlarmDialogBinding.bind(view)
    }
}