package io.dp.weather.app.net.dto

import java.util.ArrayList

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import android.os.Parcel
import android.os.Parcelable

import io.dp.weather.app.WeatherIconUrl

public data class CurrentCondition() : Parcelable {

    @Expose
    public var cloudcover: String? = null
    @Expose
    public var humidity: String? = null
    @SerializedName("observation_time")
    @Expose
    public var observationTime: String? = null
    @Expose
    public var precipMM: String? = null
    @Expose
    public var pressure: String? = null
    @SerializedName("temp_C")
    @Expose
    public var tempC: String? = null
    @SerializedName("temp_F")
    @Expose
    public var tempF: String? = null
    @Expose
    public var visibility: String? = null
    @Expose
    public var weatherCode: String? = null
    @Expose
    public var weatherDesc: List<WeatherDesc>? = ArrayList()
    @Expose
    public var weatherIconUrl: List<WeatherIconUrl>? = ArrayList()
    @Expose
    public var winddir16Point: String? = null
    @Expose
    public var winddirDegree: String? = null
    @Expose
    public var windspeedKmph: String? = null
    @Expose
    public var windspeedMiles: String? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is CurrentCondition) {
            return false
        }

        if (if (cloudcover != null) cloudcover != o.cloudcover else o.cloudcover != null) {
            return false
        }
        if (if (humidity != null) humidity != o.humidity else o.humidity != null) {
            return false
        }
        if (if (observationTime != null) observationTime != o.observationTime
        else o.observationTime != null) {
            return false
        }
        if (if (precipMM != null) precipMM != o.precipMM else o.precipMM != null) {
            return false
        }
        if (if (pressure != null) pressure != o.pressure else o.pressure != null) {
            return false
        }
        if (if (tempC != null) tempC != o.tempC else o.tempC != null) {
            return false
        }
        if (if (tempF != null) tempF != o.tempF else o.tempF != null) {
            return false
        }
        if (if (visibility != null) visibility != o.visibility else o.visibility != null) {
            return false
        }
        if (if (weatherCode != null) weatherCode != o.weatherCode else o.weatherCode != null) {
            return false
        }
        if (if (weatherDesc != null) weatherDesc != o.weatherDesc else o.weatherDesc != null) {
            return false
        }
        if (if (weatherIconUrl != null) weatherIconUrl != o.weatherIconUrl
        else o.weatherIconUrl != null) {
            return false
        }
        if (if (winddir16Point != null) winddir16Point != o.winddir16Point
        else o.winddir16Point != null) {
            return false
        }
        if (if (winddirDegree != null) winddirDegree != o.winddirDegree
        else o.winddirDegree != null) {
            return false
        }
        if (if (windspeedKmph != null) windspeedKmph != o.windspeedKmph
        else o.windspeedKmph != null) {
            return false
        }
        if (if (windspeedMiles != null) windspeedMiles != o.windspeedMiles
        else o.windspeedMiles != null) {
            return false
        }

        return true
    }

    override fun hashCode(): Int {
        var result = if (cloudcover != null) cloudcover!!.hashCode() else 0
        result = 31 * result + (if (humidity != null) humidity!!.hashCode() else 0)
        result = 31 * result + (if (observationTime != null) observationTime!!.hashCode() else 0)
        result = 31 * result + (if (precipMM != null) precipMM!!.hashCode() else 0)
        result = 31 * result + (if (pressure != null) pressure!!.hashCode() else 0)
        result = 31 * result + (if (tempC != null) tempC!!.hashCode() else 0)
        result = 31 * result + (if (tempF != null) tempF!!.hashCode() else 0)
        result = 31 * result + (if (visibility != null) visibility!!.hashCode() else 0)
        result = 31 * result + (if (weatherCode != null) weatherCode!!.hashCode() else 0)
        result = 31 * result + (if (weatherDesc != null) weatherDesc!!.hashCode() else 0)
        result = 31 * result + (if (weatherIconUrl != null) weatherIconUrl!!.hashCode() else 0)
        result = 31 * result + (if (winddir16Point != null) winddir16Point!!.hashCode() else 0)
        result = 31 * result + (if (winddirDegree != null) winddirDegree!!.hashCode() else 0)
        result = 31 * result + (if (windspeedKmph != null) windspeedKmph!!.hashCode() else 0)
        result = 31 * result + (if (windspeedMiles != null) windspeedMiles!!.hashCode() else 0)
        return result
    }

    override fun toString(): String {
        return "CurrentCondition{cloudcover='$cloudcover', humidity='$humidity', observationTime='$observationTime', precipMM='$precipMM', pressure='$pressure', tempC='$tempC', tempF='$tempF', visibility='$visibility', weatherCode='$weatherCode', weatherDesc=$weatherDesc, weatherIconUrl=$weatherIconUrl, winddir16Point='$winddir16Point', winddirDegree='$winddirDegree', windspeedKmph='$windspeedKmph', windspeedMiles='$windspeedMiles'}"
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.cloudcover)
        dest.writeString(this.humidity)
        dest.writeString(this.observationTime)
        dest.writeString(this.precipMM)
        dest.writeString(this.pressure)
        dest.writeString(this.tempC)
        dest.writeString(this.tempF)
        dest.writeString(this.visibility)
        dest.writeString(this.weatherCode)
        dest.writeList(this.weatherDesc)
        dest.writeList(this.weatherIconUrl)
        dest.writeString(this.winddir16Point)
        dest.writeString(this.winddirDegree)
        dest.writeString(this.windspeedKmph)
        dest.writeString(this.windspeedMiles)
    }

    private constructor(`in`: Parcel) : this() {
        this.cloudcover = `in`.readString()
        this.humidity = `in`.readString()
        this.observationTime = `in`.readString()
        this.precipMM = `in`.readString()
        this.pressure = `in`.readString()
        this.tempC = `in`.readString()
        this.tempF = `in`.readString()
        this.visibility = `in`.readString()
        this.weatherCode = `in`.readString()
        this.weatherDesc = ArrayList<WeatherDesc>()
//        `in`.readList(this.weatherDesc, ArrayList<Any>::class.java!!.getClassLoader())
        this.weatherIconUrl = ArrayList<WeatherIconUrl>()
//        `in`.readList(this.weatherIconUrl, ArrayList<WeatherIconUrl>::class.java!!.getClassLoader())
        this.winddir16Point = `in`.readString()
        this.winddirDegree = `in`.readString()
        this.windspeedKmph = `in`.readString()
        this.windspeedMiles = `in`.readString()
    }

    companion object {

        public val CREATOR: Parcelable.Creator<CurrentCondition> = object : Parcelable.Creator<CurrentCondition> {
            override fun createFromParcel(source: Parcel): CurrentCondition {
                return CurrentCondition(source)
            }

            override fun newArray(size: Int): Array<CurrentCondition?> {
                return arrayOfNulls(size)
            }
        }
    }
}