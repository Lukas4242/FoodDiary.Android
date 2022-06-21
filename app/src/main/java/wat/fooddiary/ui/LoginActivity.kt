package wat.fooddiary.ui

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import wat.fooddiary.R
import wat.fooddiary.Strings
import wat.fooddiary.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val vm : LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vm.loginResponse.observe(this) {
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
            finish()
        }

        vm.loginError.observe(this) {
            binding.progressLayout.progressLayout.visibility = View.GONE
            Toast.makeText(this, Strings.get(R.string.error_login), Toast.LENGTH_SHORT).show()
        }

        vm.registrationError.observe(this) {
            binding.progressLayout.progressLayout.visibility = View.GONE
            Toast.makeText(this, Strings.get(R.string.error_register), Toast.LENGTH_SHORT).show()
        }

        vm.registration.observe(this) {
            binding.progressLayout.progressLayout.visibility = View.GONE
            Toast.makeText(this, Strings.get(R.string.success_register), Toast.LENGTH_SHORT).show()
        }

        binding.btnLogin.setOnClickListener {
            binding.progressLayout.progressLayout.visibility = View.VISIBLE
            vm.login(binding.txtLogin.text.toString(), binding.txtPassword.text.toString())
        }

        binding.btnRegister.setOnClickListener {
            binding.progressLayout.progressLayout.visibility = View.VISIBLE
            vm.register(binding.txtLogin.text.toString(), binding.txtPassword.text.toString())
        }
    }
}