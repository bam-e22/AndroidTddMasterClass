package petros.efthymiou.groovy.playlist

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import petros.efthymiou.groovy.utils.BaseUnitTest

class PlaylistRepositoryShould : BaseUnitTest() {
    private val service: PlaylistService = mock()
    private val mapper: PlaylistMapper = mock()
    private val playlists = mock<List<Playlist>>()
    private val playlistsRaw = mock<List<PlaylistRaw>>()
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getPlaylistsFromService() = runBlockingTest {
        val repository = mockSuccessfulCase()
        repository.getPlayLists()
        verify(service, times(1)).fetchPlaylists()
    }

    @Test
    fun emitMappedPlaylistsFromService() = runBlockingTest {
        val repository = mockSuccessfulCase()
        assertEquals(playlists, repository.getPlayLists().first().getOrNull())
    }

    @Test
    fun propagateErrors() = runBlockingTest {
        val repository = mockFailureCase()
        assertEquals(exception, repository.getPlayLists().first().exceptionOrNull())
    }

    @Test
    fun delegateBusinessLogicToMapper() = runBlockingTest {
        val repository = mockSuccessfulCase()
        repository.getPlayLists().first()
        verify(mapper, times(1)).invoke(playlistsRaw)
    }

    private suspend fun mockFailureCase(): PlaylistRepository {
        whenever(service.fetchPlaylists()).thenReturn(flow {
            emit(Result.failure(exception))
        })
        return PlaylistRepository(service, mapper)
    }

    private suspend fun mockSuccessfulCase(): PlaylistRepository {
        whenever(service.fetchPlaylists()).thenReturn(flow {
            emit(Result.success(playlistsRaw))
        })
        whenever(mapper.invoke(playlistsRaw)).thenReturn(playlists)
        return PlaylistRepository(service, mapper)
    }
}