package com.legaldocai.network;

import com.legaldocai.BuildConfig;
import com.legaldocai.models.ChatRequest;
import com.legaldocai.models.ChatRequestMessage;
import timber.log.Timber;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J<\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\n2\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\b2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u0012"}, d2 = {"Lcom/legaldocai/network/LegalAIRepository;", "", "apiService", "Lcom/legaldocai/network/AnthropicApiService;", "preferencesManager", "Lcom/legaldocai/utils/PreferencesManager;", "(Lcom/legaldocai/network/AnthropicApiService;Lcom/legaldocai/utils/PreferencesManager;)V", "systemPrompt", "", "analyzeDocument", "Lkotlin/Result;", "question", "documentText", "conversationHistory", "", "Lcom/legaldocai/models/ChatRequestMessage;", "analyzeDocument-BWLJW6A", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class LegalAIRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.legaldocai.network.AnthropicApiService apiService = null;
    @org.jetbrains.annotations.NotNull()
    private final com.legaldocai.utils.PreferencesManager preferencesManager = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String systemPrompt = null;
    
    @javax.inject.Inject()
    public LegalAIRepository(@org.jetbrains.annotations.NotNull()
    com.legaldocai.network.AnthropicApiService apiService, @org.jetbrains.annotations.NotNull()
    com.legaldocai.utils.PreferencesManager preferencesManager) {
        super();
    }
}