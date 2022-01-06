package com.wagner.tqi.response;


import lombok.Builder;
import lombok.Data;



// Classe utilizada como response
@Data
@Builder
public class MessageResponseDTO {
    private String mensagem;
    private String autor;

    public static MessageResponseDTO createMessageResponseDTO(Long id, String message, String autor) {

        return MessageResponseDTO
                .builder()
                .mensagem(message + id)
                .autor(autor)
                .build();
    }
}
