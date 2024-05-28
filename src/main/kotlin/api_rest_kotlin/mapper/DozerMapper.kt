package api_rest_kotlin.mapper

import com.github.dozermapper.core.DozerBeanMapperBuilder
import com.github.dozermapper.core.Mapper

object DozerMapper {

    private val mapper: Mapper = DozerBeanMapperBuilder.buildDefault()

    //Parse de objetos com Dozer
    fun <O,D> parseObject(origin: O, destination: Class<D>?) : D{
        return mapper.map(origin, destination)
    }

    fun <O,D> parseListObject(origin: List<O>, destination: Class<D>?) : ArrayList<D>{
        val destinationObjects: ArrayList<D> = ArrayList()

        for(o in origin){
            destinationObjects.add(mapper.map(o, destination))
        }
        return destinationObjects
    }
}