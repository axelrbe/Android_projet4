package com.example.lamzone;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNull.notNullValue;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import android.view.KeyEvent;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.lamzone.DI.DI;
import com.example.lamzone.Model.Meeting;
import com.example.lamzone.Service.MeetingApiService;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;


/**
 * Test class for list of meeting
 */

@RunWith(AndroidJUnit4.class)
public class MeetingInstrumentedTest {

    private MeetingApiService meetingApiService;
    private List<Meeting> allMeetings;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        MainActivity activity = mActivityRule.getActivity();
        assertThat(activity, notNullValue());
        meetingApiService = DI.getMeetingApiService();
        allMeetings = meetingApiService.getAllMeetings();
    }

    @After
    public void clean() {
        for (Meeting meeting : allMeetings) {
            meetingApiService.deleteMeeting(meeting);
        }
    }

    @Test
    public void createMeeting_ShouldAddOneItemToMeetingList() {
        // Clique sur le bouton d'ajout de meeting
        onView(withId(R.id.add_meeting_btn)).perform(click());
        // Ajouter un participant
        onView(withId(R.id.participants)).perform(typeText("Test1@Lamzone.fr")).perform(pressKey(KeyEvent.KEYCODE_ENTER));
        onView(withId(R.id.participants)).perform(typeText("Test2@Lamzone.fr")).perform(pressKey(KeyEvent.KEYCODE_ENTER));
        // Séléctionner une salle
        onView(withId(R.id.show_spinner_btn)).perform(click());
        //Séléctionner une date
        onView(withId(R.id.textview_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform((ViewAction) PickerActions.setDate(2023,11,11));
        onView(withId(android.R.id.button1)).perform(click());
        //Séléctionner une heure
        onView(withId(R.id.textview_timing)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform((ViewAction) PickerActions.setTime(16,30));
        onView(withId(android.R.id.button1)).perform(click());
        //Ajouter un sujet
        onView(withId(R.id.editText_subject)).perform(replaceText("Sujet"), ViewActions.closeSoftKeyboard());
        // Sauvegarder le meeting
        onView(withId(R.id.save_meeting_btn)).perform(click());
        // Vérifier que la liste de meeting contient bien un élément
        onView(withId(R.id.recyclerView)).check(matches(hasChildCount(1)));
    }

    @Test
    public void createMeeting_ShouldThrowAnErrorIfAfieldIsMissing() {
        // Clique sur le bouton d'ajout de meeting
        onView(withId(R.id.add_meeting_btn)).perform(click());
        // Ajouter un participant
        onView(withId(R.id.participants)).perform(typeText("Test1@Lamzone.fr")).perform(pressKey(KeyEvent.KEYCODE_ENTER));
        onView(withId(R.id.participants)).perform(typeText("Test2@Lamzone.fr")).perform(pressKey(KeyEvent.KEYCODE_ENTER));
        // Séléctionner une salle
        onView(withId(R.id.show_spinner_btn)).perform(click());
        //Séléctionner une date
        onView(withId(R.id.textview_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform((ViewAction) PickerActions.setDate(2023,11,11));
        onView(withId(android.R.id.button1)).perform(click());
        //Séléctionner une heure
        onView(withId(R.id.textview_timing)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform((ViewAction) PickerActions.setTime(16,30));
        onView(withId(android.R.id.button1)).perform(click());
        // Ajouter le meeting sans renseigner de sujet
        onView(withId(R.id.save_meeting_btn)).perform(click());
        // Vérifier que l'icone warning et le toast s'affiche bien
        onView(withId(R.id.warning_subject_icon)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void deleteMeeting_ShouldDeleteOneItemToMeetingList() {
        // Ajouter un meeting avec la première méthode
        createMeeting_ShouldAddOneItemToMeetingList();
        onView(withId(R.id.recyclerView)).check(matches(hasChildCount(1)));
        // Cliquer sur le boutton de suppression d'un meeting
        onView(withId(R.id.delete_btn)).perform(click());
        // Vérifier que la liste de meeting ne contient plus aucun meeting
        onView(withId(R.id.recyclerView)).check(matches(hasChildCount(0)));
    }

    @Test
    public void filterByDate_ShouldFilterTheListByDateOfCreation() {
        // Ajouter deux meetings avec la première méthode
        createMeeting_ShouldAddOneItemToMeetingList();
        // Lancer le filtre par date
        onView(withId(R.id.icon_filtered_list)).perform(click());
        onView(withText("Filtrer par date")).perform(click());
        // Séléctionner le même date que celle du meeting
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform((ViewAction) PickerActions.setDate(2023,11,11));
        onView(withId(android.R.id.button1)).perform(click());
        // Vérifier que la liste contient bien un élément
        onView(withId(R.id.recyclerView)).check(matches(hasChildCount(1)));
        // Relancer le filtre par date
        onView(withId(R.id.icon_filtered_list)).perform(click());
        onView(withText("Filtrer par date")).perform(click());
        // Séléctionner une date différente de celle du meeting
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform((ViewAction) PickerActions.setDate(2023,12,23));
        onView(withId(android.R.id.button1)).perform(click());
        // Vérifier que la liste de meeting ne contient plus aucun élément
        onView(withId(R.id.recyclerView)).check(matches(hasChildCount(0)));
        // Cliquer sur le bouton reset
        onView(withId(R.id.reset_filter_btn)).perform(click());
        // Vérifier que le meeting réapparait dans la liste
        onView(withId(R.id.recyclerView)).check(matches(hasChildCount(1)));
    }

    @Test
    public void filterByRoom_ShouldFilterTheListByRoomOfCreation() {
        // Ajouter deux meetings avec la première méthode
        createMeeting_ShouldAddOneItemToMeetingList();
        // Lancer le filtre par date
        onView(withId(R.id.icon_filtered_list)).perform(click());
        onView(withText("Filtrer par salle")).perform(click());
        // Séléctionner la même salle
        onView(withId(R.id.filter_search_view)).perform(typeText("Goomba"));
        // Vérifier que la liste de meeting ne contient plus aucun élément
        onView(withId(R.id.recyclerView)).check(matches(hasChildCount(1)));
        // Relancer le filtre par date
        onView(withId(R.id.icon_filtered_list)).perform(click());
        onView(withText("Filtrer par salle")).perform(click());
        // Séléctionner une salle différente
        onView(withId(R.id.filter_search_view)).perform(typeText("Mario"));
        // Vérifier que la liste de meeting ne contient plus aucun élément
        onView(withId(R.id.recyclerView)).check(matches(hasChildCount(0)));
        // Cliquer sur le bouton reset
        onView(withId(R.id.reset_filter_btn)).perform(click());
        // Vérifier que le meeting réapparait dans la liste
        onView(withId(R.id.recyclerView)).check(matches(hasChildCount(1)));
    }
}