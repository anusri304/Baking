//package com.example.baking;
//
//import androidx.test.espresso.intent.Intents;
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import java.util.regex.Matcher;
//
//@RunWith(AndroidJUnit4.class)
//public class RecipeStepIntentTest {
//
//    @Test
//    public void testIntents(){
//        Intents.init();
//        Matcher expectedIntent = allOf(hasAction(Intent.ACTION_VIEW), hasData(correct_url));
//        intending(expectedIntent).respondWith(new Instrumentation.ActivityResult(0, null));
//        onView(withId(R.id.button)).perform(click());
//        intended(expectedIntent);
//        Intents.release();
//    }
//}
