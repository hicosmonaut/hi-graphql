package hi.cosmonaut.graphql.util

import com.google.gson.GsonBuilder

class GsonUtils {
    companion object{
        private val TAG: String = GsonUtils::class.java.simpleName

        fun <T> queryDataToModel(json: String, model: Class<T>): T{
            val builder = GsonBuilder().create()
            return builder.fromJson(json, model)
        }
    }
}