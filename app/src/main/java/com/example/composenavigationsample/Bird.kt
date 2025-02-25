package com.example.composenavigationsample

data class Bird(
    val id: Int,
    val name: String,
    val watariType: WatariType,
) {
    enum class WatariType(val displayName: String) {
        RyuCho("留鳥"),
        Summer("夏鳥"),
        Winter("冬鳥"),
    }
}

val birdList = listOf(
    Bird(1, "スズメ", Bird.WatariType.RyuCho),
    Bird(2, "シマエナガ", Bird.WatariType.RyuCho),
    Bird(3, "ジョウビタキ", Bird.WatariType.Winter),
    Bird(4, "ツバメ", Bird.WatariType.Summer),
)
