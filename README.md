<div dir="rtl" align="center">🎮 Spy Manager Game

یک بازی گروهی معمایی و هیجان‌انگیز برای دورهمی‌ها

Spy Manager یک بازی گروهی مبتنی بر نقش‌آفرینی، حدس و تعامل بین بازیکنان است که با هدف ایجاد تجربه‌ای ساده، سریع و سرگرم‌کننده برای جمع‌های دوستانه طراحی شده است.

«🚧 پروژه در حال توسعه و بازطراحی ساختار داخلی با Clean Architecture است.»

---

📱 پیش‌نمایش

<p align="center">
  <img src="home.png" width="220" alt="Spy Manager Home Screen" />
  &nbsp;&nbsp;&nbsp;
  <img src="login.png" width="220" alt="Spy Manager Login Screen" />
</p><p align="center">
  <i>تصاویر مربوط به نسخه فعلی پروژه هستند و رابط کاربری در حال توسعه است.</i>
</p>---

✨ ویژگی‌ها

- 🎭 بازی گروهی مبتنی بر نقش‌ها
- 👥 مناسب برای بازی چندنفره در دورهمی‌ها
- 🎯 سیستم انتخاب و مدیریت نقش‌ها
- 🕵️ مکانیزم تشخیص و حدس جاسوس
- 🎨 رابط کاربری مدرن و فارسی
- 🌙 طراحی شده با Material 3
- ⚡ تمرکز بر عملکرد و سرعت اجرای برنامه
- 🏗️ در حال مهاجرت به Clean Architecture

---

🏗️ معماری پروژه

ساختار پروژه در حال بازطراحی و انتقال به Clean Architecture است.

هدف از این بازطراحی:

- جداسازی مسئولیت‌ها
- افزایش تست‌پذیری
- کاهش وابستگی بین بخش‌های مختلف
- نگهداری و توسعه آسان‌تر پروژه
- ایجاد ساختاری مقیاس‌پذیر برای قابلیت‌های آینده

ساختار هدف پروژه:

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

«این ساختار در حال پیاده‌سازی است و ممکن است در طول توسعه تغییر کند.»

---

🛠️ تکنولوژی‌ها

تکنولوژی| استفاده
Kotlin| زبان اصلی توسعه
Jetpack Compose| طراحی رابط کاربری
Material 3| طراحی UI
Coroutines| پردازش‌های asynchronous
Flow| مدیریت جریان داده
Hilt / Dependency Injection| مدیریت وابستگی‌ها
Clean Architecture| معماری پروژه
Android SDK| توسعه اپلیکیشن اندروید

---

⚡ Performance

یکی از اهداف پروژه، کاهش زمان راه‌اندازی و بهبود عملکرد اولیه برنامه است.

Benchmark

حالت| میانگین زمان شروع
❌ بدون Baseline Profile| 1,231 ms
✅ با Baseline Profile| در حال اندازه‌گیری

«اعداد Benchmark بر اساس شرایط تست مشخص ثبت و در نسخه‌های بعدی تکمیل خواهند شد.»

---

📂 وضعیت توسعه

بخش| وضعیت
رابط کاربری| 🟢 در حال توسعه
منطق بازی| 🟢 در حال توسعه
Clean Architecture| 🟡 در حال مهاجرت
Dependency Injection| 🟢 پیاده‌سازی شده
Performance Optimization| 🟡 در حال بهینه‌سازی
تست‌ها| 🟡 در حال توسعه
مستندات| 🟡 در حال تکمیل

---

🚀 هدف پروژه

هدف Spy Manager فقط ساخت یک بازی ساده نیست؛ بلکه ایجاد یک پروژه واقعی اندرویدی با ساختار قابل توسعه و استاندارد است.

در فرآیند توسعه، تمرکز اصلی روی موارد زیر است:

Architecture → Maintainability → Performance → UX → Scalability

---

🗺️ Roadmap

- [x] طراحی اولیه بازی
- [x] پیاده‌سازی نسخه اولیه
- [x] طراحی رابط کاربری
- [ ] تکمیل مهاجرت به Clean Architecture
- [ ] تکمیل تست‌های Unit
- [ ] بهینه‌سازی Performance
- [ ] تکمیل سیستم بازی
- [ ] بهبود تجربه کاربری
- [ ] انتشار نسخه پایدار

---

👨‍💻 توسعه‌دهنده

Ali Mahjoob

Android Developer
Kotlin • Jetpack Compose • Clean Architecture

---

<div dir="ltr">⭐ If you like the project, consider giving it a star!

</div></div>
