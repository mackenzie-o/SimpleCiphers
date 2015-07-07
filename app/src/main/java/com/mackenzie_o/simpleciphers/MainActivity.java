package com.mackenzie_o.simpleciphers;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.mackenzie_o.simpleciphers.ciphers.*;
import com.mackenzie_o.simpleciphers.fragments.*;

import java.util.Random;

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
            autokey = new AutokeyFragment(),
            keyword = new KeywordFragment(),
            affine = new AffineFragment();
    

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
                .replace(R.id.container, getSelectedTab(position + 1))
                .commit();
    }

    public Fragment getSelectedTab(int selection) {
        switch (selection) {
            case 1:
                return about;
            case 2:
                return caesar;
            case 3:
                return vigenere;
            case 4:
                return autokey;
            case 5:
                return keyword;
            case 6:
                return affine;
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
            case 5:
                mTitle = getString(R.string.title_section5);
                break;
            case 6:
                mTitle = getString(R.string.title_section6);
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
            restoreActionBar();
            if (getSupportActionBar().getTitle().equals("About")){
                getMenuInflater().inflate(R.menu.main, menu);
            } else {
                getMenuInflater().inflate(R.menu.main_cipher, menu);
            }
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }
    
    
    /*onClick Methods*/
    public void toggleMode(View view) {
        ToggleButton modeToggle;
        EditText plaintext;
        TextView ciphertext;
        Button goButton;
        boolean updateShiftIndicator = false;
        switch (view.getId()) {
            case R.id.caesarModeToggle:
                modeToggle = (ToggleButton) findViewById(R.id.caesarModeToggle);
                plaintext = (EditText) findViewById(R.id.caesarPlainText);
                ciphertext = (TextView) findViewById(R.id.caesarCipherText);
                goButton = (Button) findViewById(R.id.caesarGo);
                updateShiftIndicator = true;
                break;
            case R.id.vigenereModeToggle:
                modeToggle = (ToggleButton) findViewById(R.id.vigenereModeToggle);
                plaintext = (EditText) findViewById(R.id.vigenerePlainText);
                ciphertext = (TextView) findViewById(R.id.vigenereCipherText);
                goButton = (Button) findViewById(R.id.vigenereGo);
                break;
            case R.id.autokeyModeToggle:
                modeToggle = (ToggleButton) findViewById(R.id.autokeyModeToggle);
                plaintext = (EditText) findViewById(R.id.autokeyPlainText);
                ciphertext = (TextView) findViewById(R.id.autokeyCipherText);
                goButton = (Button) findViewById(R.id.autokeyGo);
                break;
            case R.id.keywordModeToggle:
                modeToggle = (ToggleButton) findViewById(R.id.keywordModeToggle);
                plaintext = (EditText) findViewById(R.id.keywordPlainText);
                ciphertext = (TextView) findViewById(R.id.keywordCipherText);
                goButton = (Button) findViewById(R.id.keywordGo);
                break;
            case R.id.affineModeToggle:
                modeToggle = (ToggleButton) findViewById(R.id.affineModeToggle);
                plaintext = (EditText) findViewById(R.id.affinePlainText);
                ciphertext = (TextView) findViewById(R.id.affineCipherText);
                goButton = (Button) findViewById(R.id.affineGo);
                break;
            default:
                return;
        }
        if (modeToggle.isChecked()) { //change to decrypt mode
            plaintext.setHint(R.string.ciphertext_enter_hint);
            ciphertext.setText(R.string.plaintext_display_hint);
            goButton.setText("Decrypt");
            if (updateShiftIndicator) {
                ((TextView) findViewById(R.id.shiftIndicator)).setText("\tA -> Z");
                ((EditText) findViewById(R.id.shiftNum)).setText("");
            }
        } else {                      //change to encrypt mode
            plaintext.setHint(R.string.plaintext_enter_hint);
            ciphertext.setText(R.string.ciphertext_display_hint);
            goButton.setText("Encrypt");
            if (updateShiftIndicator) {
                ((TextView) findViewById(R.id.shiftIndicator)).setText("\tA -> B");
                ((EditText) findViewById(R.id.shiftNum)).setText("");
            }
        }
    }

    public void createAboutDialog(MenuItem menu) {
        Bundle args = new Bundle();
        args.putString("title", "About The "+mTitle.toString()+" Cipher");
        String message;
        switch (mTitle.toString()){
            case "Caesar Shift":
                message = getString(R.string.about_caesar);
                break;
            case "Vigenère":
                message = getString(R.string.about_vigenere);
                break;
            case "Autokey":
                message = getString(R.string.about_autokey);
                break;
            case "Keyword":
                message = getString(R.string.about_keyword);
                break;
            case "Affine":
                message = getString(R.string.about_affine);
                break;
            default:
               message = "I made a mistake. Sorry...";
        }
        args.putString("body", message);
        DialogFragment newFragment = new AboutDialogFragment();
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "about");
    }

    public String checkKey(String key) {
        String cleanedKey = Ciphers.removeNonalphabeticChars(key);
        if (!cleanedKey.equals(key)) {
            Toast.makeText(getApplicationContext(), R.string.key_fixed_notification,
                    Toast.LENGTH_SHORT).show();
        }
        return cleanedKey;
    }
    
    public boolean checkInt(EditText num){
        try{
            Integer.parseInt(num.getText().toString());
            return true;
        }catch(Exception e){
            return false;
        }
        
    }
    
    public void aboutTitle(View view){
        TextView title = (TextView) findViewById(R.id.appTitle);
        String titleContent = title.getText().toString();
        Random rand = new Random();
        if(titleContent == getString(R.string.app_title)){
            title.setText(Caesar.caesarShift(getString(R.string.app_title), rand.nextInt(25)+1,
                    true, true, true));
        }else{
            title.setText(getString(R.string.app_title));
        }
    }
    
    public void aboutAuthor(View view){
        TextView title = (TextView) findViewById(R.id.author);
        String titleContent = title.getText().toString();
        Random rand = new Random();
        String [] keys = {"me", "myself", "andi"};
        if(titleContent == getString(R.string.author)){
            title.setText(Autokey.autokeyShift(getString(R.string.author), keys[rand.nextInt(3)],
                    true, true, true));
        }else{
            title.setText(getString(R.string.author));
        }
    }
    
    /* Compute Methods */
    public void computeCaesarShift(View view) {
        EditText plainText = (EditText) findViewById(R.id.caesarPlainText);
        TextView cipherText = (TextView) findViewById(R.id.caesarCipherText);
        EditText shiftNum = (EditText) findViewById(R.id.shiftNum);
        ToggleButton capitals = (ToggleButton) findViewById(R.id.caseToggle);
        ToggleButton characters = (ToggleButton) findViewById(R.id.characterToggle);
        ToggleButton mode = (ToggleButton) findViewById(R.id.caesarModeToggle);
        if(!checkInt(shiftNum)){
            Toast.makeText(getApplicationContext(), R.string.bad_int_error,
                    Toast.LENGTH_SHORT).show();
            return;
        } else if (plainText.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), R.string.no_message_error,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        cipherText.setText(Caesar.caesarShift(plainText.getText().toString(),
                Integer.parseInt(shiftNum.getText().toString()),
                !capitals.isChecked(), !characters.isChecked(), !mode.isChecked()));
    }
    public void computeVigenere(View view) {
        EditText key = (EditText) findViewById(R.id.key);
        String keyText = checkKey(key.getText().toString().toLowerCase());
        EditText plainText = (EditText) findViewById(R.id.vigenerePlainText);
        TextView cipherText = (TextView) findViewById(R.id.vigenereCipherText);
        ToggleButton capitals = (ToggleButton) findViewById(R.id.caseToggle);
        ToggleButton characters = (ToggleButton) findViewById(R.id.characterToggle);
        ToggleButton mode = (ToggleButton) findViewById(R.id.vigenereModeToggle);
        if(keyText.length() == 0) {
            Toast.makeText(getApplicationContext(), R.string.empty_key_error,
                    Toast.LENGTH_SHORT).show();
            return;
        } else if (plainText.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), R.string.no_message_error,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        cipherText.setText(Vigenere.vigenereShift(plainText.getText().toString(),
                keyText, !capitals.isChecked(), !characters.isChecked(), !mode.isChecked()));
    }
    public void computeAutokey(View view) {
        EditText key = (EditText) findViewById(R.id.key);
        String keyText = checkKey(key.getText().toString().toLowerCase());
        EditText plainText = (EditText) findViewById(R.id.autokeyPlainText);
        TextView cipherText = (TextView) findViewById(R.id.autokeyCipherText);
        ToggleButton capitals = (ToggleButton) findViewById(R.id.caseToggle);
        ToggleButton characters = (ToggleButton) findViewById(R.id.characterToggle);
        ToggleButton mode = (ToggleButton) findViewById(R.id.autokeyModeToggle);
        if(keyText.length() == 0) {
            Toast.makeText(getApplicationContext(), R.string.empty_key_error,
                    Toast.LENGTH_SHORT).show();
            return;
        } else if (plainText.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), R.string.no_message_error,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        cipherText.setText(Autokey.autokeyShift(plainText.getText().toString(),
                keyText, !capitals.isChecked(), !characters.isChecked(), !mode.isChecked()));

    }
    public void computeKeyword(View view) {
        EditText key = (EditText) findViewById(R.id.key);
        String keyText = checkKey(key.getText().toString().toLowerCase());
        EditText plainText = (EditText) findViewById(R.id.keywordPlainText);
        TextView cipherText = (TextView) findViewById(R.id.keywordCipherText);
        ToggleButton capitals = (ToggleButton) findViewById(R.id.caseToggle);
        ToggleButton characters = (ToggleButton) findViewById(R.id.characterToggle);
        ToggleButton mode = (ToggleButton) findViewById(R.id.keywordModeToggle);
        if(keyText.length() == 0) {
            Toast.makeText(getApplicationContext(), R.string.empty_key_error,
                    Toast.LENGTH_SHORT).show();
            return;
        } else if (plainText.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), R.string.no_message_error,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        cipherText.setText(Keyword.keywordCipher(plainText.getText().toString(),
                keyText, !capitals.isChecked(), !characters.isChecked(), !mode.isChecked()));
    }
    public void computeAffineShift(View view) {
        Spinner selection = (Spinner) findViewById(R.id.multi_spinner);
        EditText plainText = (EditText) findViewById(R.id.affinePlainText);
        TextView cipherText = (TextView) findViewById(R.id.affineCipherText);
        EditText shiftNum = (EditText) findViewById(R.id.shiftNum);
        ToggleButton capitals = (ToggleButton) findViewById(R.id.caseToggle);
        ToggleButton characters = (ToggleButton) findViewById(R.id.characterToggle);
        ToggleButton mode = (ToggleButton) findViewById(R.id.affineModeToggle);
        if(!checkInt(shiftNum)) {
            Toast.makeText(getApplicationContext(), R.string.empty_key_error,
                    Toast.LENGTH_SHORT).show();
            return;
        } else if (plainText.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), R.string.no_message_error,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        cipherText.setText(Affine.affineShift(plainText.getText().toString(),
                Integer.parseInt(selection.getSelectedItem().toString()),
                Integer.parseInt(shiftNum.getText().toString()),
                !capitals.isChecked(), !characters.isChecked(), !mode.isChecked()));
    }
}
