package com.legaldocai.viewmodels;

import com.legaldocai.database.QueryHistoryDao;
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
public final class HistoryViewModel_Factory implements Factory<HistoryViewModel> {
  private final Provider<QueryHistoryDao> queryHistoryDaoProvider;

  public HistoryViewModel_Factory(Provider<QueryHistoryDao> queryHistoryDaoProvider) {
    this.queryHistoryDaoProvider = queryHistoryDaoProvider;
  }

  @Override
  public HistoryViewModel get() {
    return newInstance(queryHistoryDaoProvider.get());
  }

  public static HistoryViewModel_Factory create(Provider<QueryHistoryDao> queryHistoryDaoProvider) {
    return new HistoryViewModel_Factory(queryHistoryDaoProvider);
  }

  public static HistoryViewModel newInstance(QueryHistoryDao queryHistoryDao) {
    return new HistoryViewModel(queryHistoryDao);
  }
}
