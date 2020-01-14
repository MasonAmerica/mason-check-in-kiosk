# Keep line numbers and file name obfuscation
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable
-printconfiguration build/outputs/mapping/configuration.txt
-dontobfuscate

# For screen analytics
-keepnames class * extends support.bymason.kiosk.checkin.core.ui.FragmentBase
-keepnames class * extends support.bymason.kiosk.checkin.core.ui.DialogFragmentBase

# 🤷‍♂️
-dontwarn org.apache.commons.**
-dontnote kotlin.**
-dontnote com.google.**

# Remove logging
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}
