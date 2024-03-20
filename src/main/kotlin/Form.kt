import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Form(
    val title: String,
    val border: Boolean = true,
    val fields: Map<String, Field>,
)

interface Field {
    val label: String
    val position: Int
    val fyi: String?
    val help: String?
    val errMsg: String?
}

enum class WhitespaceHandling(
    private val transform: (String) -> String
) {
    PRESERVE({ it }),
    TRIM(String::trim),
    NORMALIZE(String::normalizeSpace);

    fun handleWhitespace(s: String): String = transform(s)
}

@Serializable
data class StringField(
    override val label: String,
    override val position: Int,
    override val fyi: String? = null,
    override val errMsg: String? = null,
    override val help: String? = null,
    val minLength: Int,
    val maxLength: Int,
    @Contextual
    val regex: Regex? = null,
    @Contextual
    val validChars: Regex? = null,
    val whitespaceHandling: WhitespaceHandling? = null,
) : Field {
    init {
        require(minLength >= 0) {
            "Invalid minimum length: must be positive or zero"
        }
        require(maxLength >= 1) {
            "Invalid maximum length: must be positive"
        }
        require(minLength <= maxLength) {
            "Maximum length ($maxLength) must be greater than minimum length ($minLength)"
        }
    }
}

@Serializable
data class EnumField(
    override val label: String,
    override val position: Int,
    override val fyi: String? = null,
    override val help: String? = null,
    override val errMsg: String? = null,
    val values: Map<String, String>,
) : Field