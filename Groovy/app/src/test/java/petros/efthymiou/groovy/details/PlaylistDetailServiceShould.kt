package petros.efthymiou.groovy.details

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import petros.efthymiou.groovy.utils.BaseUnitTest

/**
 * 1. Create PlaylistDetailServiceShould
 * 2. Mock PlaylistDetailApi
 * 3. fetchPlaylistDetailFromApi()
 * 4. convertValueToFlowResultAndEmitThem()
 * 5. emitErrorResultWhenNetworkFails()
 * 6. Refactor
 * 7. Run acceptance test
 */
class PlaylistDetailServiceShould : BaseUnitTest() {
    private lateinit var service: PlaylistDetailService
    private val id = "100"
    private val api: PlaylistDetailApi = mock()
    private val playlistDetail: PlaylistDetail = mock()
    private val exception = RuntimeException("AbCD test message")

    @Test
    fun fetchPlaylistDetailFromApi() = runBlockingTest {
        mockSuccessfulCase()
        service.fetchPlaylistDetail(id).single()
        verify(api, times(1)).fetchPlaylistDetail(id)
    }

    @Test
    fun convertValuesToFlowResultAndEmitThem() = runBlockingTest {
        mockSuccessfulCase()
        assertEquals(Result.success(playlistDetail), service.fetchPlaylistDetail(id).single())
    }

    @Test
    fun emitErrorResultWhenNetworkFails() = runBlockingTest {
        mockFailureCase()
        assertEquals("Something went wrong", service.fetchPlaylistDetail(id).single().exceptionOrNull()?.message)
    }

    private suspend fun mockFailureCase() {
        whenever(api.fetchPlaylistDetail(id)).thenThrow(exception)
        service = PlaylistDetailService(api)
    }

    private suspend fun mockSuccessfulCase() {
        whenever(api.fetchPlaylistDetail(id)).thenReturn(
            playlistDetail
        )
        service = PlaylistDetailService(api)
    }
}