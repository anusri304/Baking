package com.example.baking;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import com.example.baking.activity.MainActivity;
import com.example.baking.activity.RecipeStepActivity;
import com.example.baking.utils.ApplicationConstants;
import com.example.baking.utils.BakingUtils;
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
    public ActivityTestRule<RecipeStepActivity> mActivityRule =
            new ActivityTestRule<RecipeStepActivity>(RecipeStepActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();
                    Intent result = new Intent(targetContext, RecipeStepActivity.class);
                    result.putExtra(ApplicationConstants.RECIPE, BakingUtils.setUpMock());
                    return result;
                }
            };


    @Test
    public void clickRecyclerViewItem_OpensRecipeInstructionActivity() throws Exception {

        onView(withId(R.id.rv_step_desc))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click())); //click on first item

        onView(withId(R.id.instructionTxtView))
                .check(matches(withText(RECIPE_INSTRUCTION_INTRODUCTION)));


    }

}
