package hi.cosmonaut.graphql.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Continent(
    @SerializedName("code") val code: String,
    @SerializedName("__typename") val type: String? = null,
    @SerializedName("countries") val countries: List<Country>? = mutableListOf(),
    @SerializedName("name") val name: String? = null
) : Parcelable