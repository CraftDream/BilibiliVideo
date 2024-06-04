package online.bingzi.bilibili.video.internal.handler

import online.bingzi.bilibili.video.internal.engine.drive.BilibiliApiDrive
import online.bingzi.bilibili.video.internal.helper.debug
import online.bingzi.bilibili.video.internal.helper.infoAsLang

/**
 * Favoured handler
 * 收藏处理器
 *
 * @constructor Create empty Favoured handler
 */
class FavouredHandler : ApiHandler() {
    override fun handle(bilibiliAPI: BilibiliApiDrive, bvid: String, sessData: String): Boolean {
        debug("收藏处理器 > 视频: $bvid | 接受处理")
        bilibiliAPI.hasFavoured(bvid, sessData).execute().let { resultResponse ->
            if (resultResponse.isSuccessful) {
                resultResponse.body()?.data?.let {
                    if (it.favoured.not()) {
                        debug("收藏处理器 > 视频: $bvid | 未收藏")
                        return false
                    }
                }
            }
        }
        debug("收藏处理器 > 视频: $bvid | 移交处理")
        return callNextHandler(bilibiliAPI, bvid, sessData)
    }
}
