package com.legaldocai.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.legaldocai.R;
import com.legaldocai.adapters.ChatAdapter;
import com.legaldocai.databinding.ActivityDocumentViewerBinding;
import com.legaldocai.models.ChatMessage;
import com.legaldocai.models.MessageType;
import com.legaldocai.utils.FileUtils;
import com.legaldocai.viewmodels.DocumentViewModel;
import dagger.hilt.android.AndroidEntryPoint;
import java.util.Locale;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\r\b\u0007\u0018\u0000 &2\u00020\u0001:\u0001&B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\bH\u0002J\u0010\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\bH\u0002J\b\u0010\u0017\u001a\u00020\u0014H\u0002J\u0012\u0010\u0018\u001a\u00020\u00142\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0014J\b\u0010\u001b\u001a\u00020\u0014H\u0014J\b\u0010\u001c\u001a\u00020\u0014H\u0002J\u0010\u0010\u001d\u001a\u00020\u00142\u0006\u0010\u001e\u001a\u00020\bH\u0002J\b\u0010\u001f\u001a\u00020\u0014H\u0002J\b\u0010 \u001a\u00020\u0014H\u0002J\b\u0010!\u001a\u00020\u0014H\u0002J\b\u0010\"\u001a\u00020\u0014H\u0002J\b\u0010#\u001a\u00020\u0014H\u0002J\b\u0010$\u001a\u00020\u0014H\u0002J\b\u0010%\u001a\u00020\u0014H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u001b\u0010\r\u001a\u00020\u000e8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\'"}, d2 = {"Lcom/legaldocai/activities/DocumentViewerActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/legaldocai/databinding/ActivityDocumentViewerBinding;", "chatAdapter", "Lcom/legaldocai/adapters/ChatAdapter;", "documentText", "", "isListening", "", "speechRecognizer", "Landroid/speech/SpeechRecognizer;", "viewModel", "Lcom/legaldocai/viewmodels/DocumentViewModel;", "getViewModel", "()Lcom/legaldocai/viewmodels/DocumentViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "addBotMessage", "", "text", "addUserMessage", "loadDocument", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "scrollToBottom", "sendQuery", "query", "setupClickListeners", "setupObservers", "setupRecyclerView", "setupSpeechRecognizer", "setupToolbar", "startListening", "stopListening", "Companion", "app_debug"})
public final class DocumentViewerActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.legaldocai.databinding.ActivityDocumentViewerBinding binding;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    private com.legaldocai.adapters.ChatAdapter chatAdapter;
    private android.speech.SpeechRecognizer speechRecognizer;
    private boolean isListening = false;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String documentText = "";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_DOC_URI = "extra_doc_uri";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_DOC_NAME = "extra_doc_name";
    @org.jetbrains.annotations.NotNull()
    public static final com.legaldocai.activities.DocumentViewerActivity.Companion Companion = null;
    
    public DocumentViewerActivity() {
        super();
    }
    
    private final com.legaldocai.viewmodels.DocumentViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupToolbar() {
    }
    
    private final void setupRecyclerView() {
    }
    
    private final void loadDocument() {
    }
    
    private final void setupObservers() {
    }
    
    private final void setupClickListeners() {
    }
    
    private final void sendQuery(java.lang.String query) {
    }
    
    private final void addUserMessage(java.lang.String text) {
    }
    
    private final void addBotMessage(java.lang.String text) {
    }
    
    private final void scrollToBottom() {
    }
    
    private final void setupSpeechRecognizer() {
    }
    
    private final void startListening() {
    }
    
    private final void stopListening() {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/legaldocai/activities/DocumentViewerActivity$Companion;", "", "()V", "EXTRA_DOC_NAME", "", "EXTRA_DOC_URI", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}