package com.legaldocai.viewmodels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.legaldocai.database.DocumentDao
import com.legaldocai.database.QueryHistoryDao
import com.legaldocai.models.DocumentItem
import com.legaldocai.models.QueryHistory
import com.legaldocai.network.LegalAIRepository
import com.legaldocai.utils.FileUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DocumentViewModel @Inject constructor(
    private val repository: LegalAIRepository,
    private val queryHistoryDao: QueryHistoryDao
) : ViewModel() {

    private val _documentText = MutableLiveData<String>()
    val documentText: LiveData<String> = _documentText

    private val _aiResponse = MutableLiveData<String>()
    val aiResponse: LiveData<String> = _aiResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private var documentContext = ""
    private var currentDocName = ""

    fun loadDocument(uri: Uri, context: Context, docName: String) {
        currentDocName = docName
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val text = withContext(Dispatchers.IO) {
                    FileUtils.extractTextFromUri(context, uri)
                }
                _documentText.value = text
            } catch (e: Exception) {
                _error.value = "Failed to load document: ${e.message}"
                _documentText.value = ""
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun setDocumentContext(text: String) {
        documentContext = text
    }

    fun askQuestion(question: String, docText: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.analyzeDocument(question, docText)
                result.fold(
                    onSuccess = { response ->
                        _aiResponse.value = response
                        // Save to history
                        queryHistoryDao.insertHistory(
                            QueryHistory(
                                query = question,
                                response = response,
                                documentName = currentDocName
                            )
                        )
                    },
                    onFailure = { error ->
                        _error.value = error.message ?: "An error occurred"
                    }
                )
            } catch (e: Exception) {
                _error.value = e.message ?: "Network error"
            } finally {
                _isLoading.value = false
            }
        }
    }
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val documentDao: DocumentDao
) : ViewModel() {
    fun saveDocument(doc: DocumentItem) {
        viewModelScope.launch { documentDao.insertDocument(doc) }
    }
}

@HiltViewModel
class DocumentsViewModel @Inject constructor(
    private val documentDao: DocumentDao
) : ViewModel() {

    val documents: LiveData<List<DocumentItem>> = documentDao.getAllDocuments()

    fun addDocument(doc: DocumentItem) {
        viewModelScope.launch { documentDao.insertDocument(doc) }
    }

    fun deleteDocument(doc: DocumentItem) {
        viewModelScope.launch { documentDao.deleteDocument(doc) }
    }
}

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val queryHistoryDao: QueryHistoryDao
) : ViewModel() {

    val history: LiveData<List<QueryHistory>> = queryHistoryDao.getAllHistory()

    fun clearHistory() {
        viewModelScope.launch { queryHistoryDao.clearHistory() }
    }
}

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesManager: com.legaldocai.utils.PreferencesManager
) : ViewModel() {

    private val _apiKey = MutableLiveData<String>()
    val apiKey: LiveData<String> = _apiKey

    private val _language = MutableLiveData<String>()
    val language: LiveData<String> = _language

    init {
        _apiKey.value = preferencesManager.getApiKey()
        _language.value = preferencesManager.getLanguage()
    }

    fun saveApiKey(key: String) { preferencesManager.saveApiKey(key); _apiKey.value = key }
    fun saveLanguage(lang: String) { preferencesManager.saveLanguage(lang); _language.value = lang }
    fun setVoiceFeedback(enabled: Boolean) { preferencesManager.setVoiceFeedback(enabled) }
    fun setDarkMode(enabled: Boolean, activity: android.app.Activity) {
        preferencesManager.setDarkMode(enabled)
        androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(
            if (enabled) androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
            else androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
        )
    }
}
