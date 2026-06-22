package com.legaldocai.viewmodels;

import com.legaldocai.database.QueryHistoryDao;
import com.legaldocai.network.LegalAIRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class DocumentViewModel_Factory implements Factory<DocumentViewModel> {
  private final Provider<LegalAIRepository> repositoryProvider;

  private final Provider<QueryHistoryDao> queryHistoryDaoProvider;

  public DocumentViewModel_Factory(Provider<LegalAIRepository> repositoryProvider,
      Provider<QueryHistoryDao> queryHistoryDaoProvider) {
    this.repositoryProvider = repositoryProvider;
    this.queryHistoryDaoProvider = queryHistoryDaoProvider;
  }

  @Override
  public DocumentViewModel get() {
    return newInstance(repositoryProvider.get(), queryHistoryDaoProvider.get());
  }

  public static DocumentViewModel_Factory create(Provider<LegalAIRepository> repositoryProvider,
      Provider<QueryHistoryDao> queryHistoryDaoProvider) {
    return new DocumentViewModel_Factory(repositoryProvider, queryHistoryDaoProvider);
  }

  public static DocumentViewModel newInstance(LegalAIRepository repository,
      QueryHistoryDao queryHistoryDao) {
    return new DocumentViewModel(repository, queryHistoryDao);
  }
}
