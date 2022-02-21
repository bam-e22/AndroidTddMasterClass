package petros.efthymiou.groovy.details

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.times
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import petros.efthymiou.groovy.utils.BaseUnitTest
import petros.efthymiou.groovy.utils.captureValues
import petros.efthymiou.groovy.utils.getValueForTest

/**
 * 1. Create PlaylistDetailViewModelShould
 * 2. Mock PlaylistDetailService
 * 3. Verify that when we call getPlaylistDetails(id) from viewModel
 * and getValueForTest() we also call fetchPlaylistDetails(id) from service
 * 4. The id parameter must be the same
 *
 */
class PlaylistDetailViewModelShould : BaseUnitTest() {
    private lateinit var viewModel: PlaylistDetailViewModel
    private val id = "1"
    private val service: PlaylistDetailService = mock()
    private val playlistDetail: PlaylistDetail = mock()
    private val expected = Result.success(playlistDetail)
    private val exception = RuntimeException("Something went wrong")
    private val error = Result.failure<PlaylistDetail>(exception)

    @Test
    fun getPlaylistDetailFromRepository() = runBlockingTest {
        mockSuccessfulCase()
        viewModel.getPlaylistDetail(id)
        viewModel.playlistDetail.getValueForTest()
        verify(service, times(1)).fetchPlaylistDetail(id)
    }

    @Test
    fun emitPlaylistDetailFromService() = runBlockingTest {
        mockSuccessfulCase()
        viewModel.getPlaylistDetail(id)
        assertEquals(expected, viewModel.playlistDetail.getValueForTest())
    }

    @Test
    fun emitErrorWhenServiceFails() = runBlockingTest {
        mockFailureCase()
        assertEquals(error, viewModel.playlistDetail.getValueForTest())
    }

    @Test
    fun showLoaderWhileLoading() = runBlockingTest {
        mockSuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.getPlaylistDetail(id)
            viewModel.playlistDetail.getValueForTest()
            Assert.assertEquals(true, values[0])
        }
    }

    @Test
    fun closeLoaderAfterPlaylistDetailLoad() = runBlockingTest {
        mockSuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.getPlaylistDetail(id)
            viewModel.playlistDetail.getValueForTest()
            Assert.assertEquals(false, values.last())
        }
    }

    private suspend fun mockFailureCase() {
        whenever(service.fetchPlaylistDetail(id)).thenReturn(
            flow {
                emit(error)
            }
        )
        viewModel = PlaylistDetailViewModel(service)
        viewModel.getPlaylistDetail(id)
    }

    private suspend fun mockSuccessfulCase() {
        whenever(service.fetchPlaylistDetail(id)).thenReturn(
            flow {
                emit(expected)
            }
        )
        viewModel = PlaylistDetailViewModel(service)
    }
}