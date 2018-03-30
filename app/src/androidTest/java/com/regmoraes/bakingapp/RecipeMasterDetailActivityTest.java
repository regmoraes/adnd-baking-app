package com.regmoraes.bakingapp;

import android.content.Intent;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.regmoraes.bakingapp.data.model.Recipe;
import com.regmoraes.bakingapp.data.model.Step;
import com.regmoraes.bakingapp.presentation.recipe_detail.RecipeMasterDetailActivity;
import com.regmoraes.bakingapp.presentation.step_detail.StepDetailActivity;
import com.regmoraes.bakingapp.util.InstrumentationTestDataMock;
import com.regmoraes.bakingapp.util.RecyclerViewItemCountAssertion;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.Is.is;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RecipeMasterDetailActivityTest {

    private Recipe recipe;

    @Rule
    public IntentsTestRule<RecipeMasterDetailActivity> mActivityRule =
            new IntentsTestRule<RecipeMasterDetailActivity>(
                    RecipeMasterDetailActivity.class, false, true) {

                @Override
                protected Intent getActivityIntent() {

                    recipe = InstrumentationTestDataMock.getRecipeDataMock();

                    Intent intent = new Intent();
                    intent.putExtra(Recipe.class.getSimpleName(), recipe);

                    return intent;
                }
            };

    @Test
    public void all_steps_areShown() {

        int stepsCount = recipe.getSteps().size();

        onView(withId(R.id.recyclerView_steps))
                .check(new RecyclerViewItemCountAssertion(stepsCount));
    }

    @Test
    public void all_ingredients_areShown() {

        int ingredientsCount = recipe.getIngredients().size();

        onView(withId(R.id.recyclerView_ingredients))
                .check(new RecyclerViewItemCountAssertion(ingredientsCount));
    }

    @Test
    public void click_on_step_opens_StepDetail() {

        int position = 0;
        Step step = recipe.getSteps().get(position);

        onView(withId(R.id.recyclerView_steps))
                .perform(RecyclerViewActions.actionOnItemAtPosition(position, click()));

        try {

            // Check if Step Detail is show on Tablet layouts
            onView(withText(step.getDescription())).check(matches(isDisplayed()));

        } catch (NoMatchingViewException exception) {

            // Check if Step Detail is show on Phone layouts
            intended(allOf(hasComponent(StepDetailActivity.class.getName()), isInternal()));
        }
    }

    @Test
    public void show_player_according_to_videoUrl_presence() {

        int stepWithVideoPosition = -1;

        for (int i = 0; i < recipe.getSteps().size(); i++) {

            if (recipe.getSteps().get(i).getVideoURL() != null
                    && !recipe.getSteps().get(i).getVideoURL().isEmpty()) {

                stepWithVideoPosition = i;
                break;
            }
        }

        onView(withId(R.id.recyclerView_steps))
                .perform(RecyclerViewActions.actionOnItemAtPosition(stepWithVideoPosition, click()));

        try {

            ViewInteraction playerInteraction =
                    onView(allOf(withId(R.id.playerView),
                    withClassName(is(SimpleExoPlayer.class.getName()))));

            // Check if Step Detail is show on Tablet layouts
            if (stepWithVideoPosition >= 0) {
                playerInteraction.check(matches(isDisplayed()));
            } else {
                playerInteraction.check(matches(isDisplayed()));
            }

        } catch (NoMatchingViewException exception) {

            // Check if Step Detail is show on Phone layouts
            intended(allOf(hasComponent(StepDetailActivity.class.getName()), isInternal()));
        }

    }
}
