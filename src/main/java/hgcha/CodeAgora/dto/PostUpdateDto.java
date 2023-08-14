package hgcha.CodeAgora.dto;

import lombok.Getter;

@Getter
public class PostUpdateDto {

    private String title;
    private String content;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}


