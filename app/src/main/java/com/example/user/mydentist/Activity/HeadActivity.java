package com.example.user.mydentist.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.mydentist.HeadFragment;
import com.example.user.mydentist.R;
import com.example.user.mydentist.TestFragment;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;
import java.util.List;


public class HeadActivity extends AppCompatActivity implements OnMenuItemClickListener, OnMenuItemLongClickListener {
    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head);

        fragmentManager = getSupportFragmentManager();
        initToolbar();
        initMenuFragment();
        addFragment(new HeadFragment(), true, true, R.id.container);





    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        mToolbar.setNavigationIcon(R.mipmap.ic_launcher);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mToolBarTextView.setText("My Dentist");

    }

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
        mMenuDialogFragment.setItemLongClickListener(this);
    }

    private List<MenuObject> getMenuObjects() {
        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.mipmap.ic_btn_back);
        close.setBgColor(R.color.colorPrimaryDark);

        MenuObject send = new MenuObject("Отправить");
        send.setResource(R.mipmap.ic_btn_back);
        send.setBgColor(R.color.colorPrimaryDark);

        MenuObject chat = new MenuObject("Написать");
        chat.setResource(R.mipmap.ic_launcher);
        chat.setBgColor(R.color.colorPrimaryDark);

        MenuObject record = new MenuObject("Записаться на прием");
        record.setResource(R.mipmap.ic_launcher);
        record.setBgColor(R.color.colorPrimaryDark);

        menuObjects.add(close);
        menuObjects.add(send);
        menuObjects.add(chat);
        menuObjects.add(record);
        return menuObjects;
    }



    protected void addFragment(Fragment fragment, boolean addToBackStack, boolean addReplace, int containerId) {
        invalidateOptionsMenu();
        String backStackName = fragment.getClass().getName();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStackName, 0);
        if (!fragmentPopped) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (addReplace) {
                transaction.add(containerId, fragment, backStackName)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE); }
                else {
                transaction.replace(containerId, fragment, backStackName)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            }
                if (addToBackStack)
                    transaction.addToBackStack(backStackName);
                transaction.commit();
            }

    }


    @Override
    public void onBackPressed() {
        if (mMenuDialogFragment != null && mMenuDialogFragment.isAdded()) {
            mMenuDialogFragment.dismiss();
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onMenuItemClick(View clickedView, int position) {

        Toast.makeText(this, "Clicked on position: " + position, Toast.LENGTH_SHORT).show();
        }





    @Override
    public void onMenuItemLongClick(View clickedView, int position) {

        switch (position) {
            case 1:
                addFragment(new TestFragment(), true, false, R.id.container);
                break;

        }
    }
}
