package com.example.azatsepin.theguardianreader.ui;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.azatsepin.theguardianreader.DetailsActivity;
import com.example.azatsepin.theguardianreader.MainActivity;
import com.example.azatsepin.theguardianreader.R;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

//make sure u disable animations in developer options
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void init(){
        Intents.init();
    }

    @Test
    public void ensureDetailsActivityStarts() {
        onView(withId(R.id.recyclerView)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, click()));
        intended(hasComponent(DetailsActivity.class.getName()));
    }

    @Test
    public void ensureElementAddedToRecyclerView() {
        onView(withId(R.id.action_bookmarks))
                .perform(click());
        RecyclerView recyclerViewSaved = mActivityRule.getActivity().findViewById(R.id.recyclerViewSaved);
        int itemCountBefore = recyclerViewSaved.getAdapter().getItemCount();


        onView(withId(R.id.action_explore))
                .perform(click());

        if (itemCountBefore > 0) {
            onView(withId(R.id.recyclerView)).perform(
                    RecyclerViewActions.actionOnItemAtPosition(0, clickChildViewWithId(R.id.action_save)));
            onView(withId(R.id.action_bookmarks))
                    .perform(click());
            assertEquals(recyclerViewSaved.getAdapter().getItemCount(), itemCountBefore + 1);
        } else {
            fail("There is no saved");
        }
    }

    @Test
    public void ensureElementDeletesFromActivity() {
        onView(withId(R.id.action_bookmarks))
                .perform(click());
        RecyclerView recyclerView = mActivityRule.getActivity().findViewById(R.id.recyclerViewSaved);
        int itemCountBefore = recyclerView.getAdapter().getItemCount();
        if (itemCountBefore > 0) {

            onView(withId(R.id.recyclerViewSaved)).perform(
                    RecyclerViewActions.actionOnItemAtPosition(0, click()));

            onView(withId(R.id.action_save)).perform(click());

            Espresso.pressBack();

            assertEquals(recyclerView.getAdapter().getItemCount(), itemCountBefore + 1);
        }
        else {
            fail("There is no saved albums");
        }
    }

    @After
    public void release(){
        Intents.release();
    }

    public static ViewAction clickChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                v.performClick();
            }
        };
    }
}
