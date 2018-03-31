package com.regmoraes.bakingapp;

import android.content.Intent;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.regmoraes.bakingapp.data.model.Recipe;
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
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

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
    public void all_steps_ingredients_and_headers_areShown() {

        int stepsCount = recipe.getSteps().size();
        int ingredientsCount = recipe.getIngredients().size();
        int headersCount = 1;
        int totalItems = stepsCount +ingredientsCount + headersCount;

        onView(withId(R.id.recyclerView_ingredients_and_steps))
                .check(new RecyclerViewItemCountAssertion(totalItems));
    }

    @Test
    public void click_on_step_opens_StepDetail() {

        int ingredientsCount = recipe.getIngredients().size();
        int headersCount = 1;

        int stepRealPosition = 0;

        int stepPositionOnList = (ingredientsCount + headersCount) + stepRealPosition;

        onView(withId(R.id.recyclerView_ingredients_and_steps))
                .perform(RecyclerViewActions.actionOnItemAtPosition(stepPositionOnList, click()));

        boolean isTablet = getResources().getBoolean(R.bool.isTablet);

        if(isTablet) {

            onView(allOf(
                    withId(R.id.textView_step_description),
                    withText(recipe.getSteps().get(stepRealPosition).getDescription())
            )).check(matches(isDisplayed()));

        } else {
            intended(allOf(hasComponent(StepDetailActivity.class.getName()), isInternal()));
        }
    }

    @Test
    public void show_player_according_to_videoUrl_presence() {

        int ingredientsCount = recipe.getIngredients().size();
        int headersCount = 1;

        int stepWithVideoPosition = -1;

        for (int i = 0; i < recipe.getSteps().size(); i++) {

            if (recipe.getSteps().get(i).getVideoURL() != null
                    && !recipe.getSteps().get(i).getVideoURL().isEmpty()) {

                stepWithVideoPosition = (ingredientsCount + headersCount) + i;
                break;
            }
        }

        onView(withId(R.id.recyclerView_ingredients_and_steps))
                .perform(RecyclerViewActions.actionOnItemAtPosition(stepWithVideoPosition, click()));

        boolean isTablet = getResources().getBoolean(R.bool.isTablet);

        if(isTablet) {

            ViewInteraction playerInteraction = onView(withId(R.id.playerView));


            if (stepWithVideoPosition >= 0) {
                playerInteraction.check(matches(isDisplayed()));
            } else {
                playerInteraction.check(matches(not(isDisplayed())));
            }

        } else {

            intended(allOf(hasComponent(StepDetailActivity.class.getName()), isInternal()));
        }

    }

    private Resources getResources() {
        return InstrumentationRegistry.getTargetContext().getResources();
    }
}
