UiautomatorDemo

How to build UiautomatorDemo
1. gradle clean build
2. adb devices
3. ./gradlew connectedDebugAndroidTest


Troubleshooting

1. Run "adb devices", and get this message "adb server version (32) doesn't match this client (39); killing..."
Solution: $ adb reconnect

2. Run "./gradlew connectedDebugAndroidTest", and get this message "WARNING: Conflict with dependency 'com.android.support:support-annotations' in project ':app'. Resolved versions for app (26.0.0-alpha1) and test app (23.1.1) differ. See http://g.co/androidstudio/app-test-app-conflict for details."
Solution: 
compileSdkVersion 25
compile 'com.android.support:appcompat-v7:25.3.1'
androidTestCompile 'com.android.support:support-annotations:25.3.1'

3. Run "./gradlew connectedDebugAndroidTest" and get this message "Skipping device 'P024 - 6.0.1' for 'app:': minSdkVersion [25] > deviceApiLevel [23]"
