# Build the first SC-PT standalone APK with GitHub Actions

This is the **first functional build test**, NOT the final stylish-controller release. The game's original JAR is packaged into the official J2ME Loader `midlet` flavor. Custom touch-button art and mapping must be implemented and tested separately.

## One-time setup

1. Make a **private** GitHub repository, for example `scpt-android-test`. Keep the supplied commercial JAR private and do not share built APKs publicly.
2. Upload the **contents** of this `scpt_android_starter` directory at the repository root, including the hidden `.github/workflows/build-apk.yml` file. GitHub's web upload may hide dotfolders on some systems; using GitHub Desktop or `git push` is safer.
3. In the repository, open **Actions** > **Build SCPT test APK** > **Run workflow**.
4. When it finishes, open the workflow run, find **Artifacts**, and download `scpt-test-apk`.
5. Unzip that artifact, install the APK on Android, and test launch/orientation/gameplay. You may need to allow installation from the app used to open the APK.

If the workflow fails, the GitHub Actions log gives the exact dependency or build error. This build has **not** been verified here; Android's SDK, JDK, and native-library dependencies are handled on the GitHub-hosted runner.

## Known limitations

- The stock keypad may still appear. The compact green-accent buttons discussed in chat are **not yet implemented**.
- Version of J2ME Loader may change; workflow currently checks out upstream's default branch.
- APK should be kept for private testing with your legally obtained game copy.

## Source and licensing

Runtime: https://github.com/nikita36078/J2ME-Loader. The Android emulator's license and Gameloft's game copyright remain separate.
