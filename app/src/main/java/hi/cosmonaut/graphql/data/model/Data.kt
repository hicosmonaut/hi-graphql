package hi.cosmonaut.graphql.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(
    @SerializedName("continents") val continents: List<Continent>,
    @SerializedName("continent") val continent: Continent

) : Parcelable