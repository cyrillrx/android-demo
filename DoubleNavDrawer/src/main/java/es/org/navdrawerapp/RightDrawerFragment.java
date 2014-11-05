package es.org.navdrawerapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RightDrawerFragment extends AbstractNavigationDrawerFragment {

    private static final String PREF_USER_LEARNED_DRAWER = "right_navigation_drawer_learned";

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private DrawerLayout.DrawerListener mDrawerListener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) { }

        @Override
        public void onDrawerOpened(View drawerView) {
            if (!isAdded()) {
                return;
            }
            if (!mUserLearnedDrawer) {
                // The user manually opened the drawer; store this flag to prevent auto-showing
                // the navigation drawer automatically in the future.
                mUserLearnedDrawer = true;
                SharedPreferences sp = PreferenceManager
                        .getDefaultSharedPreferences(getActivity());
                sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
            }

            getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            if (!isAdded()) {
                return;
            }
            getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
        }

        @Override
        public void onDrawerStateChanged(int newState) { }
    };

    public RightDrawerFragment() { }

    @Override
    protected String getUserLearnDrawerKey() {
        return PREF_USER_LEARNED_DRAWER;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mDrawerListView = (ListView) inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
        mDrawerListView.setAdapter(new ArrayAdapter<>(
                getActionBar().getThemedContext(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                new String[]{
                        getString(R.string.title_section4),
                        getString(R.string.title_section5),
                        getString(R.string.title_section6),
                }));
        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
        return mDrawerListView;
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    @Override
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow_right, Gravity.END);
        // set up the drawer's list view with items and click listener

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }
        mDrawerLayout.setDrawerListener(mDrawerListener);
    }

    @Override
    protected void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerListView != null) {
            mDrawerListView.setItemChecked(position, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
    }
}
