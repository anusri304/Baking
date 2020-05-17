
package com.example.baking;


import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import com.example.baking.activity.MainActivity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

/**
 * This test demos a user clicking on a GridView item in MenuActivity which opens up the
 * corresponding OrderActivity.
 * <p>
 * This test does not utilize Idling Resources yet. If idling is set in the MenuActivity,
 * then this test will fail. See the IdlingResourcesTest for an identical test that
 * takes into account Idling Resources.
 */


@RunWith(AndroidJUnit4.class)
public class MainActivityRecyclerViewTest {

    public static final String INGREDIENTS = "2 CUP Graham Cracker crumbs"
            .concat("\n")
            .concat("6 TBLSP unsalted butter, melted")
            .concat("\n")
            .concat("0,5 CUP granulated sugar")
            .concat("\n")
            .concat("1,5 TSP salt")
            .concat("\n")
            .concat("5 TBLSP vanilla")
            .concat("\n")
            .concat("1 K Nutella or other chocolate-hazelnut spread")
            .concat("\n")
            .concat("500 G Mascapone Cheese(room temperature)")
            .concat("\n")
            .concat("1 CUP heavy cream(cold)")
            .concat("\n")
            .concat("4 OZ cream cheese(softened)")
            .concat("\n");
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
     * Clicks on a GridView item and checks it opens up the OrderActivity with the correct details.
     */
    @Test
    public void clickGridViewItem_OpensRecipeStepActivity() throws Exception {

        onView(withId(R.id.rv_recipe))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click())); //click on first item

        onView(withId(R.id.ingredientTxtView))
                .check(matches(withText(INGREDIENTS)));


    }

}