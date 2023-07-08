package tech.swiftin.feedme.scan

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf

class EntryActivity : AppCompatActivity() {
    private val permissionState = mutableStateOf(createViewModel(false))
    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            permissionState.value = createViewModel(granted = isGranted)

            if (!isGranted) {
                Toast.makeText(this, "Permission is not granted :(", Toast.LENGTH_SHORT).show()

                return@registerForActivityResult
            }

            Toast.makeText(this, "Permission is granted!", Toast.LENGTH_SHORT).show()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                var viewModel = permissionState.value

                LaunchedEffect(key1 = Unit ) {
                    requestPermission.launch(viewModel.permission)
                }

                if (!permissionState.value.granted) {
                    permissionView(
                        action = { requestPermission.launch(viewModel.permission) },
                        permissionDescription = viewModel.description,
                    )

                    return@MaterialTheme
                }

                cameraView()
            }
        }
    }
}

fun createViewModel(granted: Boolean): PermissionViewModel {
    return PermissionViewModel(
        granted = granted,
        permission = Manifest.permission.CAMERA,
        description = "Grant camera permissions"
    )
}
