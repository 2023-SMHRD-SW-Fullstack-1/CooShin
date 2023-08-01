package com.smhrd.android.Community

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.smhrd.android.Data.BoardIdVO
import com.smhrd.android.R
import java.io.ByteArrayOutputStream

class CommunityCreateActivity : AppCompatActivity() {
    lateinit var etTitle : EditText
    lateinit var etContent : EditText
    lateinit var ivUpload : ImageView
    lateinit var btnComplete : Button
    lateinit var back : ImageButton
    lateinit var ibGallery : ImageButton
    lateinit var ibcamera : ImageButton

    val STORAGE_CODE = 1000
    lateinit var encodeImgString : String
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_create)
        etTitle = findViewById(R.id.etTitle)
        etContent = findViewById(R.id.etContent)
        ivUpload = findViewById(R.id.ivUpload)
        btnComplete = findViewById(R.id.btnComplete)
        back = findViewById(R.id.back)
        ibGallery = findViewById(R.id.ibGallery)
        ibcamera  = findViewById(R.id.ibCamera)



//        reqQueue = Volley.newRequestQueue(this@BoardWriteActivity)

        val spf = getSharedPreferences("mySPF", Context.MODE_PRIVATE)

        val member = Gson().fromJson(spf.getString("member", ""), BoardIdVO::class.java)



        ibGallery.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            //intent type 정해주기
            intent.type = "image/*"
            //다른 액티비티 시작이 된 후 그 액티비티에 결과를 다시 가져오고 싶을 때 사용!

            //startActivityForResult()는  onActivityResult 함께 사용함
            startActivityForResult(intent, STORAGE_CODE)
        }

        btnComplete.setOnClickListener {
            val inputTitle = etTitle.text.toString()
            val inputContent = etContent.text.toString()
            val writer = member.id

            val request = object : StringRequest(
                Request.Method.POST,
                "http://172.30.1.43:8888/board/write",
                { response ->
                    Log.d("response", response.toString())
                    if(response =="Success"){
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                },
                { error ->
                    Log.d("error", error.toString())

                }
            ) {
                override fun getParams(): MutableMap<String, String>? {
                    val params: MutableMap<String, String> = HashMap<String, String>()
                    val board = BoardVO(null, inputTitle, inputContent, writer, encodeImgString, null)
                    params.put("board", Gson().toJson(board))
                    return params
                }
            }

            reqQueue.add(request)
        }
    }

    //data에서 실제로 사용할 uri를 가져올 수 있음
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            STORAGE_CODE ->{
                //image uri 가져오기
                //nullable한 타입으로 만들어주기
                val selectedImageUri = data?.data


//                    if(selectedImageUri!=null){
//                        //uri -> bitmap으로 변환
//                        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri)
//                        ivUpload.setImageBitmap(bitmap)
//                    }

                selectedImageUri?.let{
                    //uri -> bitmap으로 변환
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri)
                    ivUpload.setImageBitmap(bitmap)


                    val options = BitmapFactory.Options()
                    options.inSampleSize = 4 //크기를 더 줄여줄 수 있다. 4개의 픽셀이 -> 1개의 픽셀로 만들어줌 => 1/4크기로 변환

                    //filter : true (선명) false(살짝뭉개짐)
                    val resized = Bitmap.createScaledBitmap(bitmap, 100, 100, true)
                    encodeBitmapImg(resized)

                }

            }
        }
    }
    //bitmap -> String (Base64
    private fun encodeBitmapImg(bitmap: Bitmap){
        //ByteArray 생성할 수 있는 stream
        val byteArrayOutputStream = ByteArrayOutputStream()
        //bitmap 압축
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteOfimg = byteArrayOutputStream.toByteArray()

        //encoding : ByteArray -> String(Base64 - encoding)
        encodeImgString = Base64.encodeToString(byteOfimg, Base64.DEFAULT)
    }

    val request = object : StringRequest(
        Request.Method.GET,
        "http://172.30.1.43:8888/board/img",
        { response ->
            Log.d("response", response.toString())

            val imageBytes = Base64.decode(response, 0)
            val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

//            iv.setImageBitmap(image)
        },
        { error ->
            Log.d("error", error.toString())
        }
    ) {}


    }


}