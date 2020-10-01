package hi.cosmonaut.graphql.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(
    @SerializedName("continents") var continents: List<Continent>? = mutableListOf(),
    @SerializedName("continent") var continent: Continent? = null
) : Parcelable