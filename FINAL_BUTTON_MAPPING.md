# SC-PT Android — Button Mapping Specification v1.0 (proposed, subject to game testing)

## Layout
Portrait orientation. Game display retains native aspect ratio and remains completely unobstructed. Hide emulator toolbar where supported. Bottom control strip uses remaining screen height; joystick left, compact A/B/C/D right, small Pause.

## Essential input policy
- The game responds to Java ME key events; the Android UI must use actual key-down, held-key and key-up semantics. A tap is not the same as a held direction.
- 8-way joystick directions map to J2ME numeric keys: NW=1, N=2, NE=3, W=4, E=6, SW=7, S=8, SE=9. Neutral releases the current key. These represent requested test mapping, not proof of the game's action behavior.
- Use a central deadzone of 18% joystick radius to prevent accidental movement, with optional 12–28% setting. Hold direction until changed or released; on angle-sector change release old key before pressing new key. Preserve ability to hold direction while tapping an action button.
- A (green, 52 dp minimum): initially keypad 5 / FIRE, investigate if device FIRE action differs from numerical 5.
- B (red, 46 dp): initially assign 'jump/climb' only after identifying its real game key. Temporary test value: UP (2), but potentially overlaps joystick N/NE/NW; no production mapping until verified.
- C (blue, 46 dp): initially assign 'crouch/stand' only after confirming key behavior. Temporary test value: DOWN (8), may conflict with joystick S/SE/SW.
- D (amber, 42 dp): tentative keypad 0 for weapon; confirm in-game.
- Pause/Menu (gray, 36-44 dp): menu/soft key needs confirmation; avoid Android Back unconditionally exiting the game.
- Always provide an emergency optional full keypad overlay for testing hidden controls and menus. Toggle through long-press on Pause; final behavior subject to runtime implementation.

## Multi-touch
Each finger owns its pointer ID. Joystick must track one assigned pointer; button presses may overlap; a pointer release must only release the key it owns. Release all keys on focus loss, pause, interruption and touch cancel.

## Design
Graphite #101820 panel, muted green #8FE8A5 highlight, green A, red B, blue C, amber D; non-game area only. Concept: 70% visual alpha idle, fully opaque pressed; 8 dp separation, minimum 44dp touch targets when space permits. Portrait responsive measurements adapt to screen size.

## Test acceptance criteria
[ ] Game launches directly as standalone app
[ ] Game fully visible without cropping or distortion
[ ] Main menus navigable
[ ] Stick covers each of 8 sectors
[ ] Stick released stops holding J2ME key
[ ] Move + action simultaneously succeeds
[ ] A/B/C/D commands verified on gameplay, not merely assumed
[ ] Save/load, sound and app resume work
[ ] Pause/exit does not lose progress unexpectedly
[ ] Layout remains usable on target Android handset

## Version distinctions
v0.1 build via supplied workflow = standalone runtime proof of concept, stock controls likely remain.
v0.2 requires programming a custom analog-stick UI and button overlay in the J2ME runtime, plus game-specific key testing. v0.3 = styling, remapping and polish. Current ZIP does not implement the joystick overlay.
