package ru.sibur.imgurbrowser.data.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class ColorSerializer : KSerializer<Long> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("ImgurColor", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Long) {
        encoder.encodeString(value.toString(16).uppercase())
    }

    override fun deserialize(decoder: Decoder): Long {
        val colorValue = decoder.decodeString()
        return java.lang.Long.parseLong(colorValue, 16) or 0xff000000
    }
}
