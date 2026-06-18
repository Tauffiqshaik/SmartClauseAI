# LegalDoc AI — Android App

> **Intelligent Legal Document Analysis & Voice-Based Q&A System Using LLMs**

A full-featured Android application that lets users upload legal documents (PDF, DOC, TXT), analyze them using Claude AI (Anthropic), and ask questions via text or voice.

---

## 📱 Features

| Feature | Description |
|---|---|
| 📄 Document Upload | PDF, DOC, DOCX, TXT support |
| 🤖 AI Analysis | Powered by Claude claude-opus-4-5 via Anthropic API |
| 🎤 Voice Q&A | Ask questions using your microphone |
| 📋 Auto Summarize | One-tap document summaries |
| ⚠️ Risk Detection | Identify problematic clauses |
| 🔑 Key Terms | Plain-English legal definitions |
| 🕐 Query History | Room DB-persisted Q&A history |
| 🌐 Multi-language | 10 language preferences |
| 🌙 Dark Mode | Full dark/light theme support |

---

## 🚀 Setup Instructions

### Step 1 — Open in Android Studio

1. Unzip `LegalDocAI.zip`
2. Open **Android Studio** (Hedgehog 2023.1.1 or later)
3. Choose **"Open"** and select the `LegalDocAI` folder
4. Wait for Gradle sync to complete (it will download the Gradle wrapper automatically)

### Step 2 — Add Your Anthropic API Key

**Option A — Build Config (recommended for development):**

Open `app/build.gradle` and replace:
```groovy
buildConfigField "String", "ANTHROPIC_API_KEY", "\"YOUR_ANTHROPIC_API_KEY_HERE\""
```
with your actual key:
```groovy
buildConfigField "String", "ANTHROPIC_API_KEY", "\"sk-ant-api03-xxxxxxxxxxxx\""
```

**Option B — In-App Settings:**

Run the app → go to **Settings tab** → enter your API key there. Keys saved in-app take priority over build config.

Get your API key at: https://console.anthropic.com

### Step 3 — Gradle Wrapper (if needed)

If Android Studio shows a Gradle wrapper error, run in terminal:
```bash
cd LegalDocAI
gradle wrapper --gradle-version 8.1.1
```
Or let Android Studio auto-fix it by clicking "Use Gradle Wrapper".

### Step 4 — Run

- Connect an Android device (API 26+) or start an emulator
- Click **Run ▶** in Android Studio

---

## 🏗️ Architecture

```
com.legaldocai/
├── activities/
│   ├── SplashActivity.kt          # Animated launch screen
│   ├── MainActivity.kt            # Bottom nav host
│   └── DocumentViewerActivity.kt  # Chat + voice Q&A screen
├── fragments/
│   ├── HomeFragment.kt            # Upload & feature overview
│   ├── DocumentsFragment.kt       # Document library
│   ├── HistoryFragment.kt         # Query history
│   └── SettingsFragment.kt        # API key, language, prefs
├── adapters/
│   ├── ChatAdapter.kt             # Chat bubble RecyclerView
│   ├── DocumentAdapter.kt         # Document list
│   └── HistoryAdapter.kt          # History list
├── models/
│   └── Models.kt                  # ChatMessage, DocumentItem, Anthropic DTOs
├── network/
│   ├── AnthropicApiService.kt     # Retrofit interface
│   ├── LegalAIRepository.kt       # Core AI logic + system prompt
│   └── AppModule.kt               # Hilt DI bindings
├── database/
│   └── AppDatabase.kt             # Room DB, DAOs
├── viewmodels/
│   └── ViewModels.kt              # DocumentVM, HomeVM, HistoryVM, SettingsVM
└── utils/
    ├── FileUtils.kt               # PDF/text extraction
    └── PreferencesManager.kt      # SharedPreferences wrapper
```

---

## 🔧 Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI | Material Design 3, ViewBinding |
| Navigation | Jetpack Navigation Component |
| DI | Hilt (Dagger) |
| Networking | Retrofit2 + OkHttp3 |
| AI Backend | Anthropic Claude API (claude-opus-4-5) |
| PDF Parsing | PDFBox Android |
| Database | Room (SQLite) |
| Animations | Lottie |
| Markdown | Markwon |
| Voice | Android SpeechRecognizer (built-in) |
| Architecture | MVVM + Repository pattern |

---

## 📋 Permissions

```xml
INTERNET              — Anthropic API calls
RECORD_AUDIO          — Voice input
READ_EXTERNAL_STORAGE — Document access (API < 33)
READ_MEDIA_IMAGES/AUDIO/VIDEO — Document access (API 33+)
VIBRATE               — Haptic feedback
```

---

## 🎨 Design System

- **Primary color**: Deep navy `#0A1628`
- **Accent / Gold**: `#C9A84C`
- **Mint accent**: `#64FFDA`
- **Typography**: Material Typography scale
- **Theme**: Dark-first, supports light via AppCompatDelegate

---

## ⚠️ Legal Disclaimer

This app provides AI-powered informational analysis only.  
It is **not** a substitute for professional legal advice.  
Always consult a qualified attorney for legal matters.

---

## 📦 Dependencies (key)

```gradle
// AI
retrofit2:retrofit:2.9.0
squareup.okhttp3:okhttp:4.12.0

// PDF
com.tom-roush:pdfbox-android:2.0.27.0

// DB
androidx.room:room-runtime:2.6.0

// DI
com.google.dagger:hilt-android:2.48

// UI
com.airbnb.android:lottie:6.1.0
io.noties.markwon:core:4.6.2
```

---

## 🐛 Troubleshooting

| Problem | Solution |
|---|---|
| Gradle sync fails | File → Invalidate Caches & Restart |
| API key error | Check key in Settings or build.gradle |
| PDF not loading | Ensure file is not password-protected |
| Voice not working | Grant RECORD_AUDIO permission in device settings |
| Lottie crash | Ensure `legal_animation.json` is in `res/raw/` |
