package petros.efthymiou.groovy

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotExist
import org.hamcrest.Matchers
import org.junit.Test
import org.junit.runner.RunWith
import petros.efthymiou.groovy.playlist.idlingResource

@RunWith(AndroidJUnit4::class)
class PlaylistDetailsFeature : BaseUiTest() {
    @Test
    fun displaysPlaylistNameAndDetails() {
        navigateToPlaylistDetail(0)

        assertDisplayed("Hard Rock Cafe")
        assertDisplayed("Rock your senses with this timeless signature vibe list. \n\n • Poison \n • You shook me all night \n • Zombie \n • Rock'n Me \n • Thunderstruck \n • I Hate Myself for Loving you \n • Crazy \n • Knockin' on Heavens Door")
    }

    @Test
    fun displaysLoaderWhileFetchingThePlaylistDetail() {
        IdlingRegistry.getInstance().unregister(idlingResource)
        Thread.sleep(2000)
        navigateToPlaylistDetail(0)

        assertDisplayed(R.id.details_loader)
    }

    @Test
    fun hidesLoader() {
        navigateToPlaylistDetail(0)

        assertNotDisplayed(R.id.details_loader)
    }

    @Test
    fun displaysErrorMessageWhenNetworkFails() {
        navigateToPlaylistDetail(1)

        assertDisplayed(R.string.generic_error)
    }

    @Test
    fun hidesErrorMessage() {
        navigateToPlaylistDetail(1)
        Thread.sleep(3000)

        assertNotExist(R.string.generic_error) // completely removed from the view
    }

    private fun navigateToPlaylistDetail(row: Int) {
        onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.playlist_image),
                ViewMatchers.isDescendantOfA(nthChildOf(ViewMatchers.withId(R.id.playlists_list), row))
            )
        ).perform(ViewActions.click())
    }
}