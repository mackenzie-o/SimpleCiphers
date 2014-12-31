package com.mackenzie_o.simpleciphers;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.ToggleButton;

import com.mackenzie_o.simpleciphers.ShiftCiphers;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    /**
     * Different fragments
     */
    private Fragment about = new AboutFragment(),
                     caesar = new CaesarFragment(), 
                     vigenere = new VigenereFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        
        fragmentManager.beginTransaction()
                .replace(R.id.container, getSelectedTab(position+1))
                .commit();
    }
    
    public Fragment getSelectedTab(int selection){
        switch (selection) {
            case 1:
                return about;
            case 2:
                return caesar;
            case 3:
                return vigenere;
            default:
                return null;
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
    public static void updateShift(String in, View fragment){
        int num;
        if(in.equals("")){
            num=1;
        }else{
            num = Integer.parseInt(in);
        }
        CharSequence updatedText = "A -> "+ShiftCiphers.getChar('A', num, true, false);
        TextView shiftIndicator = (TextView) fragment.findViewById(R.id.shiftIndicator);
        shiftIndicator.setText(updatedText);
        
    }
    
    public void computeCaesarShift(View view){
        EditText plainText = (EditText) findViewById(R.id.caesarPlainText);
        TextView cipherText = (TextView) findViewById(R.id.caesarCipherText);
        EditText shiftNum = (EditText) findViewById(R.id.shiftNum);
        ToggleButton capitals = (ToggleButton) findViewById(R.id.caseToggle);
        ToggleButton characters = (ToggleButton) findViewById(R.id.characterToggle);
        cipherText.setText(ShiftCiphers.caesarShift(plainText.getText().toString(),
                Integer.parseInt(shiftNum.getText().toString()),
                !capitals.isChecked(), !characters.isChecked()));
    }

    public static class AboutFragment extends Fragment {
        
        
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.about_fragment, container, false);
        }
        
        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(1);
        }
    }

    public static class CaesarFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View fragment = inflater.inflate(R.layout.caesar_fragment, container, false);
            EditText shiftEntry = (EditText) fragment.findViewById(R.id.shiftNum);
            shiftEntry.setOnEditorActionListener(new OnEditorActionListener()
            {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
                {
                    String input;
                    if(actionId == EditorInfo.IME_ACTION_DONE)
                    {
                        input= v.getText().toString();
                        MainActivity.updateShift(input, fragment);
                        return true;
                    }
                    return false;
                }
            });
            shiftEntry.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    String input;
                    EditText editText;

                    if (!hasFocus) {
                        editText = (EditText) v;
                        input = editText.getText().toString();
                        MainActivity.updateShift(input, fragment);
                    }
                }
            });
            return fragment;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(2);
            
        }
    }

    public static class VigenereFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.vigenere_fragment, container, false);
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(3);

        }
    }
}
