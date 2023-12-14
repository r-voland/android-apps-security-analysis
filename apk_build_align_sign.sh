#!/bin/bash
### Configuration can be changed 
### Constants:
confJavaJdkPath=/usr/lib/jvm/jdk1.8.0_361/bin/
confApkToolPath=/usr/bin/apktool
confKeystorePath=/home/user/Keystore/androidpersonal.keystore
confKeyParameters="--ks-pass pass:qWERTY123456 --v1-signing-enabled true --v2-signing-enabled"
confZipAlignPath=/home/user/Android/Sdk/build-tools/32.0.0/zipalign
confApkSignerPath=/home/user/Apksigner/apksigner
### Variables:
confWorkDir=/home/user/WorkDir/
confAppName=base
confApkName=base.apk

### Work area/ No Change code below
appBasePath=${confWorkDir}${confAppName}/
apkTmpPath=${appBasePath}dist/${confApkName}
apkOutPath=${confWorkDir}${confApkName}
idSigPath=${confWorkDir}${confApkName}.idsig

echo "Configuring Java..."
export PATH=$PATH:$confJavaJdkPath

echo "Clear cache..."
$confApkToolPath empty-framework-dir

echo "Deleting old files..."
rm ${apkOutPath}
rm ${apkTmpPath}
rm ${idSigPath}

echo "Building apk..."
${confApkToolPath} b $confAppName

echo "Aligning APK..."
$confZipAlignPath -p -f -v 4 ${apkTmpPath} ${apkOutPath}

echo "Signing APK..."
$confApkSignerPath sign -ks $confKeystorePath $confKeyParameters true  $apkOutPath

echo "Done!"
