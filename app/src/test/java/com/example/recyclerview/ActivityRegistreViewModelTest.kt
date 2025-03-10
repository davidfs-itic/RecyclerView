import com.example.recyclerview.ActivityRegistreViewModel
import org.junit.Assert.*
import org.junit.Test

class ActivityRegistreViewModelTest {

    private val viewModel = ActivityRegistreViewModel()


    @Test
    fun `actualitzanomUsuari retorna error quan el nom d'usuari està buit`() {

        // Dades d'entrada amb nom d'usuari buit
        viewModel.actualitzanomUsuari("")

        assertEquals("El nom d'usuari és obligatori", viewModel.validaciodades.value?.errorNomUsuari)
    }

    @Test
    fun `actualitzanomUsuari retorna error comença per simbol`() {

        // Dades d'entrada amb nom d'usuari buit
        viewModel.actualitzanomUsuari("&usuari")

        assertEquals("El nom  d'usuari no pot començar per símbol", viewModel.validaciodades.value?.errorNomUsuari)
    }


}
