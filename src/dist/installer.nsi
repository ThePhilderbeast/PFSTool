!define APPNAME "Pathfinder Scenario Tracker"
!define COMPANYNAME "Philderbeast"
!define DESCRIPTION "Provides a simple interfce to assist in even planning"
# These three must be integers
!define VERSIONMAJOR 0
!define VERSIONMINOR 1
!define VERSIONBUILD 1
# These will be displayed by the "Click here for support information" link in "Add/Remove Programs"
# It is possible to use "mailto:" links in here to open the email client
!define HELPURL "http://..." # "Support Information" link
!define UPDATEURL "http://..." # "Product Updates" link
!define ABOUTURL "http://..." # "Publisher" link
# This is the size (in kB) of all the files copied into "Program Files"
!define INSTALLSIZE 1680

RequestExecutionLevel admin ;Require admin rights on NT6+ (When UAC is turned on)

InstallDir "$PROGRAMFILES\${APPNAME}"

# This will be in the installer/uninstaller's title bar
Name "${APPNAME}"
outFile "PFS-installer.exe"


# Just three pages - license agreement, install location, and installation
page directory
Page instfiles

#!macro VerifyUserIsAdmin
#UserInfo::GetAccountType
#pop $0
#$if $0 != "admin" ;Require admin rights on NT4+
#        messageBox mb_iconstop "Administrator rights required!"
#        setErrorLevel 740 ;ERROR_ELEVATION_REQUIRED
#        quit
#$EndIf
#!macroend

function .onInit
	setShellVarContext all
#	!insertmacro VerifyUserIsAdmin
functionEnd

section "install"
	# Files for the install directory - to build the installer, these should be in the same directory as the install script (this file)
	setOutPath $INSTDIR
	# Files added here should be removed by the uninstaller (see section "uninstall")
	file "PathfinderSociety.jpeg"
    file "launcher.exe"

	setOutPath $INSTDIR\lib
    file "lib\cglib-nodep-2.1_3.jar"
    file "lib\commons-codec-1.10.jar"
	file "lib\commons-exec-1.3.jar"
	file "lib\commons-io-2.4.jar"
	file "lib\commons-lang3-3.4.jar"
	file "lib\commons-logging-1.2.jar"
	file "lib\cssparser-0.9.18.jar"
	file "lib\gson-2.3.1.jar"
	file "lib\htmlunit-2.21.jar"
	file "lib\htmlunit-core-js-2.17.jar"
	file "lib\htmlunit-driver-2.21.jar"
	file "lib\httpclient-4.5.2.jar"
	file "lib\httpcore-4.4.4.jar"
	file "lib\httpmime-4.5.2.jar"
	file "lib\jetty-io-9.2.15.v20160210.jar"
	file "lib\jetty-util-9.2.15.v20160210.jar"
	file "lib\jna-4.1.0.jar"
	file "lib\jna-platform-4.1.0.jar"
	file "lib\jsoup-1.7.2.jar"
	file "lib\neko-htmlunit-2.21.jar"
	file "lib\PFScode.jar"
	file "lib\sac-1.3.jar"
	file "lib\selenium-api-2.53.1.jar"
	file "lib\selenium-chrome-driver-2.53.1.jar"
	file "lib\selenium-remote-driver-2.53.1.jar"
	file "lib\serializer-2.7.2.jar"
	file "lib\websocket-api-9.2.15.v20160210.jar"
	file "lib\websocket-client-9.2.15.v20160210.jar"
	file "lib\websocket-common-9.2.15.v20160210.jar"
	file "lib\xalan-2.7.2.jar"
	file "lib\xercesImpl-2.11.0.jar"
	file "lib\xml-apis-1.4.01.jar"

	# Add any other files for the install directory (license files, app data, etc) here

	# Uninstaller - See function un.onInit and section "uninstall" for configuration
	writeUninstaller "$INSTDIR\uninstall.exe"

	# Start Menu
	createDirectory "$SMPROGRAMS\${COMPANYNAME}"
	createShortCut "$SMPROGRAMS\${COMPANYNAME}\${APPNAME}.lnk" "$INSTDIR\launch.exe" "" "$INSTDIR\logo.ico"

	# Registry information for add/remove programs
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "DisplayName" "${COMPANYNAME} - ${APPNAME} - ${DESCRIPTION}"
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "UninstallString" "$\"$INSTDIR\uninstall.exe$\""
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "QuietUninstallString" "$\"$INSTDIR\uninstall.exe$\" /S"
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "InstallLocation" "$\"$INSTDIR$\""
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "DisplayIcon" "$\"$INSTDIR\logo.ico$\""
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "Publisher" "$\"${COMPANYNAME}$\""
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "HelpLink" "$\"${HELPURL}$\""
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "URLUpdateInfo" "$\"${UPDATEURL}$\""
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "URLInfoAbout" "$\"${ABOUTURL}$\""
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "DisplayVersion" "$\"${VERSIONMAJOR}.${VERSIONMINOR}.${VERSIONBUILD}$\""
	WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "VersionMajor" ${VERSIONMAJOR}
	WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "VersionMinor" ${VERSIONMINOR}
	# There is no option for modifying or repairing the install
	WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "NoModify" 1
	WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "NoRepair" 1
	# Set the INSTALLSIZE constant (!defined at the top of this script) so Add/Remove Programs can accurately report the size
	WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}" "EstimatedSize" ${INSTALLSIZE}
