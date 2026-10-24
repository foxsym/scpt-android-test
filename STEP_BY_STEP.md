# Step-by-step: Build SC-PT standalone Android test APK

No PC required; GitHub browser on Android works, although PC makes file upload easier. Private repo recommended because original game files are included.

## 1. Download/unzip the starter
Extract `SCPT_Android_Port_Starter_v3.zip` on phone or desktop. Ensure all files and `.github/workflows/build-apk.yml` are preserved.

## 2. GitHub private repo
At https://github.com/new sign in, choose repository name `scpt-android-test`, select **Private**, and create it. GitHub Actions can run on private repositories subject to your account's usage limits.

## 3. Upload files into repo root
Use `Add file` > `Upload files` and upload `SC-PT_E1000_EN_102.jar`, `prepare_port.py`, markdown guides, plus hidden path `.github/workflows/build-apk.yml`. Some phone file browsers don't let you upload a hidden folder directly. If so, use GitHub's `Add file > Create new file` and name it `.github/workflows/build-apk.yml`, then paste the workflow file's content from the extracted folder. Ensure the workflow file is NOT at the repo root.

## 4. Start the workflow
Open **Actions** tab. Choose `Build SCPT test APK`; select **Run workflow**, then **Run workflow** again. If the workflow does not appear, verify file path, branch and YAML content. GitHub may ask you to enable Actions.

## 5. Inspect result
Open completed run. Green = build succeeded; under `Artifacts`, download `scpt-test-apk`, unzip it, and locate the `.apk`. Red = open failed step logs and copy the ERROR output; the workflow and upstream dependencies have not been tested here. Note J2ME Loader builds native modules and may need dependencies/NDK adjustments.

## 6. Install on phone
Transfer/download APK to the Android phone, allow `Install unknown apps` for your browser or file manager as prompted, install, and launch. Android may warn about debug/test apps. Only install APK from your own build. If Android blocks install, record the error, Android version and device model.

## 7. Test carefully
First check: game launches, portrait layout, input, audio, exit/resume, save data. You will probably see the default J2ME keypad: this build tests standalone packaging, NOT the custom joystick. Take a gameplay screenshot and record any crashes. Next phase is implementing the analog stick and four action buttons in the emulator code per `FINAL_BUTTON_MAPPING.md`.

### Troubleshooting
- `Workflow not found`: `.github/workflows/build-apk.yml` missing or incorrectly placed; Actions disabled.
- Gradle sync/NDK/library failure: share full build log; do not assume build success.
- Installer failure: share APK error, Android version, and architecture.
- Game runs but keypad is huge: expected for initial APK; custom controls are a separate code implementation.

Sources: https://github.com/nikita36078/J2ME-Loader and its `app/build.gradle` `midlet` product flavor.
