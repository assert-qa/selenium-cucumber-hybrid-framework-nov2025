# 📊 Panduan Extent Report & Allure Report

## 🎯 Ringkasan Penggunaan Konstanta

### **1. EXTENT_REPORT_PATH**
- **Lokasi**: `src/main/resources/config.properties`
- **Nilai**: `exports/ExtentReport/ExtentReport.html`
- **Digunakan di**: `ExtentReportManager.java`
- **Fungsi**: Menentukan lokasi file Extent Report HTML

### **2. SCREENSHOT_PATH**
- **Lokasi**: `src/main/resources/config.properties`
- **Nilai**: `./exports/screenshots/`
- **Digunakan di**: `CaptureHelper.java`
- **Fungsi**: Menentukan lokasi penyimpanan screenshot

### **3. RECORD_VIDEO_PATH**
- **Lokasi**: `src/main/resources/config.properties`
- **Nilai**: `./exports/videos/`
- **Digunakan di**: `CaptureHelper.startRecord()`
- **Fungsi**: Menentukan lokasi penyimpanan video recording

### **4. RECORD_VIDEO**
- **Lokasi**: `src/main/resources/config.properties`
- **Nilai**: `yes` atau `no`
- **Digunakan di**: Dapat digunakan di hooks untuk enable/disable recording
- **Fungsi**: Toggle video recording

### **5. SCREENSHOT_FAIL**
- **Lokasi**: `src/main/resources/config.properties`
- **Nilai**: `yes` atau `no`
- **Digunakan di**: `CucumberHooks.afterStep()` (untuk failed steps)
- **Fungsi**: Toggle screenshot saat step gagal

### **6. SCREENSHOT_PASS**
- **Lokasi**: `src/main/resources/config.properties`
- **Nilai**: `yes` atau `no`
- **Digunakan di**: Dapat ditambahkan di hooks untuk passed steps
- **Fungsi**: Toggle screenshot saat step berhasil

### **7. SCREENSHOT_STEP_ALL**
- **Lokasi**: `src/main/resources/config.properties`
- **Nilai**: `yes` atau `no`
- **Digunakan di**: `WebUI.java` (berbagai method seperti `openURL`, `clickElement`, dll)
- **Fungsi**: Toggle screenshot untuk setiap step

### **8. AUTHOR**
- **Lokasi**: `src/main/resources/config.properties`
- **Nilai**: `Injas Mahendra Berutu`
- **Digunakan di**: `ExtentReportManager.java`
- **Fungsi**: Menampilkan nama author di report metadata

---

## 📁 Lokasi Report

### **Extent Report**
- **Output**: `exports/ExtentReport/`
- **File**: 
  - `ExtentReport.html` (dari `ExtentReportManager`)
  - `SparkReport.html` (dari Cucumber Adapter)
- **Cara Buka**: Double click file `.html` di folder tersebut

### **Allure Report**
- **Output**: `target/allure-results/` (raw data)
- **Generated**: `target/allure-report/` (setelah generate)
- **Cara Buka**: 
  ```bash
  mvn allure:serve
  ```
  atau double click `view-allure-report.bat`

### **Cucumber HTML Report**
- **Output**: `target/cucumber-reports/`
- **File**: `*.html` per runner
- **Cara Buka**: Double click file `.html` di folder tersebut

---

## 🚀 Cara Menjalankan Test

### **Metode 1: Menggunakan Script .bat**
```bash
# Jalankan semua test
run-all-tests.bat

# View Allure Report
view-allure-report.bat
```

### **Metode 2: Maven Command**
```bash
# Run semua test (via testng.xml)
mvn clean test

# Run specific runner
mvn clean test -Dtest=TestRunnerProductDetailPage

# Generate Allure Report
mvn allure:report

# Serve Allure Report (open in browser)
mvn allure:serve
```

### **Metode 3: IntelliJ IDEA**
1. Right-click pada `testng.xml` → Run
2. Right-click pada specific Runner class → Run
3. Klik icon Run di gutter line

---

## 🔧 Troubleshooting

### ❌ **Problem 1: Extent Report tidak muncul**

**Penyebab:**
- Plugin Cucumber Extent Adapter tidak dikonfigurasi dengan benar
- Path `extent.properties` tidak ditemukan
- Report tidak di-flush setelah test selesai

**Solusi:**
1. Pastikan dependency ada di `pom.xml`:
   ```xml
   <dependency>
       <groupId>tech.grasshopper</groupId>
       <artifactId>extentreports-cucumber7-adapter</artifactId>
       <version>1.14.0</version>
   </dependency>
   ```

2. Pastikan plugin di Runner:
   ```java
   @CucumberOptions(
       plugin = {
           "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
       }
   )
   ```

3. Pastikan `extent.properties` ada di `src/main/resources/`

4. Pastikan `CucumberReportListener` melakukan flush:
   ```java
   ExtentTestManager.getExtentReports().flush();
   ```

---

### ❌ **Problem 2: Allure Report tidak generate**

**Penyebab:**
- AspectJ Weaver tidak terpasang
- Plugin Allure tidak di Runner
- Allure CLI tidak terinstall

