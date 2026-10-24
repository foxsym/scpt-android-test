# Splinter Cell: Pandora Tomorrow — Android standalone port starter

This is a **starter package**, **not an APK**. It contains your supplied Gameloft JAR, a script to stage it in J2ME Loader's official `midlet` flavor, and the compact portrait control-design specification. It does not yet modify the emulator's virtual keypad appearance.

## Steps to build an initial standalone APK on a computer

1. Install Android Studio and its required Android SDK/NDK plus a suitable JDK; obtain the latest upstream source from https://github.com/nikita36078/J2ME-Loader (Git clone with submodules if required).
2. In a terminal run `python prepare_port.py /path/to/J2ME-Loader ./SC-PT_E1000_EN_102.jar` (use `python3` on systems where appropriate).
3. Go to J2ME Loader's checkout directory.
4. On Linux/macOS run `./gradlew :app:assembleMidletDebug`; on Windows use `gradlew.bat :app:assembleMidletDebug`.
5. Find the APK under `app/build/outputs/apk/midlet/debug/`. The exact name is determined by upstream Gradle. Install and test it on an Android phone.
6. For the **stylish compact touch buttons**, follow `CONTROLS_DESIGN.md` and customize the emulator's actual virtual-key UI before calling this a finished port.

**Build status:** Not built or tested in this environment (Android SDK, Gradle and Android runtime unavailable). The game/port may require additional runtime configuration. The official `midlet` Gradle flavor has been inspected, but its actual runtime setup is unverified here. This preparation script updates upstream's `app/src/midlet/AndroidManifest.xml` to request portrait mode.

Licensing: the open-source J2ME Loader runtime is under Apache-2.0; the included game remains third-party copyrighted content and is not relicensed by this package.


**Latest:** See `FINAL_BUTTON_MAPPING.md` for proposed joystick and button mapping and `STEP_BY_STEP.md` for first GitHub APK build. The custom joystick remains UNIMPLEMENTED.
