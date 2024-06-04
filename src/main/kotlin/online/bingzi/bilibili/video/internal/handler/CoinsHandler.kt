package online.bingzi.bilibili.video.internal.handler

import online.bingzi.bilibili.video.internal.engine.NetworkEngine
import online.bingzi.bilibili.video.internal.helper.debug
import online.bingzi.bilibili.video.internal.helper.infoAsLang

/**
 * Coins handler
 * 硬币处理器
 *
 * @constructor Create empty Coins handler
 */
class CoinsHandler : ApiHandler() {
    override fun handle(bvid: String, sessData: String): Boolean {
        debug("硬币处理器 > 视频: $bvid | 接受处理")
        NetworkEngine.bilibiliAPI.hasCoins(bvid, sessData).execute().let {
            if (it.isSuccessful) {
                it.body()?.data?.multiply?.let { count ->
                    if (count < 1) {
                        debug("硬币处理器 > 视频: $bvid | 硬币不足(已投币: $count, 需要投币: 2)")
                        return false
                    }
                }
            }
        }
        debug("硬币处理器 > 视频: $bvid | 移交处理")
        return callNextHandler(bvid, sessData)
    }
}
