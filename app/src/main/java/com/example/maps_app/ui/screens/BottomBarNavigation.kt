package com.example.maps_app.ui.screens

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.maps_app.navigation.BottomNavigation

@Composable
fun MyBottomBarNavigation(nav: NavHostController, items: List<BottomNavigation>) {
    BottomNavigation(backgroundColor = Color.LightGray) {
        val navBackStackEntry by nav.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route){
                        nav.navigate(item.route)
                    }
                },
                icon = { Icon(imageVector = item.icon , contentDescription = item.label) },
                label = { Text(text = item.label ) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.Black ,
                alwaysShowLabel = false
            )
        }
    }
}