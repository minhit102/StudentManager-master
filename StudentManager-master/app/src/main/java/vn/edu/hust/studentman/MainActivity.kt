package vn.edu.hust.studentman

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
  private lateinit var listViewStudents: ListView
  private lateinit var studentAdapter: ArrayAdapter<String>
  private val students = mutableListOf(
    "Nguyễn Văn An - SV001",
    "Trần Thị Bảo - SV002",
    "Lê Hoàng Cường - SV003",
    "Phạm Thị Dung - SV004",
    "Đỗ Minh Đức - SV005",
    "Vũ Thị Hoa - SV006",
    "Hoàng Văn Hải - SV007",
    "Bùi Thị Hạnh - SV008",
    "Đinh Văn Hùng - SV009",
    "Nguyễn Thị Linh - SV010",
    "Phạm Văn Long - SV011",
    "Trần Thị Mai - SV012",
    "Lê Thị Ngọc - SV013",
    "Vũ Văn Nam - SV014",
    "Hoàng Thị Phương - SV015",
    "Đỗ Văn Quân - SV016",
    "Nguyễn Thị Thu - SV017",
    "Trần Văn Tài - SV018",
    "Phạm Thị Tuyết - SV019",
    "Lê Văn Vũ - SV020"

  )



  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    listViewStudents = findViewById(R.id.list_view_students)
    studentAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, students)
    listViewStudents.adapter = studentAdapter

    // Đăng ký Context Menu cho ListView
    registerForContextMenu(listViewStudents)
  }

  // Tạo OptionMenu
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.option_menu, menu)
    return true
  }

  // Xử lý chọn OptionMenu
  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.menu_add_new -> {
        val intent = Intent(this, AddOrEditStudentActivity::class.java)
        intent.putExtra("mode", "add") // Truyền chế độ add
        startActivityForResult(intent, REQUEST_ADD_STUDENT)
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  // Tạo ContextMenu
  override fun onCreateContextMenu(
    menu: ContextMenu?,
    v: android.view.View?,
    menuInfo: ContextMenu.ContextMenuInfo?
  ) {
    super.onCreateContextMenu(menu, v, menuInfo)
    menuInflater.inflate(R.menu.context_menu, menu)
  }

  // Xử lý chọn ContextMenu
  override fun onContextItemSelected(item: MenuItem): Boolean {
    val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
    val position = info.position

    return when (item.itemId) {
      R.id.menu_edit -> {
        val intent = Intent(this, AddOrEditStudentActivity::class.java)
        intent.putExtra("mode", "edit")
        intent.putExtra("student_data", students[position])
        intent.putExtra("position", position)
        startActivityForResult(intent, REQUEST_EDIT_STUDENT)
        true
      }
      R.id.menu_remove -> {
        students.removeAt(position)
        studentAdapter.notifyDataSetChanged()
        Toast.makeText(this, "Deleted successfully", Toast.LENGTH_SHORT).show()
        true
      }
      else -> super.onContextItemSelected(item)
    }
  }

  // Xử lý kết quả trả về từ Activity thêm/sửa
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode == RESULT_OK && data != null) {
      val newStudent = data.getStringExtra("student_data") ?: return
      val position = data.getIntExtra("position", -1)

      when (requestCode) {
        REQUEST_ADD_STUDENT -> {
          students.add(newStudent)
        }
        REQUEST_EDIT_STUDENT -> {
          if (position >= 0) students[position] = newStudent
        }
      }
      studentAdapter.notifyDataSetChanged()
    }
  }

  companion object {
    const val REQUEST_ADD_STUDENT = 100
    const val REQUEST_EDIT_STUDENT = 200
  }
}
