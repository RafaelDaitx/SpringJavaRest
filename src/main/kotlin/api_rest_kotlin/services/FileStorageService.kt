package api_rest_kotlin.services

import api_rest_kotlin.config.FileStorageConfig
import api_rest_kotlin.exceptions.FileStorageException
import api_rest_kotlin.exceptions.MyFileNotFoundException
import com.ctc.wstx.util.StringUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption


@Service
class FileStorageService @Autowired constructor(fileStorageConfig: FileStorageConfig) {

    private val fileStorageLocation: Path

    init {
        fileStorageLocation= Paths.get(fileStorageConfig.uploadDir).toAbsolutePath().normalize()

        try {
            Files.createDirectories(fileStorageLocation)
        }catch (e: Exception){
            throw FileStorageException("Could not create the directory where the uploaded files" +
                    "will be stored!", e)
        }
    }

    fun storeFile(file: MultipartFile):String{
        val fileName = StringUtils.cleanPath(file.originalFilename!!)
        return try {
            if(fileName.contains(".."))  throw FileStorageException("Sorry. File $fileName. " +
                    "contains an invalid path sequence!")
            val targetLocation = fileStorageLocation.resolve(fileName)//Determina o local a ser salvo
            Files.copy(file.inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING)//replace se existe
            fileName
        }catch(e: Exception){
            throw FileStorageException("Could not store file $fileName. Please try again!", e)
        }
    }

    fun laodFileAsResource(fileName: String):Resource {
        return try {
            val filePath = fileStorageLocation.resolve(fileName).normalize()//caminho dele até o disco
            val reosurce: Resource = UrlResource(filePath.toUri())
            if(reosurce.exists()) reosurce
            else throw MyFileNotFoundException("File not Found $fileName!")
        }catch(e: Exception){
            throw MyFileNotFoundException("File not Found $fileName!", e)
        }
    }


}