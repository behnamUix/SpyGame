<div dir="rtl" align="center">🎮 Spy Manager

بازی گروهی جاسوسی و معمایی برای دورهمی‌ها

یک بازی گروهی سرگرم‌کننده که بازیکنان در آن نقش‌های مختلفی دریافت می‌کنند و باید با استفاده از سرنخ‌ها، گفتگو و دقت، جاسوس را شناسایی کنند.

<br>🚧 پروژه در حال توسعه است

</div>---

📱 Screenshots

<p align="center">
  <img src="home.png" width="220" alt="Spy Manager Home Screen">
  &nbsp;&nbsp;&nbsp;&nbsp;
  <img src="login.png" width="220" alt="Spy Manager Login Screen">
</p><p align="center">
  <sub>تصاویر مربوط به نسخه فعلی برنامه هستند.</sub>
</p>---

🎯 درباره پروژه

Spy Manager یک بازی گروهی برای Android است که با تمرکز بر تجربه‌ای ساده، سریع و سرگرم‌کننده برای بازی‌های چندنفره طراحی شده است.

هدف اصلی پروژه علاوه بر ساخت یک بازی قابل استفاده، ایجاد یک نمونه واقعی از توسعه یک اپلیکیشن Android با معماری مدرن، کد قابل نگهداری و ساختار مقیاس‌پذیر است.

در حال حاضر پروژه در حال بازطراحی معماری و انتقال تدریجی به Clean Architecture است.

---

✨ قابلیت‌ها

- 🕵️ سیستم نقش و جاسوس
- 👥 پشتیبانی از بازی گروهی
- 🎭 مدیریت نقش بازیکنان
- 🎯 مکانیزم شناسایی جاسوس
- 🎨 رابط کاربری مدرن و فارسی
- 🌙 طراحی مبتنی بر Material 3
- ⚡ تمرکز بر عملکرد و زمان شروع برنامه
- 🏗️ معماری قابل توسعه و نگهداری

---

🏗️ Architecture

پروژه در حال انتقال از ساختار فعلی به Clean Architecture است.

هدف این بازطراحی، جداسازی مسئولیت‌ها و ایجاد وابستگی‌های کنترل‌شده بین لایه‌های مختلف برنامه است.

ساختار موردنظر پروژه:

app/
│
├── data/
│   ├── local/
│   ├── remote/
│   ├── repository/
│   └── mapper/
│
├── domain/
│   ├── model/
│   ├── repository/
│   └── usecase/
│
└── presentation/
    ├── screen/
    ├── component/
    ├── navigation/
    └── state/

اهداف معماری

- جداسازی مسئولیت‌ها
- کاهش وابستگی بین لایه‌ها
- افزایش قابلیت تست
- ساده‌تر شدن نگهداری کد
- امکان توسعه قابلیت‌های جدید
- افزایش مقیاس‌پذیری پروژه

«ساختار معماری در حال توسعه است و ممکن است در ادامه پروژه تغییر کند.»

---

🛠️ Tech Stack

Android

- Kotlin
- Android SDK
- Jetpack Compose
- Material 3

Architecture & Development

- Clean Architecture
- MVVM / MVI
- Dependency Injection
- Coroutines
- Flow

هدف پروژه

تمرکز پروژه روی استفاده از اصول مدرن توسعه Android و ایجاد کدی است که در طول زمان قابل توسعه و نگهداری باقی بماند.

---

⚡ Performance

یکی از بخش‌های مهم توسعه Spy Manager، بهینه‌سازی زمان شروع برنامه و بهبود تجربه کاربر در اولین اجرای اپلیکیشن است.

Startup Benchmark

Configuration| Average Startup
❌ Without Baseline Profile| 1,231 ms
✅ With Baseline Profile| در حال اندازه‌گیری

Benchmarkها در شرایط یکسان اندازه‌گیری و در ادامه پروژه تکمیل خواهند شد.

---

🚧 Development Status

بخش| وضعیت
UI / UX| 🟢 در حال توسعه
Game Logic| 🟢 در حال توسعه
Clean Architecture| 🟡 در حال مهاجرت
Dependency Injection| 🟢 پیاده‌سازی شده
Performance| 🟡 در حال بهینه‌سازی
Unit Tests| 🟡 در حال توسعه
Documentation| 🟡 در حال تکمیل

---

🗺️ Roadmap

- [x] ایجاد نسخه اولیه بازی
- [x] طراحی رابط کاربری اولیه
- [x] پیاده‌سازی بخش‌های اصلی بازی
- [ ] تکمیل مهاجرت به Clean Architecture
- [ ] تکمیل Unit Tests
- [ ] اضافه کردن Baseline Profile
- [ ] بهینه‌سازی Startup Performance
- [ ] بهبود UI / UX
- [ ] تکمیل سیستم بازی
- [ ] انتشار نسخه پایدار

---

📂 Project Structure

ساختار پروژه به صورت تدریجی و همزمان با مهاجرت معماری بازطراحی می‌شود.

هدف نهایی، تفکیک واضح بخش‌های:

Presentation → Domain → Data

است تا هر بخش مسئولیت مشخصی داشته باشد و تغییرات یک لایه، کمترین تأثیر را روی سایر بخش‌ها ایجاد کند.

---

👨‍💻 Developer

<div dir="center">Ali Mahjoob

Android Developer

Kotlin · Jetpack Compose · Clean Architecture

</div>---

<div dir="center">⭐ اگر پروژه برایتان جالب بود، خوشحال می‌شوم با یک Star از آن حمایت کنید.

</div>
