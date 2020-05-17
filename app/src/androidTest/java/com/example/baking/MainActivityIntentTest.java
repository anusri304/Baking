
package com.example.baking;


import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.baking.activity.MainActivity;
import com.example.baking.activity.bean.RecipePresentationBean;
import com.example.baking.bean.Ingredient;
import com.example.baking.bean.Step;
import com.example.baking.utils.ApplicationConstants;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * This test demos a user clicking on a GridView item in MenuActivity which opens up the
 * corresponding OrderActivity.
 * <p>
 * This test does not utilize Idling Resources yet. If idling is set in the MenuActivity,
 * then this test will fail. See the IdlingResourcesTest for an identical test that
 * takes into account Idling Resources.
 */


@RunWith(AndroidJUnit4.class)
public class MainActivityIntentTest {

    /**
     * The ActivityTestRule is a rule provided by Android used for functional testing of a single
     * activity. The activity that will be tested will be launched before each test that's annotated
     * with @Test and before methods annotated with @Before. The activity will be terminated after
     * the test and methods annotated with @After are complete. This rule allows you to directly
     * access the activity during the test.
     */
    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = intentsTestRule.getActivity().getEspressoIdlingResourceForMainActivity();
        // To prove that the test fails, omit this call:
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    /**
     * Clicks on a GridView item and checks it opens up the OrderActivity with the correct details.
     */

    @Test
    public void clickRecyclerViewItem_OpensRecipeStepActivity1() throws Exception {

        onView(withId(R.id.rv_recipe))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click())); //click on first item

        intended(allOf(
                hasExtra(ApplicationConstants.RECIPE, setUpMock()),
                hasComponent("com.example.baking.activity.RecipeStepActivity")));


    }

    public RecipePresentationBean setUpMock() {
        RecipePresentationBean recipePresentationBean = new RecipePresentationBean();
        recipePresentationBean.setId(1);
        recipePresentationBean.setName("Nutella Pie");
        recipePresentationBean.setImage("");
        recipePresentationBean.setServing("Serves: 8");

        List<Ingredient> ingredientList = new ArrayList<Ingredient>();

        Ingredient ingredient = new Ingredient();
        ingredient.setQuantity(2.0);
        ingredient.setMeasure("CUP");
        ingredient.setName("Graham Cracker crumbs");
        ingredientList.add(ingredient);

        ingredient = new Ingredient();
        ingredient.setQuantity(6.0);
        ingredient.setMeasure("TBLSP");
        ingredient.setName("unsalted butter, melted");
        ingredientList.add(ingredient);

        ingredient = new Ingredient();
        ingredient.setQuantity(0.5);
        ingredient.setMeasure("CUP");
        ingredient.setName("granulated sugar");
        ingredientList.add(ingredient);

        ingredient = new Ingredient();
        ingredient.setQuantity(1.5);
        ingredient.setMeasure("TSP");
        ingredient.setName("salt");
        ingredientList.add(ingredient);

        ingredient = new Ingredient();
        ingredient.setQuantity(5.0);
        ingredient.setMeasure("TBLSP");
        ingredient.setName("vanilla");
        ingredientList.add(ingredient);

        ingredient = new Ingredient();
        ingredient.setQuantity(1.0);
        ingredient.setMeasure("K");
        ingredient.setName("Nutella or other chocolate-hazelnut spread");
        ingredientList.add(ingredient);

        ingredient = new Ingredient();
        ingredient.setQuantity(500.0);
        ingredient.setMeasure("G");
        ingredient.setName("Mascapone Cheese(room temperature)");
        ingredientList.add(ingredient);

        ingredient = new Ingredient();
        ingredient.setQuantity(1.0);
        ingredient.setMeasure("CUP");
        ingredient.setName("heavy cream(cold)");
        ingredientList.add(ingredient);

        ingredient = new Ingredient();
        ingredient.setQuantity(4.0);
        ingredient.setMeasure("OZ");
        ingredient.setName("cream cheese(softened)");
        ingredientList.add(ingredient);

        List<Step> steps = new ArrayList<Step>();

        Step step = new Step();
        step.setId(0);
        step.setShortDescription("Recipe Introduction");
        step.setInstruction("Recipe Introduction");
        step.setVideoURL("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4");
        step.setThumbnailURL("");
        steps.add(step);

        step = new Step();
        step.setId(1);
        step.setShortDescription("Starting prep");
        step.setInstruction("1. Preheat the oven to 350°F. Butter a 9\" deep dish pie pan.");
        step.setVideoURL("");
        step.setThumbnailURL("");
        steps.add(step);

        step = new Step();
        step.setId(2);
        step.setShortDescription("Prep the cookie crust.");
        step.setInstruction("2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.");
        step.setVideoURL("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9a6_2-mix-sugar-crackers-creampie/2-mix-sugar-crackers-creampie.mp4");
        step.setThumbnailURL("");
        steps.add(step);

        step = new Step();
        step.setId(3);
        step.setShortDescription("Press the crust into baking form.");
        step.setInstruction("3. Press the cookie crumb mixture into the prepared pie pan and bake for 12 minutes. Let crust cool to room temperature.");
        step.setVideoURL("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9cb_4-press-crumbs-in-pie-plate-creampie/4-press-crumbs-in-pie-plate-creampie.mp4");
        step.setThumbnailURL("");
        steps.add(step);


        step = new Step();
        step.setId(4);
        step.setShortDescription("Start filling prep");
        step.setInstruction("4. Beat together the nutella, mascarpone, 1 teaspoon of salt, and 1 tablespoon of vanilla on medium speed in a stand mixer or high speed with a hand mixer until fluffy.");
        step.setVideoURL("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd97a_1-mix-marscapone-nutella-creampie/1-mix-marscapone-nutella-creampie.mp4");
        step.setThumbnailURL("");
        steps.add(step);

        step = new Step();
        step.setId(5);
        step.setShortDescription("Finish filling prep");
        step.setInstruction("5. Beat the cream cheese and 50 grams (1/4 cup) of sugar on medium speed in a stand mixer or high speed with a hand mixer for 3 minutes. Decrease the speed to medium-low and gradually add in the cold cream. Add in 2 teaspoons of vanilla and beat until stiff peaks form.");
        step.setVideoURL("");
        step.setThumbnailURL("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda20_7-add-cream-mix-creampie/7-add-cream-mix-creampie.mp4");
        steps.add(step);

        step = new Step();
        step.setId(6);
        step.setShortDescription("Finishing Steps");
        step.setInstruction("6. Pour the filling into the prepared crust and smooth the top. Spread the whipped cream over the filling. Refrigerate the pie for at least 2 hours. Then it's ready to serve!");
        step.setVideoURL("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda45_9-add-mixed-nutella-to-crust-creampie/9-add-mixed-nutella-to-crust-creampie.mp4");
        step.setThumbnailURL("");
        steps.add(step);

        recipePresentationBean.setSteps(steps);
        recipePresentationBean.setIngredients(ingredientList);
        return recipePresentationBean;


    }

}