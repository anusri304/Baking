package com.example.baking;

import android.content.Context;
import android.content.Intent;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import com.example.baking.activity.RecipeInstructionActivity;
import com.example.baking.activity.RecipeStepActivity;
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
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RecipeStepIntentTest {

    /**
     * This test demos a user clicking on a Recycler View item in RecipeStepActivity which opens up the
     * corresponding RecipeInstructionActivity. This test is used to test that the launched intent has correct data.
     */

    RecipeStepActivity recipeStepActivity;


    @Rule
    public ActivityTestRule<RecipeStepActivity> activityRule = new ActivityTestRule<>(RecipeStepActivity.class, true, false);

    @Before
    public void setUp() throws Exception {
        Intents.init();
    }

    /**
     * Clicks on a Recycler view item and check the intent details are correct.
     * As the intents are launched only for phones the check is made that the test runs on a phone and not on a tablet
     */

    @Test
    public void clickRecyclerViewItem_AndCheckIntent()  {
        Context targetContext = InstrumentationRegistry.getInstrumentation()
                .getTargetContext();
        Boolean isTabletUsed = BakingUtils.isTablet(targetContext);
        if (!isTabletUsed) {
            Intent result = new Intent(targetContext, RecipeStepActivity.class);
            result.putExtra(ApplicationConstants.RECIPE, BakingUtils.setUpMock());
            activityRule.launchActivity(result);

            onView(withId(R.id.rv_step_desc))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
            intended(hasComponent(RecipeInstructionActivity.class.getName()));
        }

    }

    @After
    public void tearDown() {
        Intents.release();
    }
}
