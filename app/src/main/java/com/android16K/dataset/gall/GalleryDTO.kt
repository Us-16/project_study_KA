package com.android16K.dataset.gall

data class GalleryDTO(
    var id:Long? = null,
    var title:String? = null,
    var content:String? = null
){
    //TODO("답답한 줄은 아는데, 리팩토링은 나중에")
    companion object{
        private var instance: GalleryDTO? = null

        fun getInstance(): GalleryDTO{
            if(instance == null){
                instance = GalleryDTO()
            }
            return instance!!
        }
    }
}
