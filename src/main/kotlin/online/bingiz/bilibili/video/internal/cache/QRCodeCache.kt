package online.bingiz.bilibili.video.internal.cache

import java.util.concurrent.ConcurrentHashMap

/**
 * QRCode key
 */
val qrCodeKeyCache: ConcurrentHashMap<String, List<String>> = ConcurrentHashMap()