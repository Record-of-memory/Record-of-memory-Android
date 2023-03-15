package kr.co.app.recordOfMemory.src.main.home.diary2.likes

interface LikesInterface {
    fun onPostLikesSuccess(response: LikesResponse)

    fun onPostLikesFailure(message: String)

    fun onDeleteLikesSuccess(response: LikesResponse)

    fun onDeleteLikesFailure(message: String)

    fun onCheckLikesSuccess(response: CheckLikesResponse)

    fun onCheckLikesFailure(message: String)

}