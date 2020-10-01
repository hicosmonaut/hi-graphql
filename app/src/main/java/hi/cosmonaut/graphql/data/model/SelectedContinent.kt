package hi.cosmonaut.graphql.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SelectedContinent(
    @SerializedName("data") val data: Data? = null
) : Parcelable