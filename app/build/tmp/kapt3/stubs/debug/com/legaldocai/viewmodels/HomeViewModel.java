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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/legaldocai/viewmodels/HomeViewModel;", "Landroidx/lifecycle/ViewModel;", "documentDao", "Lcom/legaldocai/database/DocumentDao;", "(Lcom/legaldocai/database/DocumentDao;)V", "saveDocument", "", "doc", "Lcom/legaldocai/models/DocumentItem;", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class HomeViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.legaldocai.database.DocumentDao documentDao = null;
    
    @javax.inject.Inject()
    public HomeViewModel(@org.jetbrains.annotations.NotNull()
    com.legaldocai.database.DocumentDao documentDao) {
        super();
    }
    
    public final void saveDocument(@org.jetbrains.annotations.NotNull()
    com.legaldocai.models.DocumentItem doc) {
    }
}