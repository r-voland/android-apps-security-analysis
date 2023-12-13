import org.jose4j.base64url.Base64
import org.jose4j.jwe.JsonWebEncryption
import org.jose4j.jws.JsonWebSignature
import org.jose4j.jwx.JsonWebStructure
import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import javax.crypto.spec.SecretKeySpec


class JweDecoder {

    init {
        decode()
    }

    companion object {

        @Throws(Exception::class)
        fun decode() {
            // TEST VALUES ------------------
            val verdict =
                "eyJhbGciOiJBMjU2S1ciLCJlbmMiOiJBMjU2R0NNIn0.WwXUJQTM9duDP9qf6rkKgxwUTHKbDPvapSoBDbNP0kYmpIbUHSlahQ.9coJu8HsjXhkAc8S.SdWU5TZw1UtUq-eZD_GeUf7eQ_Om51Eey1jaOBH8mtIxRdp3M11RWCdMFAsC_nS_N1bhWj4qib0wFPn1VISZoD-N3RDXLT5aH9dP1KXpncE-fe0t-rQVr2OyaaocMkaFRV3YLoMo-htUa4-2Jrf3BOPG5vv43YkyqfSKr7Y9Zh_ogAtCmw8kUqsVnzEIdgmLoF7m0NvKIJnwUwE02tO0Xgh5LVm0wTM7YhdFKC2M24-j-YhLERoPDxOSHgIlQJA170jIMvrpVDdiNijQFyyecnWCjIgkNkB1L3wbs9XxbTTe9QZIKS0bzU3xEfuBQe34soRYZoMqxDbzH5TCEYAvBR6O9i43SRDngwUwsW68eS-ewDztL7MATwnr6XBlJhPcgV4iUbK_1--glwJFGFdY7voto2QsdyRaYMhu95oq8QMr1UmiQA8PVcGkcbXbquw6Z-WFu747hIdRLqH8i4A1NHQufIn9yGJiCuA2NcQV0x2C_myKTMQ1G_-VIYM0oHoT7EUGUv3oHaYP0ZQZctqozE4kMSMqtFDRi82WGfmg7Z9Y-ogosYzRSLK9VsmS1IFgVBYKfndHm9VPDkEd4Kg2YjL0fURvdDqoP41Iy80-fD83-Lz2AvdpwypPyj44WlhB1tsoXF3CJOBubWD7re0mpXml4T0yfZqgvKmbg9Fy_28vn9ptJpkGG-9L4IhOg8aD5s6XQ9taztTrg1rGR_ztci90Ge-DrrgALx2juhh3DPfKT8i_5lZEQ4zzsiXSQPPUyrZAi_QGO-FTdv2m9N8DgA_Moo9k1uPmDCGQiqMaDY-94baN8SCnJCFQsCyMt5Nu2ta0SxJ3xFeUmvlAMRU-9ytVMfAddBFiUJn7hn49T4qBb00Leq6FNPEuftQFWpSKIAlt-ag69Q09cYG_d9Z1DfxDMNH5pazU4k6elWkfx0H9krvh2XzHPI6akz-TQdwSvo1cpfWcJrA94KOonxXhX9bzmOoRezSr4EW_nu6Fug55_0JnRLa2ZKkVf5NONIdRsyVWntr9dcY4KdQTS38YQB3hFqesKtoszvu7ZPyFnYTeQU8deU9XUB6m03GyBQ9bK6d0MrbIz1Dfi_U.DbSnUoSQR8vHOzLarhbc1w"
            val decriptionKey64 = "E6qoMnNaBBnki9S398nQVbljl6E1TGmoxNI/sBKE7Mw="
            val verificationKey64 =
                "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEwBGC/IMBGL6maxYzDNnkD4izXewqX2fC99hRtDKS8hU38/fYhuRxb9rpvEVe+RPAW4ehP0m9Nv61HjdIYhQAtA=="
            //---------------------------------

            val decriptionKey = Base64.decode(decriptionKey64)
            val pubSecKeySpec = SecretKeySpec(decriptionKey, "AES")

            val verificationKey = Base64.decode(verificationKey64)
            val keyFactory = KeyFactory.getInstance("EC")

            val x509EncodedKeySpec = X509EncodedKeySpec(verificationKey)

            val generatedPublicKey = keyFactory.generatePublic(x509EncodedKeySpec)

            val jsonWebStructure = JsonWebStructure.fromCompactSerialization(verdict)

            val jsonWebEncryption = jsonWebStructure as JsonWebEncryption
            jsonWebEncryption.key = pubSecKeySpec

            val jwePayload = jsonWebEncryption.payload
            println("JWE Payload: $jwePayload")

            val jsonWebStructure2 = JsonWebStructure.fromCompactSerialization(jwePayload)
            val jsonWebSignature = jsonWebStructure2 as JsonWebSignature
            jsonWebSignature.key = generatedPublicKey

            val jwsPayload = jsonWebSignature.payload
            println("JWS Payload: $jwsPayload")
        }
    }
}
