package com.example.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("Version") val version: Int? = null,
    @SerializedName("Key") val key: String? = null,
    @SerializedName("Type") var type: String? = null,
    @SerializedName("Rank") var rank: Int? = null,
    @SerializedName("LocalizedName") var localizedName: String? = null,
    @SerializedName("EnglishName") var englishName: String? = null,
    @SerializedName("PrimaryPostalCode") var primaryPostalCode: String? = null,
    @SerializedName("Region") var region: Region? = Region(),
    @SerializedName("Country") var country: Region? = Region(),
    @SerializedName("AdministrativeArea") var administrativeArea: Region? = Region(),
    @SerializedName("TimeZone") var timeZone: TimeZone? = TimeZone(),
    @SerializedName("GeoPosition") var geoPosition: GeoPosition? = GeoPosition(),
    @SerializedName("IsAlias") var isAlias: Boolean? = null,
    @SerializedName("SupplementalAdminAreas") var supplementalAdminAreas: List<Region> = emptyList(),
    @SerializedName("DataSets") var dataSets: List<String> = emptyList()
)

data class Region(
    @SerializedName("ID") val id: String? = null,
    @SerializedName("LocalizedName") val localizedName: String? = null,
    @SerializedName("EnglishName") val englishName: String? = null,
    @SerializedName("Level") val level: Int? = null,
    @SerializedName("LocalizedType") val localizedType: String? = null,
    @SerializedName("EnglishType") val englishType: String? = null,
    @SerializedName("CountryID") val countryID: String? = null
)

data class TimeZone(
    @SerializedName("Code") var code: String? = null,
    @SerializedName("Name") var name: String? = null,
    @SerializedName("GmtOffset") var gmtOffset: Double? = null,
    @SerializedName("IsDaylightSaving") var isDaylightSaving: Boolean? = null,
    @SerializedName("NextOffsetChange") var nextOffsetChange: String? = null
)

data class Metric(
    @SerializedName("Value") var value: Double? = null, // Value should be Double for better precision
    @SerializedName("Unit") var unit: String? = null,
    @SerializedName("UnitType") var unitType: Int? = null
)

data class Imperial(
    @SerializedName("Value") var value: Double? = null, // Value should be Double
    @SerializedName("Unit") var unit: String? = null,
    @SerializedName("UnitType") var unitType: Int? = null
)

data class Elevation(
    @SerializedName("Metric") var metric: Metric? = Metric(),
    @SerializedName("Imperial") var imperial: Imperial? = Imperial()
)

data class GeoPosition(
    @SerializedName("Latitude") var latitude: Double? = null,
    @SerializedName("Longitude") var longitude: Double? = null,
    @SerializedName("Elevation") var elevation: Elevation? = Elevation()
)

//Weather of a particular city by key
data class Weather(
    @SerializedName("Headline") var Headline: Headline? = Headline(),
    @SerializedName("DailyForecasts") var DailyForecasts: ArrayList<DailyForecasts> = arrayListOf()
)

data class Headline(
    @SerializedName("EffectiveDate") var EffectiveDate: String? = null,
    @SerializedName("EffectiveEpochDate") var EffectiveEpochDate: Int? = null,
    @SerializedName("Severity") var Severity: Int? = null,
    @SerializedName("Text") var Text: String? = null,
    @SerializedName("Category") var Category: String? = null,
    @SerializedName("EndDate") var EndDate: String? = null,
    @SerializedName("EndEpochDate") var EndEpochDate: Int? = null,
    @SerializedName("MobileLink") var MobileLink: String? = null,
    @SerializedName("Link") var Link: String? = null
)

data class Minimum(
    @SerializedName("Value") var Value: Int? = null,
    @SerializedName("Unit") var Unit: String? = null,
    @SerializedName("UnitType") var UnitType: Int? = null
)

data class Maximum(
    @SerializedName("Value") var Value: Int? = null,
    @SerializedName("Unit") var Unit: String? = null,
    @SerializedName("UnitType") var UnitType: Int? = null
)

data class Temperature(
    @SerializedName("Minimum") var Minimum: Minimum? = Minimum(),
    @SerializedName("Maximum") var Maximum: Maximum? = Maximum()
)

data class Day(
    @SerializedName("Icon") var Icon: Int? = null,
    @SerializedName("IconPhrase") var IconPhrase: String? = null,
    @SerializedName("HasPrecipitation") var HasPrecipitation: Boolean? = null,
    @SerializedName("PrecipitationType") var PrecipitationType: String? = null,
    @SerializedName("PrecipitationIntensity") var PrecipitationIntensity: String? = null
)

data class Night(
    @SerializedName("Icon") var Icon: Int? = null,
    @SerializedName("IconPhrase") var IconPhrase: String? = null,
    @SerializedName("HasPrecipitation") var HasPrecipitation: Boolean? = null,
    @SerializedName("PrecipitationType") var PrecipitationType: String? = null,
    @SerializedName("PrecipitationIntensity") var PrecipitationIntensity: String? = null
)

data class DailyForecasts(
    @SerializedName("Date") var Date: String? = null,
    @SerializedName("EpochDate") var EpochDate: Int? = null,
    @SerializedName("Temperature") var Temperature: Temperature? = Temperature(),
    @SerializedName("Day") var Day: Day? = Day(),
    @SerializedName("Night") var Night: Night? = Night(),
    @SerializedName("Sources") var Sources: ArrayList<String> = arrayListOf(),
    @SerializedName("MobileLink") var MobileLink: String? = null,
    @SerializedName("Link") var Link: String? = null
)
