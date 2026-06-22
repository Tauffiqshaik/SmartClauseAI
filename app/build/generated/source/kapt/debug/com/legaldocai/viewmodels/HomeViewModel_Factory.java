package com.legaldocai.viewmodels;

import com.legaldocai.database.DocumentDao;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<DocumentDao> documentDaoProvider;

  public HomeViewModel_Factory(Provider<DocumentDao> documentDaoProvider) {
    this.documentDaoProvider = documentDaoProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(documentDaoProvider.get());
  }

  public static HomeViewModel_Factory create(Provider<DocumentDao> documentDaoProvider) {
    return new HomeViewModel_Factory(documentDaoProvider);
  }

  public static HomeViewModel newInstance(DocumentDao documentDao) {
    return new HomeViewModel(documentDao);
  }
}
