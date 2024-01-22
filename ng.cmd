:: Created by npm, please don't edit manually.
@ECHO OFF

SETLOCAL

SET "NODE_EXE=%~dp0\node\node.exe"
IF NOT EXIST "%NODE_EXE%" (
  SET "NODE_EXE=node"
)

SET "NG_CLI_JS=%~dp0\node_modules/@angular/cli/bin/ng.js"
@REM FOR /F "delims=" %%F IN ('CALL "%NODE_EXE%" "%NPM_CLI_JS%" prefix -g') DO (
@REM   SET "NPM_PREFIX_NPM_CLI_JS=%%F\node\node_modules\npm\bin\npm-cli.js"
@REM )
@REM IF EXIST "%NPM_PREFIX_NPM_CLI_JS%" (
@REM   SET "NPM_CLI_JS=%NPM_PREFIX_NPM_CLI_JS%"
@REM )

echo NODE_EXE is set to: %NODE_EXE%
echo NG_CLI_JS is set to: %NG_CLI_JS%

"%NODE_EXE%" "%NG_CLI_JS%" %*
