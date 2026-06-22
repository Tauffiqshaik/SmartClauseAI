package com.legaldocai.network;

import com.legaldocai.utils.PreferencesManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class LegalAIRepository_Factory implements Factory<LegalAIRepository> {
  private final Provider<AnthropicApiService> apiServiceProvider;

  private final Provider<PreferencesManager> preferencesManagerProvider;

  public LegalAIRepository_Factory(Provider<AnthropicApiService> apiServiceProvider,
      Provider<PreferencesManager> preferencesManagerProvider) {
    this.apiServiceProvider = apiServiceProvider;
    this.preferencesManagerProvider = preferencesManagerProvider;
  }

  @Override
  public LegalAIRepository get() {
    return newInstance(apiServiceProvider.get(), preferencesManagerProvider.get());
  }

  public static LegalAIRepository_Factory create(Provider<AnthropicApiService> apiServiceProvider,
      Provider<PreferencesManager> preferencesManagerProvider) {
    return new LegalAIRepository_Factory(apiServiceProvider, preferencesManagerProvider);
  }

  public static LegalAIRepository newInstance(AnthropicApiService apiService,
      PreferencesManager preferencesManager) {
    return new LegalAIRepository(apiService, preferencesManager);
  }
}
