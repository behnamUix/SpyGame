🕵️ Spy Manager

🎮 بازی گروهی جاسوسی و معمایی برای دورهمی‌ها

یک بازی گروهی سرگرم‌کننده که بازیکنان در آن نقش‌های مختلفی دریافت می‌کنند و با استفاده از سرنخ، گفتگو و دقت تلاش می‌کنند جاسوس را شناسایی کنند.

«🚧 وضعیت پروژه: در حال توسعه»

---

📱 Screenshots 

<p align="center">
  <img src=""https://your-link.com/screenshot1.png" (https://your-link.com/screenshot1.png)" width="250"/>
  <img src=""https://your-link.com/screenshot2.png" (https://your-link.com/screenshot2.png)" width="250"/>
  <img src=""https://your-link.com/screenshot3.png" (https://your-link.com/screenshot3.png)" width="250"/>
</p>

---

🎯 درباره پروژه

Spy Manager یک بازی گروهی برای Android است که با هدف ایجاد تجربه‌ای ساده، سریع و سرگرم‌کننده برای دورهمی‌ها توسعه داده می‌شود.

این پروژه علاوه بر جنبه محصولی، بستری برای پیاده‌سازی اصول مدرن توسعه Android، معماری نرم‌افزار و بهینه‌سازی عملکرد است.

ساختار داخلی پروژه در حال حاضر در حال مهاجرت تدریجی به Clean Architecture است.

اهداف پروژه

- معماری تمیز و قابل توسعه
- جداسازی صحیح مسئولیت‌ها
- افزایش تست‌پذیری
- بهبود قابلیت نگهداری کد
- بهینه‌سازی عملکرد
- تجربه کاربری ساده و روان

---

✨ قابلیت‌ها

- 🕵️ سیستم نقش و جاسوس
- 👥 پشتیبانی از بازی گروهی
- 🎭 مدیریت نقش بازیکنان
- 🎯 مکانیزم شناسایی جاسوس
- 🎨 رابط کاربری مدرن و فارسی
- 🌙 طراحی مبتنی بر Material 3
- ⚡ بهینه‌سازی Startup و Performance
- 🧩 معماری قابل توسعه

---

🛠️ Tech Stack

Android

فناوری| کاربرد
Kotlin| زبان اصلی توسعه
Android SDK| پلتفرم توسعه
Jetpack Compose| ساخت رابط کاربری
Material 3| طراحی رابط کاربری
Navigation Compose| مدیریت Navigation
Coroutines| پردازش‌های Asynchronous
Flow| مدیریت جریان داده

Architecture & Design

فناوری / الگو| کاربرد
Clean Architecture| جداسازی لایه‌های برنامه
MVVM / MVI| مدیریت State و منطق UI
Repository Pattern| مدیریت دسترسی به داده
Use Case| جداسازی منطق کسب‌وکار
Dependency Injection| مدیریت وابستگی‌ها
Hilt| پیاده‌سازی Dependency Injection

Data & Networking

فناوری| کاربرد
Room| ذخیره‌سازی داده‌های محلی
Retrofit| ارتباط با REST API
OkHttp| مدیریت HTTP Requests
Kotlin Serialization| تبدیل داده‌ها

Development Tools

- Android Studio
- Gradle
- KSP
- Git
- GitHub
- GitHub Actions

---

🏛️ Architecture

پروژه در حال مهاجرت به Clean Architecture است.

ساختار معماری بر اساس سه لایه اصلی طراحی می‌شود:

Presentation
     ↓
  Domain
     ↓
   Data

Presentation

مسئول رابط کاربری، مدیریت State، دریافت Eventها و نمایش وضعیت برنامه.

Domain

هسته منطق برنامه که شامل Modelها، Use Caseها و قرارداد Repositoryها است.

Data

مسئول ارتباط با منابع داده مانند Database و API و تبدیل داده‌ها.

Project Structure

app/
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

«ساختار پروژه همزمان با فرآیند مهاجرت به Clean Architecture در حال تکمیل است.»

---

⚡ Performance

یکی از اهداف پروژه، کاهش زمان Startup و بهبود عملکرد اولیه برنامه است.

Startup Benchmark

حالت اجرا| میانگین زمان شروع
❌ بدون Baseline Profile| 1,231 ms
🚧 با Baseline Profile| در حال اندازه‌گیری

«Benchmarkها در شرایط یکسان اندازه‌گیری می‌شوند و نتایج نهایی پس از تکمیل بهینه‌سازی ثبت خواهند شد.»

---

🚧 Development Status

بخش| وضعیت
🎨 UI / UX| 🟢 در حال توسعه
🎮 Game Logic| 🟢 در حال توسعه
🏗️ Clean Architecture| 🟡 در حال مهاجرت
💉 Dependency Injection| 🟢 پیاده‌سازی شده
⚡ Performance| 🟡 در حال بهینه‌سازی
🧪 Unit Tests| 🟡 در حال توسعه
📚 Documentation| 🟡 در حال تکمیل

---

🗺️ Roadmap

- [x] ایجاد نسخه اولیه بازی
- [x] طراحی رابط کاربری اولیه
- [x] پیاده‌سازی قابلیت‌های اصلی
- [ ] تکمیل مهاجرت به Clean Architecture
- [ ] تکمیل لایه Domain
- [ ] افزایش پوشش Unit Tests
- [ ] اضافه کردن Baseline Profile
- [ ] بهینه‌سازی Startup Performance
- [ ] بهبود UI / UX
- [ ] تکمیل سیستم بازی
- [ ] انتشار نسخه پایدار

---

👨‍💻 Developer

Ali Mahjoob

Android Developer

"Kotlin" · "Jetpack Compose" · "Clean Architecture"

---

⭐ اگر پروژه برایتان جالب بود، با یک Star از آن حمایت کنید.
