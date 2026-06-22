package com.legaldocai.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.legaldocai.activities.DocumentViewerActivity
import com.legaldocai.databinding.FragmentHomeBinding
import com.legaldocai.models.DocumentItem
import com.legaldocai.utils.FileUtils
import com.legaldocai.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    private val pickDocumentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                openDocument(uri)
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) pickDocument()
        else Toast.makeText(requireContext(), "Storage permission required", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        setupAnimations()
    }

    private fun setupAnimations() {
        binding.lottieHome.playAnimation()
    }

    private fun setupClickListeners() {
        binding.btnUploadPdf.setOnClickListener { checkPermissionAndPick() }
        binding.btnUploadDoc.setOnClickListener { checkPermissionAndPick() }
        binding.cardUploadArea.setOnClickListener { checkPermissionAndPick() }
    }

    private fun checkPermissionAndPick() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            pickDocument()
        } else {
            val permission = Manifest.permission.READ_EXTERNAL_STORAGE
            if (ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED) {
                pickDocument()
            } else {
                requestPermissionLauncher.launch(permission)
            }
        }
    }

    private fun pickDocument() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("application/pdf", "text/plain", "application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
        }
        pickDocumentLauncher.launch(intent)
    }

    private fun openDocument(uri: Uri) {
        val fileName = FileUtils.getFileName(requireContext(), uri) ?: "Document"
        val fileSize = FileUtils.getFileSize(requireContext(), uri)
        
        // Save to database so it shows up in the "Documents" tab
        viewModel.saveDocument(DocumentItem(name = fileName, uri = uri.toString(), size = fileSize))

        val intent = Intent(requireActivity(), DocumentViewerActivity::class.java).apply {
            putExtra(DocumentViewerActivity.EXTRA_DOC_URI, uri.toString())
            putExtra(DocumentViewerActivity.EXTRA_DOC_NAME, fileName)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
