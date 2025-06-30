package com.mansi.guardianangel



object OfflineChatbot {

    fun getFallbackReply(input: String): String {
        val lowerInput = input.lowercase()

        return when {
            listOf("help", "madad", "sahayata", "sos", "assist", "problem").any { it in lowerInput } ->
                "I'm here to help you. Please stay calm. Aap please thoda detail mein batayein ki kya dikkat hai."

            listOf("police", "thana", "chori", "loot", "attack", "fight", "maar peet").any { it in lowerInput } ->
                "Police se sampark karein: 📞 112\nAap agar danger mein hain toh turant location share karein aur safe jagah chaliye."

            listOf("fire", "aag", "jal gaya", "burn", "smoke", "bhujan", "bujhao").any { it in lowerInput } ->
                "Aag lagne par Fire Brigade ko turant call karein: 📞 101\nBijli ka switch band karein aur safe door se nikal jaayein."

            listOf("doctor", "behosh", "injury", "bleeding", "heart", "stroke").any { it in lowerInput } ->
                "Medical help ke liye turant call karein: 📞 108\nAapke aaspaas koi medical facility ho toh wahan turant jayein."

            listOf("gayab", "lost", "missing", "kho gaya", "child lost").any { it in lowerInput } ->
                "Yeh serious matter hai. Police ko report karein: 📞 112\nPhoto aur last seen details ready rakhein."

            listOf("rape", "sexual", "harass", "molest", "abuse", "bad touch", "chhed chhad").any { it in lowerInput } ->
                "Aapki safety sabse pehle hai. Women Helpline: 📞 1091\nTrustworthy person ko inform karein aur safe space mein jaayein."

            listOf("violence", "husband", "maar", "torture", "domestic").any { it in lowerInput } ->
                "Domestic violence serious crime hai. Call 📞 181 (Women Helpline) ya 📞 1091.\nAapka kanooni adhikaar hai surakshit rehna."

            listOf("child", "kid", "kidnap", "crying", "abuse child").any { it in lowerInput } ->
                "Childline India: 📞 1098\nBaccho ke saath kisi bhi galat vyavhaar par turant action lena chahiye."

            listOf("suicide", "depression", "hopeless", "alone", "mental", "anxiety").any { it in lowerInput } ->
                "Aap akele nahi hain. Mental Health Helpline: 📞 9152987821\nPlease baat karein kisi parivaar ya dost se. Main bhi aapka saathi hoon."

            else -> "Mujhe poori baat samajh nahi aayi. Kya aap thoda aur clearly batayenge? Main yahan madad ke liye hoon. 💙"
        }
    }
}
