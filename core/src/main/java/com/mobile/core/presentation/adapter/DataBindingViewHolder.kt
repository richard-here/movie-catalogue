package com.mobile.core.presentation.adapter

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.mobile.core.presentation.adapter.listener.BindableListener

class DataBindingViewHolder<T>(private val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(obj: T?, listener: BindableListener?) {
        obj?.let {
            binding.setVariable(BR.item, obj)
        }
        listener?.let {
            binding.setVariable(BR.clickListener, it)
        }
        binding.executePendingBindings()
    }
}