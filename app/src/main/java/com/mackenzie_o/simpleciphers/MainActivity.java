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
import com.mackenzie_o.simpleciphers.Fragments.*;

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

    // Stores the title, fragment object, and about dialog message for each item in the nav bar
    NavItem[] navItems;

    class NavItem {
        String title;
        String about;
        Fragment fragment;

        public NavItem(String title, String about, Fragment fragment) {
            this.title = title;
            this.about = about;
            this.fragment = fragment;
        }
    }

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
                .replace(R.id.container, getSelectedTab(position))
                .commit();
    }

    private void createNavList() {
        navItems = new NavItem[9];

        navItems[0] = new NavItem(getString(R.string.about_title),
                "", new AboutFragment());
        navItems[1] = new NavItem(getString(R.string.caesar_title),
                getString(R.string.about_caesar), new CaesarFragment());
        navItems[2] = new NavItem(getString(R.string.vigenere_title),
                getString(R.string.about_vigenere), new VigenereFragment());
        navItems[3] = new NavItem(getString(R.string.autokey_title),
                getString(R.string.about_autokey), new AutokeyFragment());
        navItems[4] = new NavItem(getString(R.string.keyword_title),
                getString(R.string.about_keyword), new KeywordFragment());
        navItems[5] = new NavItem(getString(R.string.affine_title),
                getString(R.string.about_affine), new AffineFragment());
        navItems[6] = new NavItem(getString(R.string.rail_title),
                getString(R.string.about_placeholder), new RailFragment());
        navItems[7] = new NavItem(getString(R.string.scytale_title),
                getString(R.string.about_placeholder), new ScytaleFragment());
        navItems[8] = new NavItem(getString(R.string.beaufont_title),
                getString(R.string.about_placeholder), new BeaufontFragment());

        // add new fragments here and in NavigationDrawerFragment

    }

    public Fragment getSelectedTab(int selection) {
        if (navItems == null) {
            createNavList();
        }
        if (selection >= navItems.length) {
            return null;
        }
        return navItems[selection].fragment;
    }

    public void onSectionAttached(int number) {
        if (navItems == null) {
            createNavList();
        }
        if (number < navItems.length) {
            mTitle = navItems[number].title;
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
            if (getSupportActionBar().getTitle().equals("About")) {
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
            case R.id.railModeToggle:
                modeToggle = (ToggleButton) findViewById(R.id.railModeToggle);
                plaintext = (EditText) findViewById(R.id.railPlainText);
                ciphertext = (TextView) findViewById(R.id.railCipherText);
                goButton = (Button) findViewById(R.id.railGo);
                break;
            case R.id.scytaleModeToggle:
                modeToggle = (ToggleButton) findViewById(R.id.scytaleModeToggle);
                plaintext = (EditText) findViewById(R.id.scytalePlainText);
                ciphertext = (TextView) findViewById(R.id.scytaleCipherText);
                goButton = (Button) findViewById(R.id.scytaleGo);
                break;
            case R.id.beaufontModeToggle:
                modeToggle = (ToggleButton) findViewById(R.id.beaufontModeToggle);
                plaintext = (EditText) findViewById(R.id.beaufontPlainText);
                ciphertext = (TextView) findViewById(R.id.beaufontCipherText);
                goButton = (Button) findViewById(R.id.beaufontGo);
                break;
            default:
                return;
        }
        if (modeToggle.isChecked()) { // change to decrypt mode
            plaintext.setHint(R.string.ciphertext_enter_hint);
            ciphertext.setText(R.string.plaintext_display_hint);
            goButton.setText("Decrypt");
            if (updateShiftIndicator) {
                ((TextView) findViewById(R.id.shiftIndicator)).setText("\tA -> Z");
                ((EditText) findViewById(R.id.shiftNum)).setText("");
            }
        } else {                      // change to encrypt mode
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
        String title = mTitle.toString();
        args.putString("title", "About The " + title + " Cipher");
        String message = "I made a mistake. Sorry...";
        for (int i = 0; i < navItems.length; i++) {
            if (title.equals(navItems[i].title)) {
                message = navItems[i].about;
            }
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

    public boolean checkInt(EditText num) {
        try {
            Integer.parseInt(num.getText().toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void aboutTitle(View view) {
        TextView title = (TextView) findViewById(R.id.appTitle);
        String titleContent = title.getText().toString();
        Random rand = new Random();
        if (titleContent == getString(R.string.app_title)) {
            title.setText(Caesar.caesarShift(getString(R.string.app_title), rand.nextInt(25) + 1,
                    true, true, true));
        } else {
            title.setText(getString(R.string.app_title));
        }
    }

    public void aboutAuthor(View view) {
        TextView title = (TextView) findViewById(R.id.author);
        String titleContent = title.getText().toString();
        Random rand = new Random();
        String[] keys = {"me", "myself", "andi"};
        if (titleContent == getString(R.string.author)) {
            title.setText(Autokey.autokeyShift(getString(R.string.author), keys[rand.nextInt(3)],
                    true, true, true));
        } else {
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
        if (!checkInt(shiftNum)) {
            Toast.makeText(getApplicationContext(), R.string.bad_int_error,
                    Toast.LENGTH_SHORT).show();
            return;
        } else if (plainText.getText().toString().length() == 0) {
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
        if (keyText.length() == 0) {
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
        if (keyText.length() == 0) {
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
        if (keyText.length() == 0) {
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
        if (!checkInt(shiftNum)) {
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

    public void computeRailFence(View view) {
        EditText plainText = (EditText) findViewById(R.id.railPlainText);
        TextView cipherText = (TextView) findViewById(R.id.railCipherText);
        TextView railDiagram = (TextView) findViewById(R.id.railDiagram);
        EditText railNum = (EditText) findViewById(R.id.railNum);
        ToggleButton capitals = (ToggleButton) findViewById(R.id.caseToggle);
        ToggleButton characters = (ToggleButton) findViewById(R.id.characterToggle);
        ToggleButton mode = (ToggleButton) findViewById(R.id.railModeToggle);
        if (!checkInt(railNum)) {
            Toast.makeText(getApplicationContext(), R.string.empty_key_error,
                    Toast.LENGTH_SHORT).show();
            return;
        } else if (plainText.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), R.string.no_message_error,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        boolean encode = !mode.isChecked();
        RailFence fence = new RailFence(plainText.getText().toString(),
                Integer.parseInt(railNum.getText().toString()),
                !capitals.isChecked(), !characters.isChecked(), encode);
        railDiagram.setText(fence.railFenceDiagram());
        cipherText.setText(encode ? fence.getCiphertext() : fence.getPlaintext());
    }

    public void computeScytale(View view) {
        EditText plainText = (EditText) findViewById(R.id.scytalePlainText);
        TextView cipherText = (TextView) findViewById(R.id.scytaleCipherText);
        TextView scytaleDiagram = (TextView) findViewById(R.id.scytaleDiagram);
        EditText scytaleNum = (EditText) findViewById(R.id.scytaleNum);
        ToggleButton capitals = (ToggleButton) findViewById(R.id.caseToggle);
        ToggleButton characters = (ToggleButton) findViewById(R.id.characterToggle);
        ToggleButton mode = (ToggleButton) findViewById(R.id.scytaleModeToggle);
        if (!checkInt(scytaleNum)) {
            Toast.makeText(getApplicationContext(), R.string.empty_key_error,
                    Toast.LENGTH_SHORT).show();
            return;
        } else if (plainText.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), R.string.no_message_error,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        boolean encode = !mode.isChecked();
        Scytale scy = new Scytale(plainText.getText().toString(),
                Integer.parseInt(scytaleNum.getText().toString()),
                !capitals.isChecked(), !characters.isChecked(), encode);
        scytaleDiagram.setText(scy.rodDiagram());
        cipherText.setText(encode ? scy.getCiphertext() : scy.getPlaintext());
    }

    public void computeBeaufont(View view) {
        EditText key = (EditText) findViewById(R.id.key);
        String keyText = checkKey(key.getText().toString().toLowerCase());
        EditText plainText = (EditText) findViewById(R.id.beaufontPlainText);
        TextView cipherText = (TextView) findViewById(R.id.beaufontCipherText);
        ToggleButton capitals = (ToggleButton) findViewById(R.id.caseToggle);
        ToggleButton characters = (ToggleButton) findViewById(R.id.characterToggle);
        if (keyText.length() == 0) {
            Toast.makeText(getApplicationContext(), R.string.empty_key_error,
                    Toast.LENGTH_SHORT).show();
            return;
        } else if (plainText.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), R.string.no_message_error,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        cipherText.setText(Beaufont.beaufontCipher(plainText.getText().toString(),
                keyText, !capitals.isChecked(), !characters.isChecked()));
    }
}
