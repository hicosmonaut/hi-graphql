package hi.cosmonaut.graphql.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Country(
    @SerializedName("__typename") val type: String? = null,
    @SerializedName("code") val code: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("capital") val capital: String? = null,
    @SerializedName("emoji") val emoji: String? = null,
    @SerializedName("emojiU") val emojiU: String? = null,
    @SerializedName("languages") val languages: List<Language>? = mutableListOf()
) : Parcelable