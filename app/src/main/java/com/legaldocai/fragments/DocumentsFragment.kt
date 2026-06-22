package com.legaldocai.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.legaldocai.activities.DocumentViewerActivity
import com.legaldocai.adapters.DocumentAdapter
import com.legaldocai.databinding.FragmentDocumentsBinding
import com.legaldocai.models.DocumentItem
import com.legaldocai.utils.FileUtils
import com.legaldocai.viewmodels.DocumentsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DocumentsFragment : Fragment() {

    private var _binding: FragmentDocumentsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DocumentsViewModel by viewModels()
    private lateinit var documentAdapter: DocumentAdapter

    private val pickDocumentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                val name = FileUtils.getFileName(requireContext(), uri) ?: "Document"
                val size = FileUtils.getFileSize(requireContext(), uri)
                viewModel.addDocument(DocumentItem(name = name, uri = uri.toString(), size = size))
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDocumentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        documentAdapter = DocumentAdapter(
            onItemClick = { doc ->
                val intent = Intent(requireActivity(), DocumentViewerActivity::class.java).apply {
                    putExtra(DocumentViewerActivity.EXTRA_DOC_URI, doc.uri)
                    putExtra(DocumentViewerActivity.EXTRA_DOC_NAME, doc.name)
                }
                startActivity(intent)
            },
            onDeleteClick = { doc -> viewModel.deleteDocument(doc) }
        )
        binding.rvDocuments.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = documentAdapter
        }
    }

    private fun setupObservers() {
        viewModel.documents.observe(viewLifecycleOwner) { docs ->
            documentAdapter.submitList(docs)
            binding.emptyState.visibility = if (docs.isEmpty()) View.VISIBLE else View.GONE
            binding.rvDocuments.visibility = if (docs.isEmpty()) View.GONE else View.VISIBLE
        }
    }

    private fun setupClickListeners() {
        binding.fabAddDoc.setOnClickListener { pickDocument() }
    }

    private fun pickDocument() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("application/pdf", "text/plain",
                "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
        }
        pickDocumentLauncher.launch(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
