package com.legaldocai.viewmodels;

import android.content.Context;
import android.net.Uri;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.legaldocai.database.DocumentDao;
import com.legaldocai.database.QueryHistoryDao;
import com.legaldocai.models.DocumentItem;
import com.legaldocai.models.QueryHistory;
import com.legaldocai.network.LegalAIRepository;
import com.legaldocai.utils.FileUtils;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.Dispatchers;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\bJ\u000e\u0010\u000e\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u000f"}, d2 = {"Lcom/legaldocai/viewmodels/DocumentsViewModel;", "Landroidx/lifecycle/ViewModel;", "documentDao", "Lcom/legaldocai/database/DocumentDao;", "(Lcom/legaldocai/database/DocumentDao;)V", "documents", "Landroidx/lifecycle/LiveData;", "", "Lcom/legaldocai/models/DocumentItem;", "getDocuments", "()Landroidx/lifecycle/LiveData;", "addDocument", "", "doc", "deleteDocument", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class DocumentsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.legaldocai.database.DocumentDao documentDao = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<com.legaldocai.models.DocumentItem>> documents = null;
    
    @javax.inject.Inject()
    public DocumentsViewModel(@org.jetbrains.annotations.NotNull()
    com.legaldocai.database.DocumentDao documentDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.legaldocai.models.DocumentItem>> getDocuments() {
        return null;
    }
    
    public final void addDocument(@org.jetbrains.annotations.NotNull()
    com.legaldocai.models.DocumentItem doc) {
    }
    
    public final void deleteDocument(@org.jetbrains.annotations.NotNull()
    com.legaldocai.models.DocumentItem doc) {
    }
}