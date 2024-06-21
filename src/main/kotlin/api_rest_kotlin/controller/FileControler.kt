package api_rest_kotlin.controller

import api_rest_kotlin.data.vo.v1.UploadFileResponseVO
import api_rest_kotlin.services.FileStorageService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.logging.Logger

@RestController
@Tag(name = "File Endpoint")
@RequestMapping("api/file/v1")
class FileControler {

    private val logger = Logger.getLogger(FileControler::class.java.name)

    @Autowired
    private lateinit var filesStorageService: FileStorageService

    @PostMapping("/uploadFile")
    fun uploadFile(@RequestParam("file") file: MultipartFile): UploadFileResponseVO{
        val fileName = filesStorageService.storeFile(file)
        val fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("api/file/v1/uploadFile")
            .path(fileName)
            .toUriString()


        return UploadFileResponseVO(fileName, fileDownloadUri, file.contentType!!, file.size)
    }


    @PostMapping("/uploadMultipleFile")
    fun uploadMultipleFile(@RequestParam("files") files: Array<MultipartFile>): List<UploadFileResponseVO>{
        val uploadFileResponseVOs = arrayListOf<UploadFileResponseVO>()
        for(file in files){
            var uploadFileResponseVO: UploadFileResponseVO = uploadFile(file)
            uploadFileResponseVOs.add(uploadFileResponseVO)
        }
        return uploadFileResponseVOs
    }

    @GetMapping("/downloadFile/{fileName:.+}")//nome do arquivo + mais a extensao
    fun downloadFile(@PathVariable fileName: String, request: HttpServletRequest): ResponseEntity<Resource>{
        val resource = filesStorageService.laodFileAsResource(fileName)
        var contentType = ""
        try {
            contentType = request.servletContext.getMimeType(resource.file.absolutePath)

        }catch (e: Exception){
            logger.info("Copuld not determine file type!")
        }
        if (contentType.isBlank()) contentType = "application/octet-stream"

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION,"""attachment; filename="${resource.filename}"""")
            .body(resource)

    }
}