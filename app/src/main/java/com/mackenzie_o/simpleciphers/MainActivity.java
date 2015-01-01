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
import android.widget.Button;
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
                     vigenere = new VigenereFragment(),
                     autokey = new AutokeyFragment();

    /*Navigation*/
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
            case 4:
                return autokey;
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
            case 4:
                mTitle = getString(R.string.title_section4);
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
    
    /*onClick Methods*/
    public static void updateShiftIndicator(String in, View fragment){
        int num;
        if(in.equals("")){
            num=1;
        }else{
            num = Integer.parseInt(in);
        }
        ToggleButton mode = (ToggleButton) fragment.findViewById(R.id.caesarModeToggle);
        CharSequence updatedText = "\tA -> "+ShiftCiphers.getChar('A', num, true, false, !(mode.isChecked()));
        TextView shiftIndicator = (TextView) fragment.findViewById(R.id.shiftIndicator);
        shiftIndicator.setText(updatedText);
        
    }
    
    public void toggleMode(View view){
        ToggleButton modeToggle;
        EditText plaintext;
        TextView ciphertext;
        Button goButton;
        boolean updateShiftIndicator = false;
        switch(view.getId()){
            case R.id.caesarModeToggle:
                modeToggle = (ToggleButton)findViewById(R.id.caesarModeToggle);
                plaintext = (EditText) findViewById(R.id.caesarPlainText);
                ciphertext = (TextView) findViewById(R.id.caesarCipherText);
                goButton = (Button) findViewById(R.id.caesarGo);
                updateShiftIndicator = true;
                break;
            case R.id.vigenereModeToggle:
                modeToggle = (ToggleButton)findViewById(R.id.vigenereModeToggle);
                plaintext = (EditText) findViewById(R.id.vigenerePlainText);
                ciphertext = (TextView) findViewById(R.id.vigenereCipherText);
                goButton = (Button) findViewById(R.id.vigenereGo);
                break;
            case R.id.autokeyModeToggle:
                modeToggle = (ToggleButton)findViewById(R.id.autokeyModeToggle);
                plaintext = (EditText) findViewById(R.id.autokeyPlainText);
                ciphertext = (TextView) findViewById(R.id.autokeyCipherText);
                goButton = (Button) findViewById(R.id.autokeyGo);
                break;
            default:
                return;
        }
        //TODO: if there is text in the ciphertext box- put it in the plaintext box
        if(modeToggle.isChecked()){ //change to decrypt mode 
            plaintext.setHint(R.string.ciphertext_enter_hint);
            ciphertext.setText(R.string.plaintext_display_hint);
            goButton.setText("Decrypt");
            if(updateShiftIndicator){
                ((TextView) findViewById(R.id.shiftIndicator)).setText("\tA -> Z");
                ((EditText) findViewById(R.id.shiftNum)).setText("");
            }
        }else{                      //change to encrypt mode
            plaintext.setHint(R.string.plaintext_enter_hint);
            ciphertext.setText(R.string.ciphertext_display_hint);
            goButton.setText("Encrypt");
            if(updateShiftIndicator){
                ((TextView) findViewById(R.id.shiftIndicator)).setText("\tA -> B");
                ((EditText) findViewById(R.id.shiftNum)).setText("");
            }
        }
    }
    
    public void computeCaesarShift(View view){
        EditText plainText = (EditText) findViewById(R.id.caesarPlainText);
        TextView cipherText = (TextView) findViewById(R.id.caesarCipherText);
        EditText shiftNum = (EditText) findViewById(R.id.shiftNum);
        ToggleButton capitals = (ToggleButton) findViewById(R.id.caseToggle);
        ToggleButton characters = (ToggleButton) findViewById(R.id.characterToggle);
        ToggleButton mode = (ToggleButton) findViewById(R.id.caesarModeToggle);
        cipherText.setText(ShiftCiphers.caesarShift(plainText.getText().toString(),
                Integer.parseInt(shiftNum.getText().toString()),
                !capitals.isChecked(), !characters.isChecked(), !mode.isChecked())); //TODO: check mode
    }
    
    public void computeVigenere(View view){
        EditText plainText = (EditText) findViewById(R.id.vigenerePlainText);
        TextView cipherText = (TextView) findViewById(R.id.vigenereCipherText);
        EditText key = (EditText) findViewById(R.id.key);
        //TODO: check key for special characters in key
        ToggleButton capitals = (ToggleButton) findViewById(R.id.caseToggle);
        ToggleButton characters = (ToggleButton) findViewById(R.id.characterToggle);
        ToggleButton mode = (ToggleButton) findViewById(R.id.vigenereModeToggle);
        cipherText.setText(ShiftCiphers.vigenereShift(plainText.getText().toString(),
                key.getText().toString().toLowerCase(),
                !capitals.isChecked(), !characters.isChecked(), !mode.isChecked())); //TODO: check mode
    }
    
    public void computeAutokey(View view){
        EditText plainText = (EditText) findViewById(R.id.autokeyPlainText);
        TextView cipherText = (TextView) findViewById(R.id.autokeyCipherText);
        EditText key = (EditText) findViewById(R.id.key);
        //TODO: check key for special characters in key
        ToggleButton capitals = (ToggleButton) findViewById(R.id.caseToggle);
        ToggleButton characters = (ToggleButton) findViewById(R.id.characterToggle);
        ToggleButton mode = (ToggleButton) findViewById(R.id.autokeyModeToggle);
        cipherText.setText(ShiftCiphers.autokeyShift(plainText.getText().toString(),
                key.getText().toString().toLowerCase(),
                !capitals.isChecked(), !characters.isChecked(), !mode.isChecked())); //TODO: check mode
        
    }

    /*Fragments*/
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
                        MainActivity.updateShiftIndicator(input, fragment);
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
                        MainActivity.updateShiftIndicator(input, fragment);
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
    
    public static class AutokeyFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.autokey_fragment, container, false);
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(4);

        }
    }
}
