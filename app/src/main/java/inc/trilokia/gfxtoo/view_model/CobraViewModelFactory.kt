package inc.trilokia.gfxtoo.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import inc.trilokia.gfxtoo.data.CobraRepository
import inc.trilokia.gfxtoo.utils.LinkParser

class CobraViewModelFactory(
    private val repository: CobraRepository,
    private val parser: LinkParser
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CobraViewModel(repository, parser) as T
    }
}