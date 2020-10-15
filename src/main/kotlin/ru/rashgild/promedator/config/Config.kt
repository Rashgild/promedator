package ru.rashgild.promedator.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import ru.rashgild.promedator.utils.DisableSslVerificationUtil.disableSslVerification


@Configuration
open class Config {

    @Bean
    open fun objectMapper(): ObjectMapper {
        return Jackson2ObjectMapperBuilder.json().build()
    }

    @Bean
    open fun disableSsl() {
        disableSslVerification()
    }
}
