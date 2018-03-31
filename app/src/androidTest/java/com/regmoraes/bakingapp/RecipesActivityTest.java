package com.regmoraes.bakingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.regmoraes.bakingapp.presentation.recipe_detail.RecipeMasterDetailActivity;
import com.regmoraes.bakingapp.presentation.recipes.RecipesActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RecipesActivityTest {

    @Rule
    public IntentsTestRule<RecipesActivity> mActivityRule =
            new IntentsTestRule<RecipesActivity>(RecipesActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void setUp() {

        mIdlingResource = mActivityRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @After
    public void tearDown() {
        if (mIdlingResource != null){
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }

    @Test
    public void clickOnRecipe_opensRecipeDetails() {

        onView(withId(R.id.recyclerView_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        intended(allOf(hasComponent(RecipeMasterDetailActivity.class.getName()), isInternal()));
    }
}
