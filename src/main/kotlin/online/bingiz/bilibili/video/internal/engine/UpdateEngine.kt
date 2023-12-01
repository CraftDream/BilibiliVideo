package online.bingiz.bilibili.video.internal.engine

import okhttp3.ResponseBody
import online.bingiz.bilibili.video.internal.engine.drive.UpdateDrive
import online.bingiz.bilibili.video.internal.helper.infoMessageAsLang
import online.bingiz.bilibili.video.internal.helper.warningMessageAsLang
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.Schedule
import taboolib.common.util.Version
import taboolib.module.configuration.Configuration
import taboolib.module.configuration.Type
import taboolib.platform.util.bukkitPlugin

object UpdateEngine {

    private val updateAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://github.com/BingZi-233/BilibiliVideo/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UpdateDrive::class.java)
    }

    lateinit var configuration: Configuration

    /**
     * 检查更新，每隔12小时检查一次
     */
    @Schedule(async = true, period = 1000 * 60 * 60 * 12)
    @Awake(LifeCycle.ACTIVE)
    fun checkUpdate() {
        updateAPI.getVersion().enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    response.body()?.string()?.let {
                        val content = it.replace("=", ": ")
                        configuration = Configuration.loadFromString(content, type = Type.YAML)
                        val githubVersion = configuration.getString("version")
                        if (Version(githubVersion).isAfter(Version(bukkitPlugin.description.version))) {
                            githubVersion?.let { version -> infoMessageAsLang("UpdateNewVersion", version) }
                        }
                    } ?: warningMessageAsLang("UpdateCheckFailure", "无正文内容")
                } else {
                    warningMessageAsLang("UpdateCheckFailure", response.errorBody()?.string() ?: "无信息")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                warningMessageAsLang("UpdateCheckFailure", t.message ?: "无信息")
            }
        })
    }
}