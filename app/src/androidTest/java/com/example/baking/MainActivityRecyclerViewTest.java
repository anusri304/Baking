
package com.example.baking;


import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import com.example.baking.activity.MainActivity;
import com.example.baking.utils.ApplicationConstants;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;

/**
 * This test demos a user clicking on a Recycler View item item in MainActivity which opens up the
 * corresponding RecipeStepActivity. This test is used to check that the RecipeStepActivity has the correct data.
 */


@RunWith(AndroidJUnit4.class)
public class MainActivityRecyclerViewTest {

    /**
     * The ActivityTestRule is a rule provided by Android used for functional testing of a single
     * activity. The activity that will be tested will be launched before each test that's annotated
     * with @Test and before methods annotated with @Before. The activity will be terminated after
     * the test and methods annotated with @After are complete. This rule allows you to directly
     * access the activity during the test.
     */
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getEspressoIdlingResourceForMainActivity();
        // To prove that the test fails, omit this call:
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    /**
     * This method is used to test that the recycler view item for the  main activity has the correct data
     */
    @Test
    public void loadRecipeRecyclerView() {
        onView(withRecyclerView(R.id.rv_recipe).atPosition(0))
                .check(matches(hasDescendant(withText(ApplicationConstants.NUTELLA_PIE))));
    }

    /**
     * This method is used to test that the on click of recycler view for the  main activity launches the RecipeStepActivity
     *  with correct data
     */

    @Test
    public void clickGridViewItem_OpensRecipeStepActivity() throws Exception {
        onView(withId(R.id.rv_recipe))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click())); //click on first item

        onView(withId(R.id.ingredientTxtView))
                .check(matches(withText(ApplicationConstants.INGREDIENTS)));

        onView(withText(ApplicationConstants.INGREDIENTS)).check(matches(isDisplayed()));
    }

    @After
    public void deRegisterIdlingResource() {
        //mIdlingResource = mActivityTestRule.getActivity().getEspressoIdlingResourceForMainActivity();
        // To prove that the test fails, omit this call:
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }


}