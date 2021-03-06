; Script generated by the Inno Setup Script Wizard.
; SEE THE DOCUMENTATION FOR DETAILS ON CREATING INNO SETUP SCRIPT FILES!

#define MyAppTitle "MobileVideoConverter"
#define MyAppName "MobileVideoConverter"
#define MyAppVersion "1"
#define MyAppPublisher "https://github.com/yermak"
#define MyAppURL "https://github.com/yermak/MobileVideoConverter"
#define MyAppExeName "MobileVideoConverter-1.exe"
#define MyAppIcoName "mobilevideoconverter-1.ico"

[Setup]
; NOTE: The value of AppId uniquely identifies this application.
; Do not use the same AppId value in installers for other applications.
; (To generate a new GUID, click Tools | Generate GUID inside the IDE.)
AppId = {{E2A5A133-FF7D-4267-927F-FFFDC99F0BFF}
AppName = {#MyAppTitle}
AppVersion = {#MyAppVersion}
;AppVerName={#MyAppName} {#MyAppVersion}
AppPublisher = {#MyAppPublisher}
AppPublisherURL = {#MyAppURL}
AppSupportURL = {#MyAppURL}
AppUpdatesURL = {#MyAppURL}
SetupIconFile = C:\Users\Yermak\Projects\MobileVideoConverter\src\main\deploy\package\windows\{#MyAppIcoName}

PrivilegesRequired = lowest

InfoBeforeFile = C:\Users\Yermak\Projects\MobileVideoConverter\README.md

DefaultDirName = {commonappdata}\{#MyAppTitle}\{#MyAppName}-{#MyAppVersion}
DisableDirPage = No

DefaultGroupName = {#MyAppTitle}
DisableProgramGroupPage = No


OutputBaseFilename = {#MyAppName}-Installer-{#MyAppVersion}
Compression = lzma2/ultra64
SolidCompression = yes

DisableStartupPrompt = Yes
DisableReadyPage = No
DisableFinishedPage = Yes
DisableWelcomePage = Yes
AllowCancelDuringInstall = Yes
ArchitecturesInstallIn64BitMode = x64



[Languages]
Name : "english"; MessagesFile: "compiler:Default.isl"

[Tasks]
Name : "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked

[Files]
Source : "{#MyAppName}-{#MyAppVersion}\{#MyAppExeName}"; DestDir: "{app}"; Flags: ignoreversion
Source : "{#MyAppName}-{#MyAppVersion}\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs
Source : "C:\Users\Yermak\Projects\MobileVideoConverter\external\*"; DestDir: "{app}\app\external"; Flags: ignoreversion recursesubdirs createallsubdirs
; NOTE: Don't use "Flags: ignoreversion" on any shared system files

[Icons]
Name : "{group}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"
Name : "{commondesktop}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; IconFilename: "{app}\{#MyAppIcoName}"; Tasks: desktopicon

[Run]
Filename : "{app}\{#MyAppExeName}"; Description: "{cm:LaunchProgram,{#StringChange(MyAppName, '&', '&&')}}"; Flags: shellexec postinstall skipifsilent

