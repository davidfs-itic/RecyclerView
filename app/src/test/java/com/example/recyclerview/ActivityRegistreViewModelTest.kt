import com.example.recyclerview.ActivityRegistreViewModel
import org.junit.Assert.*
import org.junit.Test
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule

class ActivityRegistreViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val viewModel = ActivityRegistreViewModel()


    @Test
    fun `actualitzanomUsuari retorna error quan el nom d'usuari està buit`() {

        // Dades d'entrada amb nom d'usuari buit
        viewModel.actualitzanomUsuari("")
        viewModel.comprova_nomusuari()
        assertEquals("El nom d'usuari és obligatori", viewModel.errorNomUsuari.value)
    }

    @Test
    fun `actualitzanomUsuari retorna error comença per simbol`() {

        // Dades d'entrada amb nom d'usuari buit
        viewModel.actualitzanomUsuari("&usuari")
        viewModel.comprova_nomusuari()
        assertEquals("El nom  d'usuari no pot començar per símbol", viewModel.errorNomUsuari.value)
    }
}
