import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

object RegexSerializer : KSerializer<Regex> {
    override fun deserialize(decoder: Decoder): Regex =
        decoder.decodeString().toRegex()

    override fun serialize(encoder: Encoder, value: Regex) =
        encoder.encodeString(value.toString())

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Regex", PrimitiveKind.STRING)
}

val jsonSerializer = Json {
    serializersModule = SerializersModule {
        polymorphic(Field::class) {
            subclass(StringField::class)
            subclass(EnumField::class)
        }
        contextual(Regex::class, RegexSerializer)
    }
}
