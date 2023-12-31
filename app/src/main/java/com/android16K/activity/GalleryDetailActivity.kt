package com.android16K.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.*
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.lifecycle.lifecycleScope
import com.android16K.databinding.ActivityGalleryDetailBinding
import com.android16K.dataset.*
import com.android16K.dataset.gall.*
import com.android16K.dataset.gall.Gallery
import com.android16K.view_model.GallDetailViewModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

class GalleryDetailActivity : AppCompatActivity() {
    private lateinit var detailBinding: ActivityGalleryDetailBinding
    private var gallId:Long? = null

    private val gallDetailViewModel = GallDetailViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        detailBinding = ActivityGalleryDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        gallId = intent.getLongExtra("gall_id", -1)

        getGalleryData()
        getGalleryImage()
        getGalleryAnswer()
    }

    override fun onStart() {
        super.onStart()
        detailBinding.gallDetailSaveAnswerButton.setOnClickListener(saveAnswer)
        detailBinding.gallDetailUpdateButton.setOnClickListener(updateGallery)
        detailBinding.gallDetailDeleteButton.setOnClickListener(deleteGallery)
    }

    private fun getGalleryImage() {
        lifecycleScope.launch {
            val imageList = gallDetailViewModel.getGalleryImage(gallId!!)
            imageLoad(detailBinding.gallDetailImageContainer, imageList!!)
        }
    }

    private fun imageLoad(layout: LinearLayoutCompat, imageList:List<GalleryImage>){
        for(image in imageList){
            val imageView = ImageView(this@GalleryDetailActivity.applicationContext)
            val imageParam = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT, //width
                1000
            )
            imageParam.marginStart = 50
            imageParam.topMargin = 150

            imageView.layoutParams = imageParam

            Glide.with(this@GalleryDetailActivity.applicationContext)
                .load("http://49.173.81.98:8080"+image.path)
                .into(imageView)

            layout.addView(imageView)
        }
    }

    private fun answerLoad(layout: LinearLayoutCompat, answerList:List<Answer>){
        for(answer in answerList){
            val contentText = TextView(this@GalleryDetailActivity.applicationContext)
            val textViewParam = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            )
            textViewParam.topMargin = 50
            textViewParam.marginStart = 50

            contentText.layoutParams = textViewParam

            contentText.text ="${answer.account.username}\n" +
                    "${answer.content}\n" +
                    "${answer.createdDate.split("T")[0]} ${answer.createdDate.split("T")[1].split(".")[0]}"

            layout.addView(contentText)
        }
    }

    private val saveAnswer: OnClickListener = OnClickListener {
        val answerInput = detailBinding.gallDetailAnswerInput
        val authenticationInfo = AuthenticationInfo.getInstance()

        lifecycleScope.launch {
            val request = GallDetailViewModel().writeAnswer(
                RequestAnswer(content = answerInput.text.toString(),
                    gallId = gallId!!.toLong(),
                    username = authenticationInfo.username!!)
            )
            if (request != null) {
                if(request > 0){
                    getGalleryAnswer()
                    answerInput.text.clear()
                }else{
                    Toast.makeText(this@GalleryDetailActivity.applicationContext, "답변 등록에 실패했습니다.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private val deleteGallery: OnClickListener = OnClickListener {
        lifecycleScope.launch {
            gallDetailViewModel.deleteGall(gallId!!)
            Toast.makeText(this@GalleryDetailActivity, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@GalleryDetailActivity, GalleryActivity::class.java))
            finish()
        }
    }

    private val updateGallery: OnClickListener = OnClickListener {
        val it = Intent(this@GalleryDetailActivity, GalleryFormActivity::class.java)
        it.putExtra("isUpdate", gallId)
        startActivity(it)
    }

    private fun getGalleryAnswer() {
        lifecycleScope.launch{
            val answerList = GallDetailViewModel().getAnswer(gallId!!)
            val answerContainer = detailBinding.gallDetailAnswerContainer
            answerLoad(answerContainer, answerList!!)
        }
    }

    private fun getGalleryData(){
        lifecycleScope.launch {
            val gall = gallDetailViewModel.getGallery(gallId!!)
            detailBinding.gallDetailTitle.text = gall!!.title
            detailBinding.gallDetailContent.text = gall.content
            detailBinding.gallDetailAuthor.text=  gall.account.username
            detailBinding.gallDetailDate.text = gall.modifiedDate?.let { dateStyle(it) } ?: dateStyle(gall.createDate)

            if(gall.account.username == AuthenticationInfo.getInstance().username){
                detailBinding.gallDetailMyButtonContainer.visibility = View.VISIBLE // [수정/삭제]버튼 활성화

                //수정 필요한 데이터 디바이스 내부에 저장
                val gallInstance = GalleryDTO.getInstance()
                gallInstance.id = gall.id
                gallInstance.title = gall.title
                gallInstance.content = gall.content
            }

        }
    }

    private fun dateStyle(date: String): String {
        val dateSplit = date.split("T")
        return dateSplit[0] + " " + dateSplit[1].split(".")[0]
    }
}