package com.ionix.megamovies.ui.activity

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.ionix.megamovies.R
import com.ionix.megamovies.databinding.ActivityMainBinding
import com.permissionx.guolindev.PermissionX
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        requestPermissions()
    }

    private fun requestPermissions(){

        PermissionX.init(this)
            .permissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA)
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(deniedList, getString(R.string.permissions_required), getString(R.string.ok), getString(R.string.cancel))
            }
            .request { allGranted, _, deniedList ->
                if (allGranted) {
                    Toast.makeText(this, R.string.granted_permissions, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, getString(R.string.denied_permissions, deniedList), Toast.LENGTH_LONG).show()
                }
            }

    }
}