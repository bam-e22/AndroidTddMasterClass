package petros.efthymiou.groovy.playlist

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import petros.efthymiou.groovy.utils.BaseUnitTest

class PlaylistServiceShould : BaseUnitTest() {
    private lateinit var service: PlaylistService
    private val api: PlaylistApi = mock()
    private val playlists: List<PlaylistRaw> = mock()

    @Test
    fun fetchPlaylistsFromAPI() = runBlockingTest {
        service = PlaylistService(api)
        service.fetchPlaylists().first() // <- add terminal operation
        verify(api, times(1)).fetchAllPlayLists()
    }

    @Test
    fun convertValuesToFlowResultAndEmitsThem() = runBlockingTest {
        mockSuccessfulCase()
        assertEquals(Result.success(playlists), service.fetchPlaylists().first())
    }

    @Test
    fun emitsErrorResultWhenNetworkFails() = runBlockingTest {
        mockFailureCase()
        assertEquals("Something went wrong", service.fetchPlaylists().first().exceptionOrNull()?.message)
    }

    private suspend fun mockSuccessfulCase() {
        whenever(api.fetchAllPlayLists()).thenReturn(playlists)
        service = PlaylistService(api)
    }

    private suspend fun mockFailureCase() {
        whenever(api.fetchAllPlayLists()).thenThrow(RuntimeException("Backend exception"))
        service = PlaylistService(api)
    }
}