sectionEnd

# Uninstaller

function un.onInit
	SetShellVarContext all

	#Verify the uninstaller - last chance to back out
	MessageBox MB_OKCANCEL "Permanantly remove ${APPNAME}?" IDOK next
		Abort
	next:
	#!insertmacro VerifyUserIsAdmin
functionEnd

section "uninstall"

	# Remove Start Menu launcher
	delete "$SMPROGRAMS\${COMPANYNAME}\${APPNAME}.lnk"
	# Try to remove the Start Menu folder - this will only happen if it is empty
	rmDir "$SMPROGRAMS\${COMPANYNAME}"

	# Remove files
	delete $INSTDIR\app.exe
	delete $INSTDIR\PathfinderSociety.jpeg
    delete $INSTDIR\launcher.exe
    delete $INSTDIR\lib\cglib-nodep-2.1_3.jar.jar
    delete $INSTDIR\commons-codec-1.10.jar
	delete $INSTDIR\lib\commons-exec-1.3.jar
	delete $INSTDIR\lib\commons-io-2.4.jar
	delete $INSTDIR\lib\commons-lang3-3.4.jar
	delete $INSTDIR\lib\commons-logging-1.2.jar
	delete $INSTDIR\lib\cssparser-0.9.18.jar
	delete $INSTDIR\lib\gson-2.3.1.jar
	delete $INSTDIR\lib\htmlunit-2.21.jar
	delete $INSTDIR\lib\htmlunit-core-js-2.17.jar
	delete $INSTDIR\lib\htmlunit-driver-2.21.jar
	delete $INSTDIR\lib\httpclient-4.5.2.jar
	delete $INSTDIR\lib\httpcore-4.4.4.jar
	delete $INSTDIR\lib\httpmime-4.5.2.jar
	delete $INSTDIR\lib\jetty-io-9.2.15.v20160210.jar
	delete $INSTDIR\lib\jetty-util-9.2.15.v20160210.jar
	delete $INSTDIR\lib\jna-4.1.0.jar
	delete $INSTDIR\lib\jna-platform-4.1.0.jar
	delete $INSTDIR\lib\jsoup-1.7.2.jar
	delete $INSTDIR\lib\neko-htmlunit-2.21.jar
	delete $INSTDIR\lib\sac-1.3.jar
	delete $INSTDIR\lib\selenium-api-2.53.1.jar
	delete $INSTDIR\lib\selenium-chrome-driver-2.53.1.jar
	delete $INSTDIR\lib\selenium-remote-driver-2.53.1.jar
	delete $INSTDIR\lib\serializer-2.7.2.jar
	delete $INSTDIR\lib\websocket-api-9.2.15.v20160210.jar
	delete $INSTDIR\lib\websocket-client-9.2.15.v20160210.jar
	delete $INSTDIR\lib\websocket-common-9.2.15.v20160210.jar
	delete $INSTDIR\lib\xalan-2.7.2.jar
	delete $INSTDIR\lib\xercesImpl-2.11.0.jar
	delete $INSTDIR\lib\xml-apis-1.4.01.jar

	# Always delete uninstaller as the last action
	delete $INSTDIR\uninstall.exe

	# Try to remove the install directory - this will only happen if it is empty
	rmDir $INSTDIR

	# Remove uninstaller information from the registry
	DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${COMPANYNAME} ${APPNAME}"
sectionEnd
