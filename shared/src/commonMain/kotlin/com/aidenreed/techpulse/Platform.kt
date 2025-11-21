package com.aidenreed.techpulse

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform