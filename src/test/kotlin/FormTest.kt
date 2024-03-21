import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlin.test.Test
import kotlin.test.assertEquals

class FormTest {

    val form = Form(
        title = "Person",
        border = true,
        fields = mapOf(
            "id" to StringField(
                label = "Id",
                position = 1,
                minLength = 8,
                maxLength = 8,
                regex = "\\d{8}".toRegex(),
                validChars = "\\d".toRegex(),
                fyi = "8 digits",
                errMsg = "Invalid id: must be 8 digits",
                help = """
                    ℹ️ Enter _exactly_ 8 digits.
      
                    Doncha dare tell us we didn't warn you!
                """.trimIndent(),
            ),
            "name" to StringField(
                label = "Name",
                position = 2,
                minLength = 4,
                maxLength = 32,
                regex = "\\p{IsLatin}\\.?( (\\.|\\p{IsLatin}))+".toRegex(),
                validChars = "[ \\p{IsLatin}]".toRegex(),
                whitespaceHandling = WhitespaceHandling.NORMALIZE,
                fyi = "An alphabetic, space-separated person name",
                errMsg = "Invalid name: must contain only letters and spaces",
            ),
            "gender" to EnumField(
                label = "Gender",
                position = 3,
                fyi = "An alphabetic, space-separated person name",
                errMsg = "Invalid name: must contain only letters and spaces",
                values = mapOf(
                    "f" to "♀ Female",
                    "m" to "♂ Male",
                )
            ),
            "militaryId" to StringField(
                label = "Military Id",
                position = 4,
                minLength = 7,
                maxLength = 7,
                regex = "\\d{7}".toRegex(),
                validChars = "\\d".toRegex(),
                fyi = "7 digits",
                errMsg = "Invalid military id: must be 7 digits",
                help = """
                    ℹ️ Enter _exactly_ 7 digits.
                          
                    Applies **only to males**; you've been warned!
                """.trimIndent()
            ),
        )
    )

    @Test
    @OptIn(ExperimentalSerializationApi::class)
    fun serializesToJson() {
        val formJson = jsonSerializer.encodeToString(form)
        val deserializedForm = jsonSerializer.decodeFromString<Form>(formJson)
        assertEquals(
            form.toString().normalizeSpace(),
            deserializedForm.toString().normalizeSpace()
        )
    }

    @Test
    fun interpretsSwing() {
        SwingFormInterpreter.interpret(form)
        Thread.sleep(15000)
    }

    @Test
    fun interpretsWeb() {
        WebFormInterpreter.interpret(form)
    }
}