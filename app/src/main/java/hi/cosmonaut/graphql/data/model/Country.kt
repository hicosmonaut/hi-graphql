package hi.cosmonaut.graphql.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Country(
    @SerializedName("__typename") val type: String,
    @SerializedName("code") val code: String,
    @SerializedName("name") val name: String,
    @SerializedName("capital") val capital: String,
    @SerializedName("emoji") val emoji: String,
    @SerializedName("emojiU") val emojiU: String,
    @SerializedName("languages") val languages: List<Language>
) : Parcelable