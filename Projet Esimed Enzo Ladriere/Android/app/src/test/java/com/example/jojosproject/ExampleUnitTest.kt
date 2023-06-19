package com.example.jojosproject

import com.example.jojosproject.DataClass.AuthenticationResult
import com.example.jojosproject.DataClass.User
import com.example.jojosproject.GestionApi.GestionUtilisateur
import com.example.jojosproject.GestionApi.UserService
import org.junit.Test
import org.junit.Assert.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    val mygestionUtilisateur = GestionUtilisateur()
    @Test
    fun champsRemplie() {
        assertEquals(true,
            mygestionUtilisateur.estLesChampsRemplit("a" ,"a", "a"))
        assertEquals(false,
        mygestionUtilisateur.estLesChampsRemplit("a","a",""))
        assertEquals(false,
        mygestionUtilisateur.estLesChampsRemplit("","",""))
    }



    @Test
    fun createUser() {
        val myService = UserService()
        val user = User()
        user.pseudonyme = "Test"
        user.nom = "TestNom"
        user.prenom = "TestPrenom"
        user.motDePasse = "motDePasse"

        myService.userAccountRoutes.postUsers(user).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                assertEquals(response.code(), 200)
                println("onResponse")
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                println("onFailure")
            }
        })

        println("ici")
    }

    data class LoginData(val pseudonyme: String, val motDePasse: String)
    @Test
    fun testLoginRoute() {
        val loginData = LoginData("Test", "TestMotDePasse")

        val callback = object : Callback<AuthenticationResult> {
            override fun onResponse(call: Call<AuthenticationResult>, response: Response<AuthenticationResult>) {
                assertEquals(200, response.code())
            }

            override fun onFailure(call: Call<AuthenticationResult>, t: Throwable) {
            }
        }
    }
}