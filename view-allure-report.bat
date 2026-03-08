@echo off
REM ========================================
REM Script untuk view Allure Report
REM ========================================

echo ========================================
echo Opening Allure Report in Browser
echo ========================================

call mvn allure:serve

pause
