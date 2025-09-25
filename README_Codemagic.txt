SkyNet - Codemagic optimized package

Changes made to optimize build on Codemagic:
- Ensured res directories have valid names.
- Added res/values/colors.xml and styles.xml with primary colors (black/white/red).
- Replaced hardcoded colors in layouts to use color resources.
- Added drawable/btn_primary.xml for rounded primary buttons.
- Added codemagic.yaml workflow to build APK via './gradlew clean assembleRelease'.

Build instructions (local):
1. Open project in Android Studio.
2. File > Sync Project with Gradle Files.
3. Build > Clean Project, then Rebuild Project.
4. To build release APK locally: ./gradlew clean assembleRelease

If you use Codemagic, upload this repository or connect to GitHub and use the 'build-android' workflow.
