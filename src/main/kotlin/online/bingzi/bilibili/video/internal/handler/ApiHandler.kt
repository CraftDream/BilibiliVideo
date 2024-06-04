package online.bingzi.bilibili.video.internal.handler

import online.bingzi.bilibili.video.internal.engine.drive.BilibiliApiDrive
import online.bingzi.bilibili.video.internal.helper.debug
import online.bingzi.bilibili.video.internal.helper.debugStatus

abstract class ApiHandler {
    private var nextHandler: ApiHandler? = null

    fun setNextHandler(nextHandler: ApiHandler): ApiHandler {
        this.nextHandler = nextHandler
        return nextHandler
    }

    abstract fun handle(bilibiliApi: BilibiliApiDrive, bvid: String, sessData: String): Boolean

    protected fun callNextHandler(bilibiliApi: BilibiliApiDrive, bvid: String, sessData: String): Boolean {
        return nextHandler?.handle(bilibiliApi, bvid, sessData) ?: let {
            if (debugStatus) {
                debug("最终处理器 > 视频: $bvid | 通过")
            }
            true
        }
    }
}
