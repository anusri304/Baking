package com.example.baking;

import android.app.Activity;
import android.app.Instrumentation;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import com.example.baking.activity.MainActivity;
import com.example.baking.activity.RecipeStepActivity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class RecipeStepActivityRecyclerViewTest {

    public static final String RECIPE_INSTRUCTION_INTRODUCTION = "Recipe Introduction";

    @Rule
    public IntentsTestRule<RecipeStepActivity> mActivityTestRule = new IntentsTestRule<>(RecipeStepActivity.class);

    @Before
    public void stubAllExternalIntents() {
        // By default Espresso Intents does not stub any Intents. Stubbing needs to be setup before
        // every test run. In this case all external Intents will be blocked.
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }



    @Test
    public void clickRecyclerViewItem_OpensRecipeInstructionActivity() throws Exception {

        onView(withId(R.id.rv_step_desc))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click())); //click on first item

        onView(withId(R.id.instructionTxtView))
                .check(matches(withText(RECIPE_INSTRUCTION_INTRODUCTION)));


    }

}
