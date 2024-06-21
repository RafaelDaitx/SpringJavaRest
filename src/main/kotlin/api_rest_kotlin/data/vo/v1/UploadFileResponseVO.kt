package api_rest_kotlin.data.vo.v1

class UploadFileResponseVO (
    var fileName: String = "",
    var fileDownloadUri: String = "",
    var fileType: String = "",
    var fileSize: Long = 0
)