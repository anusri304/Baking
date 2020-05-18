
package com.example.baking;


import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.baking.activity.MainActivity;
import com.example.baking.utils.ApplicationConstants;
import com.example.baking.utils.BakingUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

/**
 * This test demos a user clicking on a Recycler View item in MainActivity which opens up the
 * corresponding RecipeStepActivity. This test is used to test that the launched intent has correct data.
 */


@RunWith(AndroidJUnit4.class)
public class MainActivityIntentTest {

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = intentsTestRule.getActivity().getEspressoIdlingResourceForMainActivity();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    /**
     * Clicks on a Recycler view item and check the intent details are correct.
     */

    @Test
    public void clickRecyclerViewItem_AndCheckIntent() throws Exception {

        onView(withId(R.id.rv_recipe))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click())); //click on first item

        intended(allOf(
                hasExtra(ApplicationConstants.RECIPE, BakingUtils.setUpMock()),
                hasComponent("com.example.baking.activity.RecipeStepActivity"))); // Launch RecipeStepActivity


    }

    @After
    public void deRegisterIdlingResource() {
        //mIdlingResource = mActivityTestRule.getActivity().getEspressoIdlingResourceForMainActivity();
        // To prove that the test fails, omit this call:
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }


}