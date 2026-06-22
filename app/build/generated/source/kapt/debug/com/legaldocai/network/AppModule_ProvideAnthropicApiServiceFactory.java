package com.legaldocai.network;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

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
public final class AppModule_ProvideAnthropicApiServiceFactory implements Factory<AnthropicApiService> {
  private final Provider<Retrofit> retrofitProvider;

  public AppModule_ProvideAnthropicApiServiceFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public AnthropicApiService get() {
    return provideAnthropicApiService(retrofitProvider.get());
  }

  public static AppModule_ProvideAnthropicApiServiceFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new AppModule_ProvideAnthropicApiServiceFactory(retrofitProvider);
  }

  public static AnthropicApiService provideAnthropicApiService(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideAnthropicApiService(retrofit));
  }
}
