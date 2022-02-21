package petros.efthymiou.groovy.playlist

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import petros.efthymiou.groovy.utils.BaseUnitTest
import petros.efthymiou.groovy.utils.captureValues
import petros.efthymiou.groovy.utils.getValueForTest

class PlaylistViewModelShould : BaseUnitTest() {
    private val repository: PlaylistRepository = mock()
    private val playlists = mock<List<Playlist>>()
    private val expected = Result.success(playlists)
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getPlaylistsFromRepository() = runBlockingTest {
        val viewModel = mockSuccessfulCase() // 테스트 대상을 제외한 것들은 모두 mock

        viewModel.playlists.getValueForTest()
        verify(repository, times(1)).getPlayLists()
    }

    @Test
    fun emitPlaylistsFromRepository() = runBlockingTest {
        val viewModel = mockSuccessfulCase()

        assertEquals(expected, viewModel.playlists.getValueForTest())
    }

    @Test
    fun emitErrorWhenReceiveError() {
        val viewModel = mockFailureCase()

        assertEquals(exception, viewModel.playlists.getValueForTest()?.exceptionOrNull())
    }

    @Test
    fun showSpinnerWhileLoading() = runBlockingTest {
        val viewModel = mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.playlists.getValueForTest()
            assertEquals(true, values[0])
        }
    }

    @Test
    fun closeLoaderAfterPlaylistsLoad() = runBlockingTest {
        val viewModel = mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.playlists.getValueForTest()
            assertEquals(false, values.last())
        }
    }

    @Test
    fun closeLoaderAfterError() = runBlockingTest {
        val viewModel = mockFailureCase()

        viewModel.loader.captureValues {
            viewModel.playlists.getValueForTest()
            assertEquals(false, values.last())
        }
    }

    // 테스트 대상을 제외한 것들은 모두 mock
    private fun TestCoroutineScope.mockSuccessfulCase(): PlaylistViewModel {
        runBlockingTest {
            whenever(repository.getPlayLists()).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }
        return PlaylistViewModel(repository) // 테스트 대상을 제외한 것들은 모두 mock
    }

    private fun mockFailureCase(): PlaylistViewModel {
        runBlockingTest {
            whenever(repository.getPlayLists()).thenReturn(flow {
                emit(Result.failure(exception))
            })
        }
        val viewModel = PlaylistViewModel(repository)
        return viewModel
    }

}
