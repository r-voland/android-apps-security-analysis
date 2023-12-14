# The Collection of snippets for Android Apps security analysis.

 ###  **1. JweDecoder.kt**
 
 This [code](https://github.com/r-voland/android-apps-security-analysis/blob/main/JweDecoder.kt) will help you to **decrypt Google Play Intrgrity Service verdict** without using network Google API requests.
 Of cause, in this case you must have decryption and verification keys.

 > [!IMPORTANT]
 > Dependencies:
 >```
 > implementation("org.bitbucket.b_c:jose4j:0.9.4")
 > implementation("org.slf4j:slf4j-simple:2.0.9")
> ```
 ###  **2. apk_build_align_sign.sh**
 
 This [script](https://github.com/r-voland/android-apps-security-analysis/blob/main/apk_build_align_sign.sh) will help you to automatize build, align and sign procedures.

 > [!NOTE]
 > In-Script variables:
 >```
 > confJavaJdkPath - Java JDK Path
 > confApkToolPath - path of apktool executable file
> confKeystorePath - path of Keystore file
> confKeyParameters - key parameters
> confZipAlignPath - path of zipalign executable file
> confApkSignerPath - path of apksigner executable file
> confWorkDir - work directory contains folder with apk decompilation results
> confAppName - name of folder with apk decompilation results
> confApkName - name of apk file, that will be giving in result of this script work
> ```
