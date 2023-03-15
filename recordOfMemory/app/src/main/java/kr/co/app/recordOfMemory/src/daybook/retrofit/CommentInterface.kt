package kr.co.app.recordOfMemory.src.daybook.retrofit

import kr.co.app.recordOfMemory.config.BaseResponse
import kr.co.app.recordOfMemory.src.daybook.retrofit.models.GetCommentsResponse
import kr.co.app.recordOfMemory.src.daybook.retrofit.models.PostCommentResponse

interface CommentInterface {
	fun onPostCommentSuccess(response: PostCommentResponse)
	fun onPostCommentFailure(message:String)

	fun onGetCommentsSuccess(response: GetCommentsResponse)
	fun onGetCommentsFailure(message: String)

	fun onDeleteCommentSuccess(response: BaseResponse)
	fun onDeleteCommentFailure(message: String)
}
