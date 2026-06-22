package com.legaldocai.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.legaldocai.R
import com.legaldocai.adapters.ChatAdapter
import com.legaldocai.databinding.ActivityDocumentViewerBinding
import com.legaldocai.models.ChatMessage
import com.legaldocai.models.MessageType
import com.legaldocai.utils.FileUtils
import com.legaldocai.viewmodels.DocumentViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class DocumentViewerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDocumentViewerBinding
    private val viewModel: DocumentViewModel by viewModels()
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var speechRecognizer: SpeechRecognizer
    private var isListening = false
    private var documentText = ""

    companion object {
        const val EXTRA_DOC_URI = "extra_doc_uri"
        const val EXTRA_DOC_NAME = "extra_doc_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocumentViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupSpeechRecognizer()
        setupObservers()
        setupClickListeners()
        loadDocument()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val docName = intent.getStringExtra(EXTRA_DOC_NAME) ?: "Document"
        binding.toolbar.title = docName
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter()
        binding.rvChat.apply {
            layoutManager = LinearLayoutManager(this@DocumentViewerActivity).apply {
                stackFromEnd = true
            }
            adapter = chatAdapter
        }
    }

    private fun loadDocument() {
        val uriString = intent.getStringExtra(EXTRA_DOC_URI) ?: return
        val docName = intent.getStringExtra(EXTRA_DOC_NAME) ?: "Document"
        val uri = Uri.parse(uriString)
        binding.progressBar.visibility = View.VISIBLE

        viewModel.loadDocument(uri, this, docName)
    }

    private fun setupObservers() {
        viewModel.documentText.observe(this) { text ->
            documentText = text
            binding.progressBar.visibility = View.GONE
            if (text.isNotEmpty()) {
                addBotMessage("✅ Document loaded successfully! I've analyzed **${text.length / 5} words** across your legal document.\n\nYou can now:\n• Ask me questions about this document\n• Request a summary\n• Ask about specific clauses or terms\n• Use the 🎤 mic to ask via voice")
                viewModel.setDocumentContext(text)
            } else {
                addBotMessage("⚠️ Could not extract text from this document. Please ensure it's a readable PDF or text file.")
            }
        }

        viewModel.aiResponse.observe(this) { response ->
            binding.progressBar.visibility = View.GONE
            binding.btnSend.isEnabled = true
            addBotMessage(response)
            scrollToBottom()
        }

        viewModel.isLoading.observe(this) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
            binding.btnSend.isEnabled = !loading
        }

        viewModel.error.observe(this) { error ->
            binding.progressBar.visibility = View.GONE
            binding.btnSend.isEnabled = true
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        }
    }

    private fun setupClickListeners() {
        binding.btnSend.setOnClickListener {
            val query = binding.etQuery.text.toString().trim()
            if (query.isNotEmpty()) {
                sendQuery(query)
                binding.etQuery.setText("")
            }
        }

        binding.btnMic.setOnClickListener {
            if (isListening) stopListening() else startListening()
        }

        binding.btnSummarize.setOnClickListener {
            sendQuery("Please provide a comprehensive summary of this legal document, including key parties, obligations, dates, and important clauses.")
        }

        binding.btnRisks.setOnClickListener {
            sendQuery("Identify and explain any potential risks, unfavorable clauses, or red flags in this legal document that I should be aware of.")
        }

        binding.btnKeyTerms.setOnClickListener {
            sendQuery("Extract and explain all important legal terms, definitions, and key provisions from this document.")
        }
    }

    private fun sendQuery(query: String) {
        addUserMessage(query)
        binding.progressBar.visibility = View.VISIBLE
        binding.btnSend.isEnabled = false
        viewModel.askQuestion(query, documentText)
    }

    private fun addUserMessage(text: String) {
        chatAdapter.addMessage(ChatMessage(text, MessageType.USER))
        scrollToBottom()
    }

    private fun addBotMessage(text: String) {
        chatAdapter.addMessage(ChatMessage(text, MessageType.BOT))
        scrollToBottom()
    }

    private fun scrollToBottom() {
        binding.rvChat.postDelayed({
            if (chatAdapter.itemCount > 0) {
                binding.rvChat.smoothScrollToPosition(chatAdapter.itemCount - 1)
            }
        }, 100)
    }

    private fun setupSpeechRecognizer() {
        if (!SpeechRecognizer.isRecognitionAvailable(this)) {
            binding.btnMic.isEnabled = false
            return
        }
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                binding.btnMic.setImageResource(R.drawable.ic_mic_active)
                binding.tvVoiceHint.visibility = View.VISIBLE
            }
            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) {
                    val spokenText = matches[0]
                    binding.etQuery.setText(spokenText)
                    sendQuery(spokenText)
                    binding.etQuery.setText("")
                }
                stopListening()
            }
            override fun onError(error: Int) { stopListening() }
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })
    }

    private fun startListening() {
        isListening = true
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Ask your legal question...")
        }
        speechRecognizer.startListening(intent)
    }

    private fun stopListening() {
        isListening = false
        speechRecognizer.stopListening()
        binding.btnMic.setImageResource(R.drawable.ic_mic)
        binding.tvVoiceHint.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::speechRecognizer.isInitialized) {
            speechRecognizer.destroy()
        }
    }
}
