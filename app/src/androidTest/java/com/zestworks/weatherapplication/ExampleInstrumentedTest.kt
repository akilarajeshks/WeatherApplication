package com.zestworks.weatherapplication

import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import androidx.test.runner.AndroidJUnit4
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.zestworks.weatherapplication.repository.OkHttpSingleton.okHttpClient
import com.zestworks.weatherapplication.view.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {


    @Rule
    @JvmField
    val grantPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_COARSE_LOCATION)

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)


    private val idlingResource = OkHttp3IdlingResource.create("okhttp", okHttpClient)

    @Before
    fun registerIdling() {
        IdlingRegistry.getInstance().register(idlingResource)
    }

    @After
    fun unRegisterIdling() {
        IdlingRegistry.getInstance().unregister(idlingResource)
    }


    @Test
    fun check_ui_loaded(){
        Espresso.onView(ViewMatchers.withId(R.id.forecast_recycler)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.temperature)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.city_text_view)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
