


---

## 🎮 Spy Manager Game

> **یک بازی گروهی معمایی و جذاب برای دورهمی‌ها!**  
> *در حال توسعه · نسخه آلفا · آماده برای تست*

<br/>

<div align="center">
  
  <!-- TODO: جایگزین با اسکرین‌شات واقعی بازی -->
<img src="home.png" width="200" />
  
  <br/>
  <br/>
  
  <i>🎯 اسکرین‌شات واقعی به زودی — در حال آماده‌سازی نسخه نمایشی</i>
  
</div>

---
<!DOCTYPE html>
<html lang="fa" dir="rtl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>نتایج بنچمارک - بازی جاسوس</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            background: linear-gradient(135deg, #0f0f1a 0%, #1a1a2e 100%);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            padding: 40px 20px;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .container {
            max-width: 900px;
            width: 100%;
            margin: 0 auto;
        }

        /* کارت اصلی */
        .benchmark-card {
            background: rgba(30, 30, 50, 0.95);
            backdrop-filter: blur(10px);
            border-radius: 32px;
            padding: 32px;
            box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.5);
            border: 1px solid rgba(255, 255, 255, 0.1);
            transition: transform 0.3s ease;
        }

        .benchmark-card:hover {
            transform: translateY(-5px);
        }

        /* هدر */
        .header {
            text-align: center;
            margin-bottom: 32px;
            border-bottom: 2px solid rgba(76, 175, 80, 0.3);
            padding-bottom: 20px;
        }

        .header h1 {
            font-size: 28px;
            color: #ffffff;
            margin-bottom: 8px;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 12px;
            flex-wrap: wrap;
        }

        .header h1 .badge {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            font-size: 14px;
            padding: 4px 12px;
            border-radius: 50px;
            font-weight: normal;
        }

        .header p {
            color: #a0a0c0;
            font-size: 14px;
        }

        /* جدول */
        .table-wrapper {
            overflow-x: auto;
            margin: 24px 0;
            border-radius: 20px;
            background: rgba(20, 20, 35, 0.6);
        }

        .benchmark-table {
            width: 100%;
            border-collapse: collapse;
            font-size: 16px;
        }

        .benchmark-table th {
            background: linear-gradient(135deg, #2d2d4a 0%, #1a1a30 100%);
            color: #e0e0ff;
            padding: 16px 12px;
            font-weight: 600;
            font-size: 15px;
            border-bottom: 2px solid #4CAF50;
        }

        .benchmark-table td {
            padding: 14px 12px;
            text-align: center;
            color: #d0d0e0;
            border-bottom: 1px solid rgba(255, 255, 255, 0.08);
        }

        .benchmark-table tr:last-child td {
            border-bottom: none;
        }

        .benchmark-table tr:hover td {
            background: rgba(76, 175, 80, 0.08);
        }

        /* وضعیت خوب/بد */
        .bad {
            color: #ff6b6b;
            font-weight: 600;
            background: rgba(255, 107, 107, 0.15);
            padding: 4px 8px;
            border-radius: 12px;
            display: inline-block;
        }

        .good {
            color: #4CAF50;
            font-weight: 700;
            background: rgba(76, 175, 80, 0.15);
            padding: 4px 8px;
            border-radius: 12px;
            display: inline-block;
        }

        .improvement {
            color: #ffd93d;
        }

        /* بخش تحلیل */
        .analysis {
            background: linear-gradient(135deg, rgba(102, 126, 234, 0.15) 0%, rgba(118, 75, 162, 0.15) 100%);
            border-radius: 20px;
            padding: 20px;
            margin-top: 24px;
            border: 1px solid rgba(102, 126, 234, 0.3);
        }

        .analysis h3 {
            color: #ffd93d;
            font-size: 18px;
            margin-bottom: 16px;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .stats {
            display: flex;
            justify-content: space-around;
            flex-wrap: wrap;
            gap: 20px;
            margin-top: 16px;
        }

        .stat-item {
            text-align: center;
            background: rgba(0, 0, 0, 0.3);
            padding: 16px 24px;
            border-radius: 20px;
            flex: 1;
            min-width: 150px;
        }

        .stat-number {
            font-size: 32px;
            font-weight: 800;
            color: #4CAF50;
        }

        .stat-label {
            font-size: 13px;
            color: #a0a0c0;
            margin-top: 8px;
        }

        .stat-number.negative {
            color: #ff6b6b;
        }

        /* فوتر */
        .footer {
            text-align: center;
            margin-top: 24px;
            padding-top: 20px;
            border-top: 1px solid rgba(255, 255, 255, 0.08);
            font-size: 12px;
            color: #8080a0;
        }

        .footer a {
            color: #4CAF50;
            text-decoration: none;
        }

        /* ابزارک‌ها */
        .chip {
            display: inline-block;
            background: rgba(255, 255, 255, 0.1);
            padding: 4px 12px;
            border-radius: 50px;
            font-size: 12px;
            margin: 4px;
        }

        @media (max-width: 600px) {
            .benchmark-card {
                padding: 20px;
            }
            .benchmark-table th,
            .benchmark-table td {
                padding: 10px 8px;
                font-size: 13px;
            }
            .stat-number {
                font-size: 24px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="benchmark-card">
            <div class="header">
                <h1>
                    🎮 نتایج بنچمارک بازی جاسوس
                    <span class="badge">Baseline Profile</span>
                </h1>
                <p>اندازه‌گیری زمان شروع (Cold Start) بر حسب میلی‌ثانیه</p>
            </div>

            <div class="table-wrapper">
                <table class="benchmark-table">
                    <thead>
                        <tr>
                            <th>حالت تست</th>
                            <th>میانگین (ms)</th>
                            <th>سریع‌ترین (ms)</th>
                            <th>کندترین (ms)</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><span class="bad">❌ بدون Baseline Profile</span></td>
                            <td><span class="bad">1,231</span></td>
                            <td>1,202</td>
                            <td>1,615</td>
                        </tr>
                        <tr style="background: rgba(76, 175, 80, 0.05);">
                            <td><span class="good">✅ با Baseline Profile</span></td>
                            <td><span class="good">1,037</span></td>
                            <td>1,002</td>
                            <td>1,282</td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <div class="analysis">
                <h3>
                    📈 تحلیل نتایج
                    <span class="chip">بهبود ۱۶٪</span>
                </h3>
                <div class="stats">
                    <div class="stat-item">
                        <div class="stat-number">-194 ms</div>
                        <div class="stat-label">کاهش میانگین زمان</div>
                    </div>
                    <div class="stat-item">
                        <div class="stat-number">-200 ms</div>
                        <div class="stat-label">بهبود در بهترین حالت</div>
                    </div>
                    <div class="stat-item">
                        <div class="stat-number">-333 ms</div>
                        <div class="stat-label">بهبود در بدترین حالت</div>
                    </div>
                </div>
                <div style="margin-top: 16px; text-align: center; font-size: 14px; color: #ccc;">
                    ⚡ بازی جاسوس با فعال‌سازی Baseline Profile <strong style="color:#4CAF50;">~۲۰۰ میلی‌ثانیه سریع‌تر</strong> شروع می‌شود
                </div>
            </div>

            <div class="footer">
                <p>🔬 تست شده روی دستگاه <strong>Samsung SM-A325F - Android 13</strong></p>
                <p>📊 تعداد دفعات تست: <strong>۵ بار</strong> در هر حالت | 🏷️ <a href="#">#BaselineProfile</a> <a href="#">#AndroidBenchmark</a></p>
            </div>
        </div>
    </div>
</body>
</html>

## 📖 درباره بازی

**Spy Manager** یک بازی گروهی سبک معمایی و مافیا-گونه است که در آن:

- 👥 **تعداد بازیکنان:** ۴ تا ۱۲ نفر
- 🕵️ **نقش‌ها:** مامور (اکثریت) + جاسوس (۱ تا ۳ نفر)
- 🎲 **هدف مامورها:** کشف کلمه مخفی با پرسش و پاسخ هوشمندانه
- 🕶️ **هدف جاسوس:** مخفی ماندن و حدس زدن کلمه از روی سرنخ‌ها

بازی کاملاً **آفلاین** و مناسب برای **دورهمی‌های دوستانه** طراحی شده.

---

## 🎯 قابلیت‌های کلیدی (نسخه آلفا)

| قابلیت | وضعیت | توضیح |
|--------|--------|-------|
| 🎲 **تولید نقش تصادفی** | ✅ کامل | توزیع هوشمند نقش‌ها بین بازیکنان |
| 📝 **مدیریت کلمات** | ✅ کامل | افزودن/حذف کلمات سفارشی توسط کاربر |
| 🎵 **موسیقی محیطی** | ✅ کامل | پخش موسیقی در پس‌زمینه + کنترل پخش |
| 📖 **آموزش بازی** | ✅ کامل | راهنمای کامل برای تازه‌واردها |
| 🎨 **UI با Jetpack Compose** | ✅ کامل | طراحی مدرن و روان |
| 🗃️ **ذخیره کلمات** | ✅ کامل | Room Database + DataStore |
| 🔊 **تنظیمات صدا** | ✅ کامل | MediaPlayer + کنترل حجم صدا |
| 🌙 **حالت شب** | 🔄 برنامه‌ریزی شده | برای نسخه بتا |
| 🏆 **امتیازدهی** | 🔄 برنامه‌ریزی شده | ثبت آمار و رکوردهای بازی |

---

## 🏗️ معماری فنی

```kotlin
// لایه‌های معماری پروژه
SpyManager/
├── presentation/     // Compose UI + ViewModels
├── domain/          // UseCases + Business Logic
├── data/            // Repository + Room + DataStore
└── di/              // Koin Dependency Injection
