# SC-PT Android portrait touch controller — specification

**Status:** design and port preparation, **not a completed or tested APK**.

## Desired presentation
- Portrait only. Keep the original Java ME screen uncropped and with its original aspect ratio.
- Full-screen, no emulator title bar while playing.
- Put a dark charcoal/navy gamepad panel **below the game**, not over artwork.
- Left: 4-way cross (up/left/right/down); optionally center/select.
- Right: primary A (fire/confirm), secondary B (context/crouch), small L and R shortcuts.
- Top-right of control zone: tiny settings/menu button, not over the game.
- Buttons: rounded, inset-style, charcoal translucency with subtle cyan-green active state; diameter about 48–56 dp for primary buttons and 36–42 dp for secondary (adjust to screen width).
- Single-tap and simultaneous multi-touch must both work. Game keys need keyDown/keyUp behavior rather than one-shot clicks.
- Keep an accessible expanded keypad drawer (0–9, *, #, soft keys) until every in-game action is tested.

## Mapping to verify on device
Directional pad -> J2ME directional keys. Primary -> J2ME FIRE action. Secondary and L/R -> discover original in-game bindings (do not guess them). Map start/pause and softkeys only after testing.

## App wiring
J2ME Loader `midlet` Gradle product flavor provides the standalone launcher. The game assets/classes are placed in `app/src/midlet/resources`, with `MIDLET-META-INF/MANIFEST.MF`. Implement the custom panel using the emulator's virtual keypad plumbing so it sends the same MIDP Canvas keyDown/keyUp events as its stock keyboard. Reuse its virtual-key handling and multi-touch routing; a decorative XML layout alone would not control the game.

## Limits
The archive's original graphics and code remain copyrighted by their original owner. This bundle is for private experimentation using the supplied JAR. Android compatibility and runtime/build behavior haven't been validated here.
