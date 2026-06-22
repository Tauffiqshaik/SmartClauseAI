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
public final class DocumentsViewModel_Factory implements Factory<DocumentsViewModel> {
  private final Provider<DocumentDao> documentDaoProvider;

  public DocumentsViewModel_Factory(Provider<DocumentDao> documentDaoProvider) {
    this.documentDaoProvider = documentDaoProvider;
  }

  @Override
  public DocumentsViewModel get() {
    return newInstance(documentDaoProvider.get());
  }

  public static DocumentsViewModel_Factory create(Provider<DocumentDao> documentDaoProvider) {
    return new DocumentsViewModel_Factory(documentDaoProvider);
  }

  public static DocumentsViewModel newInstance(DocumentDao documentDao) {
    return new DocumentsViewModel(documentDao);
  }
}
