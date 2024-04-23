import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin.moviles.R
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FetchDataTask().execute()
    }

    inner class FetchDataTask : AsyncTask<Void, Void, JSONArray>() {

        override fun doInBackground(vararg params: Void?): JSONArray? {
            var result: JSONArray? = null
            var connection: HttpURLConnection? = null

            try {
                val url = URL("https://api.api-ninjas.com/v1/dnslookup?domain=google.com")
                connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("X-Api-Key", "wLVPN1zV08lJYF7uXqgyPw==zVwp6TlVcAO1NLUf")

                val inputStream = connection.inputStream
                val bufferedReader = BufferedReader(InputStreamReader(inputStream))
                val stringBuilder = StringBuilder()
                var line: String?
                while (bufferedReader.readLine().also { line = it } != null) {
                    stringBuilder.append(line)
                }
                result = JSONArray(stringBuilder.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                connection?.disconnect()
            }

            return result
        }

        override fun onPostExecute(result: JSONArray?) {
            super.onPostExecute(result)
            result?.let {
                displayData(it)
            }
        }
    }

    private fun displayData(data: JSONArray) {
        val dataString = data.toString()
        textView.text = dataString
    }
}
