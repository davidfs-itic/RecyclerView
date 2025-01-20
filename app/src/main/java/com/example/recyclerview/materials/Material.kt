import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Material(
    val id: Int,
    val descripcio: String,
    val imatge: String
) : Parcelable