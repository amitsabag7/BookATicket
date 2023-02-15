package com.example.bookaticket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bookaticket.Model.Model;

public class MainActivity extends AppCompatActivity {

    NavController controller;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
//            controller.popBackStack();
            controller.navigate(R.id.homePage_Fragment);
        } else if(item.getItemId() == R.id.main_menu_private) {
            controller.navigate(R.id.action_global_privateArea_Fragment);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFrg = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.main_navhost);
        controller = navHostFrg.getNavController();
        NavigationUI.setupActionBarWithNavController(this,controller);
    }
}