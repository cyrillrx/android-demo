package com.cyrillrx.android.demo.drawer;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.cyrillrx.android.demo.R;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public abstract class AbstractNavigationDrawerFragment extends Fragment {

    /**
     * Remember the position of the selected item.
     */
    protected static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    protected NavigationDrawerCallbacks callbacks;

    protected DrawerLayout drawerLayout;
    protected ListView drawerListView;
    protected View fragmentContainerView;

    protected int currentSelectedPosition = 0;
    protected boolean fromSavedInstanceState;

    public AbstractNavigationDrawerFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            currentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            fromSavedInstanceState = true;
        }

        // Select either the default item (0) or the last selected item.
        selectItem(currentSelectedPosition);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, currentSelectedPosition);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (drawerLayout != null && isDrawerOpen()) {
            inflater.inflate(R.menu.global, menu);
            showGlobalContextActionBar();
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_example) {
            if (drawerLayout.isDrawerVisible(Gravity.END)) {
                drawerLayout.closeDrawer(Gravity.END);
            } else {
                // Close the left drawer if necessary
                if (drawerLayout.isDrawerVisible(Gravity.START)) {
                    drawerLayout.closeDrawer(Gravity.START);
                }
                drawerLayout.openDrawer(Gravity.END);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean isDrawerOpen() {
        return drawerLayout != null && drawerLayout.isDrawerOpen(fragmentContainerView);
    }

    protected abstract void selectItem(int position);

    /**
     * Per the navigation drawer design guidelines, updates the action bar to show the global app
     * 'context', rather than just what's in the current screen.
     */
    protected void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.app_name);
    }

    /**
     * Convenient method to get the action bar activity.
     *
     * @return The action bar activity.
     */
    protected ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public interface NavigationDrawerCallbacks {

        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position);
    }
}
