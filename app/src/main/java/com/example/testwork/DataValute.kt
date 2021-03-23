package com.example.testwork

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.widget.ArrayAdapter
import com.google.gson.annotations.SerializedName

data class DataValute (
    val Date : String?,
    val PreviousDate : String?,
    val PreviousURL : String?,
    val Timestamp : String?,
    val Valute : Valute
    )
data class Valute(
        val AUD :AUD,
        val AZN : AZN,
        val EUR : EUR,
        val USD : USD,
        val GBP : GBP,
        val BYN : BYN,
        val BGN : BGN,
        val BRL : BRL,
        val HUF : HUF,
        val HKD : HKD,
        val DKK : DKK,
        val INR : INR,
        val KZT : KZT,
        val CAD : CAD,
        val KGS : KGS,
        val CNY : CNY,
        val MDL : MDL,
        val NOK : NOK,
        val PLN : PLN
) : Parcelable{
    constructor(parcel: Parcel) : this(
            TODO("AUD"),
            TODO("AZN"),
            TODO("EUR"),
            TODO("USD"),
            TODO("GBP"),
            TODO("BYN"),
            TODO("BGN"),
            TODO("BRL"),
            TODO("HUF"),
            TODO("HKD"),
            TODO("DKK"),
            TODO("INR"),
            TODO("KZT"),
            TODO("CAD"),
            TODO("KGS"),
            TODO("CNY"),
            TODO("MDL"),
            TODO("NOK"),
            TODO("PLN")
    ) {
    }
    override fun toString() : String{
    return " $AUD $AZN $EUR $USD $GBP $BYN $BGN $BRL $HUF $HKD $DKK" +
            " $INR $KZT $CAD $KGS $CNY $MDL $NOK $PLN"
}
    override fun writeToParcel(parcel: Parcel, flags: Int) {
    }
    override fun describeContents(): Int {
        return 0
    }
    companion object CREATOR : Parcelable.Creator<Valute> {
        override fun createFromParcel(parcel: Parcel): Valute {
            return Valute(parcel)
        }
        override fun newArray(size: Int): Array<Valute?> {
            return arrayOfNulls(size)
        }
    }
}
 data class AUD(
         @SerializedName("Name") val Name : String?,
         @SerializedName("Value") val Value : Double?
     ){override fun toString() : String{
    return "$Name  :  $Value${"\n"}"
}}

    data class AZN(
            val Name : String?,
            val Value : Double?
    ){override fun toString() : String{
        return "$Name  :  $Value  ${"\n"}"
    }}

    class EUR(
            val Name : String?,
            val Value : Double?
    ){override fun toString() : String{
        return "$Name  :  $Value  ${"\n"}"
    }}
    class USD (
            val Name : String?,
            val Value : Double?
    ){override fun toString() : String{
        return "$Name  :  $Value  ${"\n"}"
    }}
data class GBP(
    val Name : String?,
    val Value : Double?
){override fun toString() : String{
    return "$Name  :  $Value  ${"\n"}"
}}
data class BYN(
    val Name : String?,
    val Value : Double?
){override fun toString() : String{
    return "$Name  :  $Value  ${"\n"}"
}}
data class BGN(
    val Name : String?,
    val Value : Double?
){override fun toString() : String{
    return "$Name  :  $Value  ${"\n"}"
}}
data class BRL(
    val Name : String?,
    val Value : Double?
){override fun toString() : String{
    return "$Name  :  $Value  ${"\n"}"
}}
data class HUF(
        val Name : String?,
        val Value : Double?
){override fun toString() : String{
    return "$Name  :  $Value  ${"\n"}"
}}
data class HKD(
        val Name : String?,
        val Value : Double?
){override fun toString() : String{
    return "$Name  :  $Value  ${"\n"}"
}}
data class DKK(
        val Name : String?,
        val Value : Double?
){override fun toString() : String{
    return "$Name  :  $Value  ${"\n"}"
}}
data class INR(
        val Name : String?,
        val Value : Double?
){override fun toString() : String{
    return "$Name  :  $Value  ${"\n"}"
}}
data class KZT(
        val Name : String?,
        val Value : Double?
){override fun toString() : String{
    return "$Name  :  $Value  ${"\n"}"
}}
data class CAD(
        val Name : String?,
        val Value : Double?
){override fun toString() : String{
    return "$Name  :  $Value  ${"\n"}"
}}
data class KGS(
        val Name : String?,
        val Value : Double?
){override fun toString() : String{
    return "$Name  :  $Value  ${"\n"}"
}}
data class CNY(
        val Name : String?,
        val Value : Double?
){override fun toString() : String{
    return "$Name  :  $Value  ${"\n"}"
}}
data class MDL(
        val Name : String?,
        val Value : Double?
){override fun toString() : String{
    return "$Name  :  $Value  ${"\n"}"
}}
data class NOK(
        val Name : String?,
        val Value : Double?
){override fun toString() : String{
    return "$Name  :  $Value  ${"\n"}"
}}
data class PLN(
        val Name : String?,
        val Value : Double?
){override fun toString() : String{
    return "$Name  :  $Value  ${"\n"}"
}}
//class Bund(context: Context, val resource: Int, val list: ArrayList<String>) : ArrayAdapter<String>(context, resource, list), Parcelable {
//    constructor(parcel: Parcel) : this() {
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<Bund> {
//        override fun createFromParcel(parcel: Parcel): Bund {
//            return Bund(parcel)
//        }
//
//        override fun newArray(size: Int): Array<Bund?> {
//            return arrayOfNulls(size)
//        }
//    }

//}
//
//}