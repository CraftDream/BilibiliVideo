package online.bingzi.bilibili.video.internal.handler

import online.bingzi.bilibili.video.internal.engine.NetworkEngine
import online.bingzi.bilibili.video.internal.helper.debug
import online.bingzi.bilibili.video.internal.helper.infoAsLang

/**
 * Following handler
 * 关注处理器
 *
 * @constructor Create empty Following handler
 */
class FollowingHandler : ApiHandler() {
    override fun handle(bvid: String, sessData: String): Boolean {
        debug("关注处理器 > 视频: $bvid | 接受处理")
        NetworkEngine.bilibiliAPI.hasFollowing(bvid, sessData).execute().let { resultResponse ->
            if (resultResponse.isSuccessful) {
                resultResponse.body()?.data?.let {
                    if (it.card.following.not()) {
                        debug("关注处理器 > 视频: $bvid | 未关注")
                        return false
                    }
                }
            }
        }
        debug("关注处理器 > 视频: $bvid | 移交处理")
        return callNextHandler(bvid, sessData)
    }
}
