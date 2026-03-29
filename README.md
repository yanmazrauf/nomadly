# Nomadly

A premium travel discovery Android app built with Jetpack Compose. Discover destinations through an immersive swipe-based interface inspired by modern design principles — warm cream tones, Noto Serif elegance, and fluid gesture interactions.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin 2.1.0 |
| UI Framework | Jetpack Compose (BOM 2024.12.01) |
| Navigation | Navigation Compose 2.8.5 |
| Image Loading | Coil 2.7.0 |
| Typography | Google Fonts via `ui-text-google-fonts` |
| Build System | Gradle 8.9 with Kotlin DSL |
| Min SDK | 26 (Android 8.0) |
| Target SDK | 35 (Android 15) |

---

## How to Run

1. Open Android Studio (Hedgehog or later recommended)
2. Select **Open** and navigate to `apps/android/`
3. Wait for Gradle sync to complete
4. Connect a device or start an emulator (API 26+)
5. Press **Run** (Shift + F10) or click the green play button

> Internet permission is declared in the manifest. The app loads destination images from `picsum.photos` — ensure your emulator/device has network access.

---

## Project Structure

```
apps/android/
├── app/
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/nomadly/app/
│       │   ├── MainActivity.kt
│       │   ├── data/mock/
│       │   │   └── MockRepository.kt          # 8 destinations, 5 boards, 1 user profile
│       │   ├── model/
│       │   │   ├── Destination.kt
│       │   │   ├── Board.kt
│       │   │   └── UserProfile.kt
│       │   ├── navigation/
│       │   │   ├── Screen.kt                  # Sealed class for all routes
│       │   │   └── NomadlyNavGraph.kt         # NavHost with all screens wired up
│       │   └── ui/
│       │       ├── theme/
│       │       │   ├── Color.kt               # Full design system palette
│       │       │   ├── Type.kt                # Google Fonts (Noto Serif + Manrope)
│       │       │   ├── Shape.kt               # 32dp / 48dp / pill radii
│       │       │   └── Theme.kt               # NomadlyTheme composable
│       │       ├── components/
│       │       │   ├── NomadlyTopBar.kt       # Frosted top bar with menu + bell
│       │       │   ├── NomadlyBottomNav.kt    # Custom pill-active bottom nav
│       │       │   ├── PrimaryButton.kt       # Rust gradient pill button
│       │       │   ├── GhostButton.kt         # Semi-transparent outlined button
│       │       │   ├── BoardCard.kt           # Full-bleed image board card
│       │       │   ├── StyleChip.kt           # Active/inactive tag chips
│       │       │   ├── StatCard.kt            # Profile stats (Saved/Boards/Visited)
│       │       │   └── SwipeCard.kt           # SwipeDeck + SwipeActionButtons
│       │       └── screens/
│       │           ├── OnboardingScreen.kt    # Full-screen hero + CTA
│       │           ├── HomeScreen.kt          # Swipe discovery interface
│       │           ├── DestinationDetailScreen.kt
│       │           ├── SavedBoardsScreen.kt
│       │           └── ProfileScreen.kt
│       └── res/
│           └── values/
│               ├── strings.xml
│               ├── themes.xml                 # NoActionBar XML theme
│               └── font_certs.xml             # Google Fonts provider certificates
├── gradle/
│   ├── libs.versions.toml                     # Version catalog
│   └── wrapper/gradle-wrapper.properties
├── build.gradle.kts                           # Project-level plugins
├── settings.gradle.kts
└── gradle.properties
```

---

## Design System

### Colors

| Token | Hex | Usage |
|---|---|---|
| Cream | `#FCF9F2` | App background |
| Surface | `#F6F3EC` | Cards, panels |
| Surface Alt | `#EBE8E1` | Inactive chips |
| Primary Text | `#1C1C18` | Headings, body |
| Secondary Text | `#3D4949` | Subtitles, labels |
| Brand Teal | `#006768` | Primary brand, icons |
| Accent Cyan | `#73F3EF` | Active chips, highlights |
| Rust Orange | `#A7330F` | CTA buttons, accents |
| Rust Light | `#C94B26` | Gradient end |
| Destructive | `#BA1A1A` | Log out, errors |

### Typography

- **Noto Serif** — Headings, display text, card titles (via Google Fonts)
- **Manrope** — UI labels, body text, buttons (via Google Fonts)

### Shape Language

- `48dp` — Large cards, hero sections
- `32dp` — Overlays, containers, info panels
- `999dp` — Buttons, chips, avatars (pill/circle)

### Swipe Deck

The SwipeDeck component (`SwipeCard.kt`) implements a Tinder-style card stack:
- Top 3 destinations shown at once with parallax scaling (1.0 / 0.95 / 0.90)
- Drag gesture with spring-back animation if below threshold (400f px)
- Smooth fly-off animation when swiped (300ms tween to ±2000px)
- Subtle rotation tied to horizontal drag offset
- Both drag-to-swipe and programmatic button triggers supported

---

## Mock Data

All data is served from `MockRepository.kt` (no network calls required for core UI):

- **8 destinations**: Amalfi Coast, Santorini, Kyoto, Maldives, Swiss Alps, Marrakech, Bali, Patagonia
- **5 boards**: Summer in Greece, Nordic Escapes, Winter in Maldives, Swiss Alps Trek, Tokyo & Kyoto
- **1 user profile**: Elena Moretti (Florence, Italy — 124 saved, 18 boards, 42 visited)
- Images from `picsum.photos` with named seeds for deterministic results
