package petros.efthymiou.groovy

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adevinta.android.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.adevinta.android.barista.internal.matcher.DrawableMatcher.Companion.withDrawable
import org.hamcrest.Matchers.allOf
import org.junit.Test
import org.junit.runner.RunWith
import petros.efthymiou.groovy.playlist.idlingResource

@RunWith(AndroidJUnit4::class)
class PlayListFeature : BaseUiTest() {
    @Test
    fun displayScreenTitle() {  // TODO replace compose and compose test
        assertDisplayed("Playlists")
    }

    @Test
    fun displayListOfPlaylists() { // TODO replace compose and compose test
        //Thread.sleep(4000) // TODO: refactor this
        assertRecyclerViewItemCount(R.id.playlists_list, 10)

        onView(allOf(withId(R.id.playlist_name), isDescendantOfA(nthChildOf(withId(R.id.playlists_list), 0))))
            .check(matches(withText("Hard Rock Cafe")))
            .check(matches(isDisplayed()))

        onView(allOf(withId(R.id.playlist_category), isDescendantOfA(nthChildOf(withId(R.id.playlists_list), 0))))
            .check(matches(withText("rock")))
            .check(matches(isDisplayed()))

        onView(allOf(withId(R.id.playlist_image), isDescendantOfA(nthChildOf(withId(R.id.playlists_list), 0))))
            .check(matches(withDrawable(R.drawable.rock)))
            .check(matches(isDisplayed()))
    }

    @Test
    fun displayLoaderWhileFetchingThePlaylist() {
        IdlingRegistry.getInstance().unregister(idlingResource)
        assertDisplayed(R.id.loader)
    }

    @Test
    fun hidesLoader() {
        //Thread.sleep(4000) // TODO refactor this
        assertNotDisplayed(R.id.loader)
    }

    @Test
    fun displayRockImageForRockListItems() {
        //Thread.sleep(4000) // TODO: refactor this

        onView(allOf(withId(R.id.playlist_image), isDescendantOfA(nthChildOf(withId(R.id.playlists_list), 0))))
            .check(matches(withDrawable(R.drawable.rock)))
            .check(matches(isDisplayed()))

        onView(allOf(withId(R.id.playlist_image), isDescendantOfA(nthChildOf(withId(R.id.playlists_list), 3))))
            .check(matches(withDrawable(R.drawable.rock)))
            .check(matches(isDisplayed()))
    }

    @Test
    fun navigateToDetailScreen() {
        onView(allOf(withId(R.id.playlist_image), isDescendantOfA(nthChildOf(withId(R.id.playlists_list), 0))))
            .perform(click())

        assertDisplayed(R.id.playlists_detail_root)
    }

}