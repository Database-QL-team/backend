package ggyuel.ggyuup.global.apiResponse;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class Message {

    private String message;
    @Builder
    public Message(String message) {
        this.message = message;
    }

}