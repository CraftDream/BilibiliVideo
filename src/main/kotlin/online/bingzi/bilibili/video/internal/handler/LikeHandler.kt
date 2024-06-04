package online.bingzi.bilibili.video.internal.handler

import online.bingzi.bilibili.video.internal.engine.NetworkEngine
import online.bingzi.bilibili.video.internal.helper.debug
import online.bingzi.bilibili.video.internal.helper.infoAsLang

/**
 * Like handler
 * 点赞处理器
 *
 * @constructor Create empty Like handler
 */
class LikeHandler : ApiHandler() {
    override fun handle(bvid: String, sessData: String): Boolean {
        debug("点赞处理器 > 视频: $bvid | 接受处理")
        NetworkEngine.bilibiliAPI.hasLike(bvid, sessData).execute().let {
            if (it.isSuccessful) {
                it.body()?.data?.let { count ->
                    if (count < 1) {
                        debug("点赞处理器 > 视频: $bvid | 未点赞")
                        return false
                    }
                }
            }
        }
        debug("点赞处理器 > 视频: $bvid | 移交处理")
        return callNextHandler(bvid, sessData)
    }
}

