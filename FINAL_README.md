SkyNet — Final Bitrise-ready package (High-quality UI & TTS)
===========================================================

What was added/changed:
- High-resolution triangle launcher icons (mipmap-*) and adaptive icon XML.
- res/raw/tts_defaults.json with tuned pitch/rate for Persian and English for master/friend modes.
- TtsDefaultsLoader.kt helper (app/src/main/kotlin/com.skynet.voiceassistant/TtsDefaultsLoader.kt).
- UI color palette (res/values/colors.xml) and placeholder adaptive icon.
- SettingsActivity hints to apply TTS defaults.
- Keystore/signing config should be provided via Bitrise Secrets before release build.

Important notes:
- I could not run Gradle here; please run the release build on Bitrise or locally.
- If Bitrise build fails, send me the build log and I will fix any issues quickly.

Build checklist for Bitrise:
1. Upload this repo or point Bitrise to the GitHub repo.
2. Set Secrets: ANDROID_KEYSTORE (base64), KEYSTORE_PASSWORD, KEY_ALIAS, KEY_PASSWORD.
3. Start a Release build. Check Artifacts for APK/AAB.
4. Test on device: Hotword, Notification reading (allow Notification Access), TTS master/friend modes, OPENAI online features.
