# === CONFIG ===
GRADLEW=./gradlew

# clear terminal
clear:
	clear

# clear build
cleanBuild: clear
	$(GRADLEW) clean

# clear Build manually
manualClean: cleanBuild
	@echo "Clean build manually"
	rm -rf .idea
	rm -rf .gradle
	rm -rf build
	rm -rf */build
	rm -rf iosApp/iosApp.xcworkspace
	rm -rf iosApp/Pods
	rm -rf iosApp/iosApp.xcodeproj/project.xcworkspace
	rm -rf iosApp/iosApp.xcodeproj/xcuserdata
	rm -rf kotlin-js-store
	@echo "✅ Done!"

# List gradle task
taskList: cleanBuild
	@echo "Task List"
	$(GRADLEW) task
	@echo "✅ Done!"

# Generate compose stability report
reportCompose: manualClean
	@echo "Compose Compiler Report"
	$(GRADLEW) assembleDebug -PenableComposeCompilerMetrics=true -PenableComposeCompilerReports=true
	@echo "✅ Done!"

# Apply spotless
spotless: manualClean
	@echo "Apply spotless"
	$(GRADLEW) spotlessApply
	@echo "✅ Done!"

# Run Android build
buildAndroid: cleanBuild
	@echo "Android build"
	$(GRADLEW) androidApp:app:installDebug
	@echo "✅ Done!"

# Run IOS build
buildIOS: cleanBuild
	@echo "IOS build"
	chmod +x ./scripts/ios_script.sh
	./scripts/ios_script.sh
	@echo "✅ Done!"

# Run Desktop build
buildDesktop: cleanBuild
	@echo "Desktop build"
	$(GRADLEW) :desktopapp:run
	@echo "✅ Done!"

# Run Desktop hot reload build
buildHotDesktop: clear
	@echo "Desktop Hot reload build"
	$(GRADLEW) :desktopapp:hotRunJvm --auto
	@echo "✅ Done!"

# Run wasm build
buildWasmWeb: cleanBuild
	@echo "Web Wasm build"
	$(GRADLEW) webApp:wasm:wasmJsBrowserDevelopmentRun
	@echo "✅ Done!"

# Run js build
buildJsWeb: cleanBuild
	@echo "Web JS build"
	$(GRADLEW) webApp:js:jsBrowserDevelopmentRun
	@echo "✅ Done!"

# Run wasm UI test
testUiJsWeb: cleanBuild
	@echo "Web Js UI test"
	$(GRADLEW) iosApp:iosSimulatorArm64Test
	@echo "✅ Done!"

# Run Shared Unit test
testUnit: cleanBuild
	@echo "Shared Unit test"
	$(GRADLEW) sharedApp:cleanTestDebugUnitTest :sharedApp:testDebugUnitTest
	@echo "✅ Done!"

# Run Desktop Ui test
testUiDesktop: cleanBuild
	@echo "Desktop UI test"
	$(GRADLEW) :desktopapp:jvmTest
	@echo "✅ Done!"

# Run Android Ui test
testUiAndroid: cleanBuild
	@echo "Android UI test"
	$(GRADLEW) androidApp:app:connectedDebugAndroidTest
	@echo "✅ Done!"


# Run wasm UI test
testUiJsWeb: cleanBuild
	@echo "Web Js UI test"
	$(GRADLEW) iosApp:iosSimulatorArm64Test
	@echo "✅ Done!"

# Run JS UI test
testUiJsWeb: cleanBuild
	@echo "Web Js UI test"
	$(GRADLEW) webApp:wasm:jsBrowserTest
	@echo "✅ Done!"

# Run wasm UI test
testUiWasmWeb: cleanBuild
	@echo "Web wasm UI test"
	$(GRADLEW) webApp:wasm:wasmJsBrowserTest
	@echo "✅ Done!"
