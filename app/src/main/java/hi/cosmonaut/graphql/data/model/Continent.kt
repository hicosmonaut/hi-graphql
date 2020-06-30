package hi.cosmonaut.graphql.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Continent(
    @SerializedName("code") val code: String,
    @SerializedName("__typename") val type: String,
    @SerializedName("countries") val countries: List<Country>,
    @SerializedName("name") val name: String
) : Parcelable