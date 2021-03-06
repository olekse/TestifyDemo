package com.olek.testify.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.olek.testify.R;
import com.olek.testify.activity.NexusFragments.FormGenerationActivity;
import com.olek.testify.model.Student;
import com.olek.testify.model.TableAdapter;
import com.olek.testify.recycleview.adapter.StudentAdapter;

import java.util.ArrayList;
import java.util.Map;

import butterknife.ButterKnife;

public class HomeScreenActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mFirebaseAuth;
    private boolean toSignOut = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        ButterKnife.bind(this);

        mFirebaseAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //fab.setImageDrawable(R.drawable.);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View hView =  navigationView.getHeaderView(0);
        TextView textName = (TextView) hView.findViewById(R.id.nav_panel_name);
        TextView textEmail = (TextView) hView.findViewById(R.id.nav_panel_email);


        if (mFirebaseAuth.getCurrentUser() != null) {
            textName.setText(mFirebaseAuth.getCurrentUser().getDisplayName());
            textEmail.setText(mFirebaseAuth.getCurrentUser().getEmail());
        }

        navigationView.setNavigationItemSelectedListener(this);

        setUpRecyclerView();

    }



    private void setUpRecyclerView() {
        RecyclerView recList = (RecyclerView) findViewById(R.id.main_recylcer_view);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        /*
        Map<Course, Integer> map = TableAdapter.getAllFromTableAsMap(this, Course.class);
        CourseAdapter adapter = new CourseAdapter(new ArrayList<>(map.keySet()), this.getApplication());
        recList.setAdapter(adapter);
        */

        Map<Student, Integer> studentMap = TableAdapter.getAllFromTableAsMap(this, Student.class);
        StudentAdapter studentAdapter = new StudentAdapter(new ArrayList<>(studentMap.keySet()), this);
        recList.setAdapter(studentAdapter);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);

        /*
        navPanelName.setText(mFirebaseAuth.getCurrentUser().getDisplayName());
        navPanelEmail.setText(mFirebaseAuth.getCurrentUser().getEmail());
        */
        return true;
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //Intent intent = new Intent(this, FormGenerationActivity.class);
        Intent listIntent = new Intent(this, ListGenerationActivity.class);

        if (id == R.id.nav_group) {
            listIntent.putExtra(FormGenerationActivity.FORM_TYPE_ARGUMENT_KEY, FormGenerationActivity.FormType.GROUP_FRAGMENT_KEY);
            startActivity(listIntent);
        } else if (id == R.id.nav_student) {
            listIntent.putExtra(FormGenerationActivity.FORM_TYPE_ARGUMENT_KEY, FormGenerationActivity.FormType.STUDENT_FRAGMENT_KEY);
            startActivity(listIntent);
        } else if (id == R.id.nav_courses) {
            listIntent.putExtra(FormGenerationActivity.FORM_TYPE_ARGUMENT_KEY, FormGenerationActivity.FormType.COURSE_FRAGMENT_KEY);
            startActivity(listIntent);
        } else if (id == R.id.nav_subjects) {
            listIntent.putExtra(FormGenerationActivity.FORM_TYPE_ARGUMENT_KEY, FormGenerationActivity.FormType.SUBJECT_FRAGMENT_KEY);
            startActivity(listIntent);
        } else if (id == R.id.nav_new_test) {
            listIntent.putExtra(FormGenerationActivity.FORM_TYPE_ARGUMENT_KEY, FormGenerationActivity.FormType.TEST_FRAGMENT_KEY);
            startActivity(listIntent);
        } else if (id == R.id.nav_sign_out){
            //
            mFirebaseAuth.signOut();
            Intent returnIntent = new Intent();
            setResult(RESULT_CANCELED, returnIntent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
