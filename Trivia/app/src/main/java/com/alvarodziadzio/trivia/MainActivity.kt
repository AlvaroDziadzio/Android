package com.alvarodziadzio.trivia

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.alvarodziadzio.trivia.data.User
import com.alvarodziadzio.trivia.fragments.GameMultipleFragment
import com.alvarodziadzio.trivia.fragments.RankingFragment
import com.alvarodziadzio.trivia.fragments.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_game.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    lateinit var nav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val pref = getPreferences(Context.MODE_PRIVATE)

        val email = pref.getString("email", "")
        val password = pref.getString("password", "")

        HttpWorkbench.auth(email, password) {
            if (it != null) {
                if(!it.getBoolean("sucesso")) {
                    val i = Intent(this, LoginActivity::class.java)
                    i.action = Intent.ACTION_VIEW
                    startActivity(i)
                }

            }
        }



//        Game.start(User("alvaro.email@email.com", "123456", 0)) {
//            nav.selectedItemId = R.id.navigation_game
//            openFragment(GameMultipleFragment(Game.nextRemote()!!))
//        }

        nav = navigation
        nav.setOnNavigationItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.navigation_game -> openFragment(GameMultipleFragment(Game.nextRemote()!!))
            R.id.navigation_ranking -> openFragment(RankingFragment())
            R.id.navigation_settings -> openFragment(SettingsFragment())
        }

        return true
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}
