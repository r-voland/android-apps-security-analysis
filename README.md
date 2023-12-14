# The Collection of snippets for Android Apps security analysis.

 ###  **1. JweDecoder.kt**
 
 This [Kotlin code](https://github.com/r-voland/android-apps-security-analysis/blob/main/JweDecoder.kt) will help you to **decrypt Google Play Intrgrity Service verdict** without using network Google API requests.
 Of cause, in this case you must have decryption and verification keys.

 > [!IMPORTANT]
 > Dependencies:
 >```
 > implementation("org.bitbucket.b_c:jose4j:0.9.4")
 > implementation("org.slf4j:slf4j-simple:2.0.9")
> ```

 ###  **2. apk_build_align_sign.sh** 
 
 This [Linux bash script](https://github.com/r-voland/android-apps-security-analysis/blob/main/apk_build_align_sign.sh) will help you to automatize build, align and sign procedures.

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

 ###  **3. RenameAppId.kt** 
 
 This [Kotlin code](https://github.com/r-voland/android-apps-security-analysis/blob/main/ReanameAppId.kt) will help you to rename App's packet id after APK file has been decompiled.
 
What this script do:
  > [!IMPORTANT]
>  
 > - change names of smali folders
>  - change paths in smali files
>  - change app packet id in AndroidManifest file
>  - change "renameManifestPackage:" section in apktool.yml file

 

 > [!CAUTION]
 >Four command line arguments are required in the following sequence :
 >```
 > 1. path of source work directory that contains original apk decompilation results
 > 2. original app packet id
> 3. new app packet id
> 4. path of destination work directory that will contains files with new app id
> ```
