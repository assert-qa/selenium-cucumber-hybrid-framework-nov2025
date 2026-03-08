@echo off
REM ========================================
REM Script untuk menjalankan semua test
REM ========================================

echo ========================================
echo Running All Cucumber Tests
echo ========================================

REM Clean dan compile project
echo [1/3] Cleaning and compiling project...
call mvn clean compile

REM Run all tests
echo.
echo [2/3] Running all tests...
call mvn test

REM Generate Allure Report (optional)
echo.
echo [3/3] Generating Allure Report...
call mvn allure:report

echo.
echo ========================================
echo Test Execution Completed!
echo ========================================
echo.
echo Report Locations:
echo - Extent Report: exports\ExtentReport\SparkReport.html
echo - Allure Report: Run 'mvn allure:serve' to view
echo - Cucumber HTML: target\cucumber-reports\*.html
echo ========================================

pause