**Solusi:**
1. Tambahkan AspectJ di `pom.xml`:
   ```xml
   <dependency>
       <groupId>org.aspectj</groupId>
       <artifactId>aspectjweaver</artifactId>
       <version>1.9.22.1</version>
   </dependency>
   ```

2. Konfigurasi Maven Surefire Plugin:
   ```xml
   <argLine>
       -javaagent:"${settings.localRepository}/org/aspectj/aspectjweaver/1.9.22.1/aspectjweaver-1.9.22.1.jar"
   </argLine>
   ```

3. Tambahkan plugin di Runner:
   ```java
   @CucumberOptions(
       plugin = {
           "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
       }
   )
   ```

4. Install Allure CLI (optional):
   ```bash
   npm install -g allure-commandline
   ```

---

### ❌ **Problem 3: Screenshot tidak tersimpan**

**Penyebab:**
- Folder tidak dibuat otomatis
- Path salah di `config.properties`
- Driver belum diinisialisasi

**Solusi:**
1. Pastikan path benar di `config.properties`:
   ```properties
   SCREENSHOT_PATH=./exports/screenshots/
   ```

2. Pastikan `CaptureHelper` menggunakan `ConstantGlobal.SCREENSHOT_PATH`

3. Enable screenshot di `config.properties`:
   ```properties
   SCREENSHOT_STEP_ALL=yes
   ```

---

### ❌ **Problem 4: Report kosong / tidak ada data**

**Penyebab:**
- Test tidak dijalankan dengan benar
- Listener tidak terdaftar
- ExtentTest tidak dibuat

**Solusi:**
1. Pastikan `CucumberHooks.beforeScenario()` membuat ExtentTest:
   ```java
   ExtentTestManager.createTest(scenario.getName());
   ```

2. Pastikan listener di `testng.xml`:
   ```xml
   <listeners>
       <listener class-name="io.qameta.allure.testng.AllureTestNg"/>
   </listeners>
   ```

3. Pastikan Runner di `testng.xml`:
   ```xml
   <test name="Test Name">
       <classes>
           <class name="runner.TestRunnerClassName"/>
       </classes>
   </test>
   ```

---

## 📊 Struktur Folder Report

```
cucumber_hybrid_framework/
├── exports/
│   ├── ExtentReport/
│   │   ├── ExtentReport.html      ← Custom Extent Report
│   │   └── SparkReport.html       ← Cucumber Adapter Report
│   ├── screenshots/               ← Screenshot dari steps
│   └── videos/                    ← Video recording
├── target/
│   ├── allure-results/            ← Allure raw data
│   ├── allure-report/             ← Generated Allure HTML
│   └── cucumber-reports/          ← Cucumber HTML reports
└── logs/                          ← Log4j2 logs
```

---

## 🎓 Best Practices

### **1. Gunakan Konstanta**
❌ **Salah:**
```java
String path = "exports/screenshots/";
```

✅ **Benar:**
```java
String path = ConstantGlobal.SCREENSHOT_PATH;
```

### **2. Handle Null ExtentTest**
❌ **Salah:**
```java
ExtentTestManager.getTest().pass("Test passed");
```

✅ **Benar:**
```java
if (ExtentTestManager.getTest() != null) {
    ExtentTestManager.getTest().pass("Test passed");
}
```

### **3. Flush Report Setelah Test**
✅ **Tambahkan di TestRunFinished:**
```java
ExtentTestManager.getExtentReports().flush();
```

### **4. Screenshot Conditional**
✅ **Gunakan flag dari config.properties:**
```java
if (ConstantGlobal.SCREENSHOT_STEP.equals("yes")) {
    CaptureHelper.takeScreenshot("screenshot_name");
}
```

---

## 📝 Checklist Setup Report

- [x] `testng.xml` dibuat di root project
- [x] `extent.properties` ada di `src/main/resources/`
- [x] `extent-config.xml` ada di `src/main/resources/`
- [x] Plugin Extent Adapter di Runner `@CucumberOptions`
- [x] Plugin Allure di Runner `@CucumberOptions`
- [x] Listener Allure di `testng.xml`
- [x] `ExtentTestManager.createTest()` di `@Before` hook
- [x] `ExtentReports.flush()` di TestRunFinished
- [x] AspectJ Weaver di `pom.xml`
- [x] Maven Surefire Plugin configured
- [x] Konstanta digunakan untuk paths

---

## 🎉 Kesimpulan

Sekarang Anda memiliki:
1. ✅ **Extent Report** yang tersimpan di `exports/ExtentReport/`
2. ✅ **Allure Report** yang dapat di-generate dengan `mvn allure:serve`
3. ✅ **Screenshot** yang tersimpan di `exports/screenshots/`
4. ✅ **Video Recording** (optional) di `exports/videos/`
5. ✅ **Script .bat** untuk menjalankan test dengan mudah
6. ✅ **Konstanta terpusat** untuk konfigurasi

**Jalankan test Anda sekarang dengan:**
```bash
run-all-tests.bat
```

atau

```bash
mvn clean test
```

Report akan otomatis ter-generate! 🚀
