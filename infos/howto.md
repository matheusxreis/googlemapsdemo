To generate a debug certificate fingerprint, you can do:
 ```bash
 ./gradlew signingReport
 ```

To generate a release certificate fingerprint, you can do, after generate 
a release of the app(AndroidStudio>Build>Generate Signed Bundle/APK...):
```bash 
keytool -list -v -alias <your-key-name> -keystore <path-to-production-keystore>
```