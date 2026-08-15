package com.behnamuix.spygame.viewModel

import androidx.lifecycle.ViewModel
import com.behnamuix.retrofittest.SpyGame.model.Agent
import com.behnamuix.spygame.core.media.controller.MusicController

class TrainingViewModel(private val controller: MusicController) : ViewModel() {
    var listRole = listOf<String>(
        "مامور",
        "جاسوس",
    )
    val agentEducationList = listOf(
        Agent(
            "هدف",
            desc = "شناسایی و حذف جاسوس یا جاسوسان از طریق رای گیری"
        ),
        Agent(
            "اطلاعات",
            desc = "میداند که خودش مامور است نمیداند هویت جاسوس یا جاسوسان کیست "
        ),
        Agent(
            "تحلیل",
            desc = "با دقت به سوالات پرسیده شده و پاسخ های داده شده گوش میدهد به دنبال تناقضات پاسخ های مبهم یا اطلاعاتی است که با دانشش از مکان یا موضوع همخوانی ندارد "
        ),
        Agent(
            "پرسش هوشمندانه",
            desc = "سوالاتی میپرسد که سرنخ هایی درباره هویت واقعی فرد مقابل بدهد اما خودش را لو ندهد"
        ),
        Agent(
            "همکاری",
            desc = "سعی میکند با سایر ماموران ارتباط برقرار کند وتیمی برای شناسایی جاسوس تشکیل دهد "
        ),
        Agent(
            "رای گیری",
            desc = "پس از جمع آوری اطلاعات به کسی رای میدهد که بیشترین شکرا به او دارد  "
        ),
        Agent(
            "چالش",
            desc = "باید بتواند در میان بازیکنان کسی که اطلاعات کمتری دارد یا پاسخ هایش با منطق موقعیت همخوانی ندارد پیدا کند"
        )
    )

    fun setVolume() {
        //mediaVm.volumeLow()
    }


}