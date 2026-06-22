package com.legaldocai.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.legaldocai.databinding.ItemDocumentBinding
import com.legaldocai.databinding.ItemHistoryBinding
import com.legaldocai.models.DocumentItem
import com.legaldocai.models.QueryHistory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DocumentAdapter(
    private val onItemClick: (DocumentItem) -> Unit,
    private val onDeleteClick: (DocumentItem) -> Unit
) : ListAdapter<DocumentItem, DocumentAdapter.ViewHolder>(DocumentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDocumentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemDocumentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(doc: DocumentItem) {
            binding.tvDocName.text = doc.name
            binding.tvDocSize.text = doc.size
            val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            binding.tvDocDate.text = dateFormat.format(Date(doc.addedAt))
            binding.tvDocIcon.text = if (doc.name.endsWith(".pdf", true)) "📄" else "📝"
            binding.root.setOnClickListener { onItemClick(doc) }
            binding.btnDelete.setOnClickListener { onDeleteClick(doc) }
        }
    }

    class DocumentDiffCallback : DiffUtil.ItemCallback<DocumentItem>() {
        override fun areItemsTheSame(oldItem: DocumentItem, newItem: DocumentItem) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: DocumentItem, newItem: DocumentItem) = oldItem == newItem
    }
}

class HistoryAdapter : ListAdapter<QueryHistory, HistoryAdapter.ViewHolder>(HistoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: QueryHistory) {
            binding.tvQuery.text = item.query
            binding.tvResponse.text = item.response.take(150) + if (item.response.length > 150) "..." else ""
            binding.tvDocName.text = item.documentName.ifEmpty { "Unknown Document" }
            val dateFormat = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())
            binding.tvTimestamp.text = dateFormat.format(Date(item.timestamp))
        }
    }

    class HistoryDiffCallback : DiffUtil.ItemCallback<QueryHistory>() {
        override fun areItemsTheSame(oldItem: QueryHistory, newItem: QueryHistory) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: QueryHistory, newItem: QueryHistory) = oldItem == newItem
    }
}
