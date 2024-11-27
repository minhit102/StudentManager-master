package vn.edu.hust.studentman

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddOrEditStudentActivity : AppCompatActivity() {
    private lateinit var editName: EditText
    private lateinit var editId: EditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_or_edit_student)

        editName = findViewById(R.id.edit_name)
        editId = findViewById(R.id.edit_id)
        btnSave = findViewById(R.id.btn_save)

        val mode = intent.getStringExtra("mode")
        val studentData = intent.getStringExtra("student_data") ?: ""


        if (mode == "edit") {
            val parts = studentData.split(" - ")
            if (parts.size == 2) {
                editName.setText(parts[0])
                editId.setText(parts[1])
            }
        }


        btnSave.setOnClickListener {
            val newStudent = "${editName.text} - ${editId.text}"
            intent.putExtra("student_data", newStudent)
            intent.putExtra("position", intent.getIntExtra("position", -1))
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
