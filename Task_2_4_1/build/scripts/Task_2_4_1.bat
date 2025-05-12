@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%"=="" @echo off
@rem ##########################################################################
@rem
@rem  Task_2_4_1 startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
@rem This is normally unused
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and TASK_2_4_1_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if %ERRORLEVEL% equ 0 goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\Task_2_4_1-1.0-SNAPSHOT.jar;%APP_HOME%\lib\gradle-tooling-api-7.3-20210825160000+0000.jar;%APP_HOME%\lib\jsoup-1.17.2.jar;%APP_HOME%\lib\freemarker-2.3.32.jar;%APP_HOME%\lib\commons-io-2.16.1.jar;%APP_HOME%\lib\slf4j-simple-1.7.10.jar;%APP_HOME%\lib\slf4j-api-1.7.30.jar;%APP_HOME%\lib\groovy-cli-picocli-5.0.0-alpha-1.jar;%APP_HOME%\lib\groovy-groovysh-5.0.0-alpha-1.jar;%APP_HOME%\lib\groovy-console-5.0.0-alpha-1.jar;%APP_HOME%\lib\groovy-datetime-5.0.0-alpha-1.jar;%APP_HOME%\lib\groovy-groovydoc-5.0.0-alpha-1.jar;%APP_HOME%\lib\groovy-docgenerator-5.0.0-alpha-1.jar;%APP_HOME%\lib\groovy-jmx-5.0.0-alpha-1.jar;%APP_HOME%\lib\groovy-yaml-5.0.0-alpha-1.jar;%APP_HOME%\lib\groovy-json-5.0.0-alpha-1.jar;%APP_HOME%\lib\groovy-jsr223-5.0.0-alpha-1.jar;%APP_HOME%\lib\groovy-macro-5.0.0-alpha-1.jar;%APP_HOME%\lib\groovy-nio-5.0.0-alpha-1.jar;%APP_HOME%\lib\groovy-servlet-5.0.0-alpha-1.jar;%APP_HOME%\lib\groovy-sql-5.0.0-alpha-1.jar;%APP_HOME%\lib\groovy-swing-5.0.0-alpha-1.jar;%APP_HOME%\lib\groovy-templates-5.0.0-alpha-1.jar;%APP_HOME%\lib\groovy-test-5.0.0-alpha-1.jar;%APP_HOME%\lib\groovy-test-junit5-5.0.0-alpha-1.jar;%APP_HOME%\lib\groovy-xml-5.0.0-alpha-1.jar;%APP_HOME%\lib\groovy-ant-5.0.0-alpha-1.jar;%APP_HOME%\lib\groovy-5.0.0-alpha-1.jar;%APP_HOME%\lib\ant-junit-1.10.13.jar;%APP_HOME%\lib\ant-1.10.13.jar;%APP_HOME%\lib\ant-launcher-1.10.13.jar;%APP_HOME%\lib\ant-antlr-1.10.13.jar;%APP_HOME%\lib\picocli-4.7.4.jar;%APP_HOME%\lib\javaparser-core-3.25.4.jar;%APP_HOME%\lib\asm-util-9.5.jar;%APP_HOME%\lib\org.abego.treelayout.core-1.0.3.jar;%APP_HOME%\lib\ivy-2.5.1.jar;%APP_HOME%\lib\qdox-1.12.1.jar;%APP_HOME%\lib\jline-2.14.6.jar;%APP_HOME%\lib\junit-4.13.2.jar;%APP_HOME%\lib\junit-jupiter-engine-5.10.0.jar;%APP_HOME%\lib\junit-jupiter-api-5.10.0.jar;%APP_HOME%\lib\junit-platform-engine-1.10.0.jar;%APP_HOME%\lib\junit-platform-commons-1.10.0.jar;%APP_HOME%\lib\junit-platform-launcher-1.10.0.jar;%APP_HOME%\lib\jackson-databind-2.15.2.jar;%APP_HOME%\lib\jackson-core-2.15.2.jar;%APP_HOME%\lib\jackson-annotations-2.15.2.jar;%APP_HOME%\lib\jackson-dataformat-yaml-2.15.2.jar;%APP_HOME%\lib\asm-analysis-9.5.jar;%APP_HOME%\lib\asm-tree-9.5.jar;%APP_HOME%\lib\asm-9.5.jar;%APP_HOME%\lib\hamcrest-core-1.3.jar;%APP_HOME%\lib\opentest4j-1.3.0.jar;%APP_HOME%\lib\snakeyaml-2.0.jar


@rem Execute Task_2_4_1
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %TASK_2_4_1_OPTS%  -classpath "%CLASSPATH%" org.example.Main %*

:end
@rem End local scope for the variables with windows NT shell
if %ERRORLEVEL% equ 0 goto mainEnd

:fail
rem Set variable TASK_2_4_1_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
set EXIT_CODE=%ERRORLEVEL%
if %EXIT_CODE% equ 0 set EXIT_CODE=1
if not ""=="%TASK_2_4_1_EXIT_CONSOLE%" exit %EXIT_CODE%
exit /b %EXIT_CODE%

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
