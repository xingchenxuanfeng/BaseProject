package xc.baseproject.app

import androidx.annotation.Keep
import io.reactivex.functions.Action

@Keep
data class YiqingModel(
        val currentConfirmedCount: Long,
        val confirmedCount: Long,
        val suspectedCount: Long,
        val curedCount: Long,
        val deadCount: Long,
        val seriousCount: Long,
        val currentConfirmedIncr: Long,
        val confirmedIncr: Long,
        val suspectedIncr: Long,
        val curedIncr: Long,
        val deadIncr: Long,
        val seriousIncr: Long,
        val globalStatistics: GlobalStatistics?,
        val areaStat: List<AreaStat>?,
        val globalAreaStat: List<GlobalAreaStat>?,
        val getCount: Long,
        val updateTime: Long
)

@Keep
data class AreaStat(
        val provinceName: String,
        val provinceShortName: String,
        val currentConfirmedCount: Long,
        val confirmedCount: Long,
        val suspectedCount: Long,
        val curedCount: Long,
        val deadCount: Long,
        val comment: String,
        val locationID: Long,
        val statisticsData: String,
        val cities: List<City>
)

@Keep
data class City(
        val cityName: String,
        val currentConfirmedCount: Long,
        val confirmedCount: Long,
        val suspectedCount: Long,
        val curedCount: Long,
        val deadCount: Long,
        val locationID: Long
)

@Keep
data class GlobalAreaStat(
        val id: Long? = null,
        val createTime: Long? = null,
        val modifyTime: Long? = null,
        val tags: String? = null,
        val countryType: Long,
        val continents: Continents,
        val provinceID: String,
        val provinceName: String,
        val provinceShortName: String,
        val cityName: String,
        val currentConfirmedCount: Long,
        val confirmedCount: Long,
        val confirmedCountRank: Long? = null,
        val suspectedCount: Long,
        val curedCount: Long,
        val deadCount: Long,
        val deadCountRank: Long? = null,
        val deadRate: String,
        val deadRateRank: Long? = null,
        val comment: String? = null,
        val sort: Long? = null,
        val locationID: Long,
        val countryShortCode: String,
        val countryFullName: String,
        val statisticsData: String,
        val incrVo: IncrVo,
        val showRank: Boolean
)

@Keep
enum class Continents {
    亚洲,
    其他,
    北美洲,
    南美洲,
    大洋洲,
    欧洲,
    非洲
}

@Keep
data class IncrVo(
        val currentConfirmedIncr: Long,
        val confirmedIncr: Long,
        val curedIncr: Long,
        val deadIncr: Long
)

@Keep
data class GlobalStatistics(
        val currentConfirmedCount: Long,
        val confirmedCount: Long,
        val curedCount: Long,
        val deadCount: Long,
        val currentConfirmedIncr: Long,
        val confirmedIncr: Long,
        val curedIncr: Long,
        val deadIncr: Long
)

@Keep
data class SeeAllItemData(
        val action: Action
)
